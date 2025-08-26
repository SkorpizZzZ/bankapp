package org.company.exchangegenerator.dto;

import java.math.BigDecimal;

public record CurrencyDto(
        String title,
        String name,
        BigDecimal value
) {
}
