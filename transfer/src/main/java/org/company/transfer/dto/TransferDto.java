package org.company.transfer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferDto(
        @NotBlank
        String fromCurrency,
        @NotBlank
        String toCurrency,
        @NotNull
        BigDecimal value,
        String toLogin
) {
}
