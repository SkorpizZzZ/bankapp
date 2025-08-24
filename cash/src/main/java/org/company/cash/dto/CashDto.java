package org.company.cash.dto;

import java.math.BigDecimal;

public record CashDto(
        String currency,
        BigDecimal value,
        String action
) {
}
