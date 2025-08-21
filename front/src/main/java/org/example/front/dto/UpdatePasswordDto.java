package org.example.front.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public record UpdatePasswordDto(
        String login,
        @NotBlank
        String password,
        @NotBlank
        String confirmPassword
) {
    @AssertTrue(message = "Пароли не совпадают")
    public boolean isPasswordMatching() {
        return Objects.equals(password, confirmPassword);
    }
}
