package org.example.front.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.example.front.controller.validator.Adult;

import java.time.LocalDate;

public record EditUserAccountDto(
        @NotBlank
        String name,
        @NotNull
        @Past
        @Adult
        LocalDate birthdate
) {
}
