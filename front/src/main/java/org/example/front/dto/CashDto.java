package org.example.front.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record CashDto(
        @NotBlank(message = "Необходимо указать валюту")
        String currency,
        @NotNull(message = "Необходимо указать сумму")
        BigDecimal value,
        @NotBlank
        @Pattern(regexp = "^(GET|PUT)$", message = "Можно либо снять, либо положить на счет")
        String action
) {
}
