package org.company.transfer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferExchangeDto(
        @NotBlank
        String fromCurrency,
        @NotBlank
        String toCurrency,
        @NotNull
        BigDecimal value,
        BigDecimal convertedValue,
        String toLogin
) {
}
