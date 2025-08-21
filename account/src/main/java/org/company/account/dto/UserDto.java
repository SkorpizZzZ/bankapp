package org.company.account.dto;

import java.time.LocalDate;
import java.util.List;

public record UserDto(
        String login,
        String password,
        String name,
        LocalDate birthdate,
        List<AccountDto> accounts
) {
}