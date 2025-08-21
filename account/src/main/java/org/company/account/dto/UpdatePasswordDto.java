package org.company.account.dto;

public record UpdatePasswordDto(
        String login,
        String password,
        String confirmPassword
) {
}
