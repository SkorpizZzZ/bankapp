package org.company.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.company.account.constant.Currency;
import org.company.account.domain.Account;
import org.company.account.domain.User;
import org.company.account.dto.*;
import org.company.account.exception.AccountException;
import org.company.account.exception.UserNotFoundException;
import org.company.account.feign.ExchangeFeign;
import org.company.account.mapper.AccountMapper;
import org.company.account.mapper.UserMapper;
import org.company.account.repository.AccountRepository;
import org.company.account.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    private final ExchangeFeign exchangeFeign;

    private final UserMapper userMapper;
    private final AccountMapper accountMapper;


    @Override
    @Transactional
    public CreateUserDto createUser(CreateUserDto user) {
        log.debug("Процесс добавления пользователя {}", user);
        ifLoginNotUniqueThrow(user.login());
        User savedUser = userRepository.save(userMapper.createUserDtoToEntity(user));
        savedUser.setAccounts(saveAccounts(savedUser));
        log.debug("Пользователь добавлен {}", savedUser);
        return userMapper.entityToCreateUserDto(savedUser);
    }

    private List<Account> saveAccounts(User savedUser) {
        return accountRepository.saveAll(buildAccounts(savedUser));
    }

    private List<Account> buildAccounts(User savedUser) {
        return List.of(
                Account.builder()
                        .user(savedUser)
                        .currency(Currency.RUB.name())
                        .build(),
                Account.builder()
                        .user(savedUser)
                        .currency(Currency.USD.name())
                        .build(),
                Account.builder()
                        .user(savedUser)
                        .currency(Currency.CNY.name())
                        .build()
        );
    }

    private void ifLoginNotUniqueThrow(String login) {
        log.debug("Процесс проверки уникальности логина {}", login);
        userRepository.findUserByLogin(login)
                .ifPresent(user -> {
                    log.error("Пользователь с логином {} уже существует", login);
                    throw new AccountException(format("Пользователь с логином %s уже существует", login), HttpStatus.FORBIDDEN);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findUserByLogin(String login) {
        log.info("Процесс поиска пользователя по логину {}", login);
        User user = userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(format("Пользователь с логином %s не найден", login)));
        List<CurrencyDto> currencies = exchangeFeign.findAll();
        log.debug("Найдены все доступные валюты {}", currencies);
        return userMapper.entityToUserDto(user, accountMapper.toAccountDtoList(user.getAccounts(), currencies));
    }

    @Override
    @Transactional
    public UpdatePasswordDto updatePassword(UpdatePasswordDto user) {
        log.debug("Процесс обновления пароля пользователя {}", user.login());
        User foundUser = userRepository.findUserByLogin(user.login())
                .orElseThrow(() -> new UserNotFoundException(format("Пользователь с логином %s не найден", user.login())));
        foundUser.setPassword(user.password());
        User updatedUser = userRepository.save(foundUser);
        log.debug("Процесс обновления пароля {} завершился успешно", updatedUser);
        return userMapper.entityToUpdatePasswordDto(updatedUser);
    }

    @Override
    @Transactional
    public EditUserAccountDto updateUserAccounts(EditUserAccountDto user) {
        log.debug("Процесс обновления персональных данных пользователя {}", user);
        User foundUser = userRepository.findUserByLogin(user.login())
                .orElseThrow(() -> new UserNotFoundException(format("Пользователь с логином %s не найден", user.login())));
        foundUser.setName(user.name());
        foundUser.setBirthdate(user.birthdate());
        User updatedUser = userRepository.save(foundUser);
        EditUserAccountDto result = userMapper.entityToEditUserAccountDto(updatedUser);
        log.debug("Данные обновлены {}", result);
        return result;
    }

    @Override
    @Transactional
    public void transfer(String login, TransferExchangeDto transferDto) {
        log.info("Процесс перевода пользователя {} пользователю {}", login, transferDto.toLogin());
        User userFrom = userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(format("Пользователь с логином %s не найден", login)));
        Account accountFrom = getTransferAccount(transferDto.fromCurrency(), userFrom);
        ifValueMoreThanCurrentBalanceThrow(accountFrom.getBalance(), transferDto.value());
        Account accountTo;
        if (isTransferToYourself(login, transferDto.toLogin())) {
            log.debug("Перевод средств самому себе ");
            accountTo = getTransferAccount(transferDto.toCurrency(), userFrom);
        } else {
            log.debug("Перевод средств другому пользователю");
            accountTo = getExternalAccount(transferDto);
        }
        transferMoney(accountFrom, accountTo, transferDto.value(), transferDto.convertedValue());
        List<Account> accounts = accountRepository.saveAll(List.of(accountFrom, accountTo));
        log.debug("Средства переведены {}", accounts);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EditUserAccountDto> findAllUsersData() {
        return userRepository.findAll().stream()
                .map(userMapper::entityToEditUserAccountDto)
                .toList();
    }

    private Account getExternalAccount(TransferExchangeDto transferDto) {
        User userTo = userRepository.findUserByLogin(transferDto.toLogin())
                .orElseThrow(() -> new UserNotFoundException(format("Пользователь с логином %s не найден", transferDto.toLogin())));
        log.debug("Найден пользователь, кому переводятся средства {}", userTo);
        return getTransferAccount(transferDto.toCurrency(), userTo);
    }

    private void ifValueMoreThanCurrentBalanceThrow(BigDecimal balance, BigDecimal value) {
        log.debug("Процесс проверки, что средств на балансе {} достаточно для перевода {}", balance, value);
        if (value.compareTo(balance) > 0) {
            log.error("На балансе не достаточно средств");
            throw new AccountException("На балансе не достаточно средств", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private void transferMoney(Account accountFrom, Account accountTo, BigDecimal value, BigDecimal convertedValue) {
        log.debug("Процесс перевода денег из одного аккаунта {} с суммой {} на другой {} c суммой {}",
                accountFrom, value, accountTo, convertedValue
        );
        accountFrom.setBalance(accountFrom.getBalance().subtract(value));
        accountTo.setBalance(accountTo.getBalance().add(convertedValue));
    }

    private static Account getTransferAccount(String currency, User user) {
        log.info("Процесс поиска аккаунта валютой {} пользователя {}", currency, user.getLogin());
        Account accFrom = user.getAccounts().stream()
                .filter(account -> currency.equalsIgnoreCase(account.getCurrency()))
                .findFirst()
                .orElseThrow();
        log.info("Процесс перевода из аккаунта {}", accFrom);
        return accFrom;
    }

    private boolean isTransferToYourself(String from, String to) {
        return StringUtils.equals(from, to);
    }
}
