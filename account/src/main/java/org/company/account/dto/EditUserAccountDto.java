package org.company.account.dto;

import java.time.LocalDate;

public record EditUserAccountDto(
        String login,
        String name,
        LocalDate birthdate
) {
}
