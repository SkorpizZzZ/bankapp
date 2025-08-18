package org.example.front.dto;

import java.time.LocalDate;
import java.util.List;

public record UserDto(
        Long userId,
        String login,
        String password,
        String name,
        LocalDate birthdate,
        List<AccountDto> accounts
) {
}