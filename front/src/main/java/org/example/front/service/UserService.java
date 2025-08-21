package org.example.front.service;

import org.example.front.dto.CreateUserDto;
import org.example.front.dto.EditUserAccountDto;
import org.example.front.dto.UpdatePasswordDto;

public interface UserService {

    CreateUserDto createUser(CreateUserDto user);

    UpdatePasswordDto updatePassword(String login, UpdatePasswordDto user);

    EditUserAccountDto updateUserAccounts(String login, EditUserAccountDto dto);
}
