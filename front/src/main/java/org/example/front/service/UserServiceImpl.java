package org.example.front.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.example.front.dto.CreateUserDto;
import org.example.front.dto.EditUserAccountDto;
import org.example.front.dto.UpdatePasswordDto;
import org.example.front.dto.UserDto;
import org.example.front.feign.FeignAccount;
import org.example.front.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final FeignAccount feignAccount;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user;
        try {
             user = feignAccount.findUserByLogin(username);
        } catch (FeignException.FeignClientException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
        return mapper.userDtoToUserDetails(user);
    }

    @Override
    public UserDto createUser(CreateUserDto user) {
        return feignAccount.createUser(mapper.createUserDtoToUserDto(user, passwordEncoder));
    }

    @Override
    public UserDto updatePassword(String login, UpdatePasswordDto user) {
        return feignAccount.updatePassword(mapper.updatePasswordDtoToUserDto(login, user, passwordEncoder));
    }

    @Override
    public UserDto updateUserAccounts(String login, EditUserAccountDto dto) {
        return feignAccount.updateUserAccounts(mapper.editUserAccountDtoToUserDto(login, dto));
    }
}
