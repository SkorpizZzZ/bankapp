package org.example.front.dto;

import jakarta.validation.constraints.*;
import org.example.front.controller.validator.Adult;

import java.time.LocalDate;
import java.util.Objects;

public record CreateUserDto(
        @Email
        @NotBlank
        String login,
        @NotBlank
        String password,
        @NotBlank
        String confirmPassword,
        @NotBlank
        String name,
        @NotNull
        @Past
        @Adult
        LocalDate birthdate
) {
        @AssertTrue(message = "Пароли не совпадают")
        public boolean isPasswordMatching() {
                return Objects.equals(password, confirmPassword);
        }
}