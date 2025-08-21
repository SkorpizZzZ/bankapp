package org.company.account.dto;

import java.math.BigDecimal;

public record CurrencyDto(
        Long currencyId,
        String title,
        String name,
        BigDecimal value
) {
}
