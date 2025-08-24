package org.company.transfer.dto;


import java.math.BigDecimal;

public record TransferExchangeDto(
        String fromCurrency,
        String toCurrency,
        BigDecimal value,
        BigDecimal convertedValue,
        String toLogin
) {
}
