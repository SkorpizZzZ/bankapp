package org.example.front.service;

import org.example.front.dto.CreateUserDto;
import org.example.front.dto.EditUserAccountDto;
import org.example.front.dto.UpdatePasswordDto;
import org.example.front.dto.UserDto;

public interface UserService {

    UserDto createUser(CreateUserDto user);

    UserDto updatePassword(String login, UpdatePasswordDto user);

    UserDto updateUserAccounts(String login, EditUserAccountDto dto);
}
