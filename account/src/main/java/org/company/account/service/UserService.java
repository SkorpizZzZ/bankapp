package org.company.account.service;

import org.company.account.dto.*;

import java.util.List;

public interface UserService {

    CreateUserDto createUser(CreateUserDto user);

    UserDto findUserByLogin(String username);

    UpdatePasswordDto updatePassword(UpdatePasswordDto user);

    EditUserAccountDto updateUserAccounts(EditUserAccountDto user);

    void transfer(String login, TransferExchangeDto transferDto);

    List<EditUserAccountDto> findAllUsersData();

    void withdraw(String login, CashDto cash);

    void deposit(String login, CashDto cash);
}
