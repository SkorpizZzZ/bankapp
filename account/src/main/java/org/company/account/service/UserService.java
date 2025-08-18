package org.company.account.service;

import org.company.account.dto.UserDto;

public interface UserService {

    UserDto createUser(UserDto user);

    UserDto findUserByLogin(String username);

    UserDto updatePassword(UserDto user);

    UserDto updateUserAccounts(UserDto user);
}
