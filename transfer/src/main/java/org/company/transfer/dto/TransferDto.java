package org.company.transfer.dto;

import java.math.BigDecimal;

public record TransferDto(
        String fromCurrency,
        String toCurrency,
        BigDecimal value,
        String toLogin
) {
}
