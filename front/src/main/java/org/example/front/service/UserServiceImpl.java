package org.example.front.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.front.dto.CreateUserDto;
import org.example.front.dto.EditUserAccountDto;
import org.example.front.dto.UpdatePasswordDto;
import org.example.front.dto.UserDto;
import org.example.front.exception.FrontException;
import org.example.front.feign.AccountFeign;
import org.example.front.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final AccountFeign accountFeign;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user;
        try {
             user = accountFeign.findUserByLogin(username);
        } catch (FeignException.FeignClientException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
        return mapper.userDtoToUserDetails(user);
    }

    @Override
    public CreateUserDto createUser(CreateUserDto user) {
        log.info("Процесс добавления пользователя {}", user);
        try {
            return accountFeign.createUser(mapper.encodePassAndReturn(user, passwordEncoder));
        } catch (FeignException.ServiceUnavailable e) {
            log.warn("Аккаунт сервис не доступен. {}", e.getMessage());
            throw new FrontException("Аккаунт сервис не доступен", e.status());
        } catch (FeignException e) {
            log.error(e.contentUTF8());
            throw new FrontException(e.contentUTF8(), e.status());
        }
    }

    @Override
    public UpdatePasswordDto updatePassword(String login, UpdatePasswordDto user) {
        log.info("Процесс обновления пароля для пользователя {}", login);
        return accountFeign.updatePassword(new UpdatePasswordDto(login, passwordEncoder.encode(user.password()), user.confirmPassword()));
    }

    @Override
    public EditUserAccountDto updateUserAccounts(String login, EditUserAccountDto dto) {
        log.info("Процесс обновления данных {} пользователя {}", dto, login);
        return accountFeign.updateUserAccounts(new EditUserAccountDto(login, dto.name(), dto.birthdate()));
    }
}
