package org.company.account.service;

import lombok.RequiredArgsConstructor;
import org.company.account.domain.Account;
import org.company.account.domain.User;
import org.company.account.dto.UserDto;
import org.company.account.exception.AccountException;
import org.company.account.exception.UserNotFoundException;
import org.company.account.mapper.AccountMapper;
import org.company.account.mapper.UserMapper;
import org.company.account.repository.AccountRepository;
import org.company.account.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final UserMapper userMapper;
    private final AccountMapper accountMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto user) {
        ifLoginNotUniqueThrow(user.login());
        User savedUser = userRepository.save(userMapper.userDtoToEntity(user));
        Account account = Account.builder()
                .user(savedUser)
                .build();
        savedUser.setAccounts(Collections.singletonList(accountRepository.save(account)));
//        AccountDto accountDto = accountMapper.entityToDto(accountRepository.save(account));
        return userMapper.entityToUserDto(savedUser);
    }

    private void ifLoginNotUniqueThrow(String login) {
        userRepository.findUserByLogin(login)
                .ifPresent(user -> {
                    throw new AccountException(format("Пользователь с логином %s уже существует", login), HttpStatus.FORBIDDEN);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findUserByLogin(String login) {
        User user = userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(format("Пользователь с логином %s не найден", login)));
        return userMapper.entityToUserDto(user);
    }

    @Override
    @Transactional
    public UserDto updatePassword(UserDto user) {
        User foundUser = userRepository.findUserByLogin(user.login())
                .orElseThrow(() -> new UserNotFoundException(format("Пользователь с логином %s не найден", user.login())));
        foundUser.setPassword(user.password());
        return userMapper.entityToUserDto(userRepository.save(foundUser));
    }

    @Override
    public UserDto updateUserAccounts(UserDto user) {
        User foundUser = userRepository.findUserByLogin(user.login())
                .orElseThrow(() -> new UserNotFoundException(format("Пользователь с логином %s не найден", user.login())));
        foundUser.setName(user.name());
        foundUser.setBirthdate(user.birthdate());
        return userMapper.entityToUserDto(userRepository.save(foundUser));
    }
}
