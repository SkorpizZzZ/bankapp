package org.company.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.company.account.controller.validator.Adult;

import java.time.LocalDate;
import java.util.List;

public record UserDto(
        @Email
        @NotBlank
        String login,
        @NotBlank
        String password,
        @NotBlank
        String name,
        @NotNull
        @Past
        @Adult
        LocalDate birthdate,
        List<AccountDto> accounts
) {
}