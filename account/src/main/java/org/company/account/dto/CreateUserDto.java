package org.company.account.dto;

import java.time.LocalDate;

public record CreateUserDto(
        String login,
        String password,
        String confirmPassword,
        String name,
        LocalDate birthdate
) {
}