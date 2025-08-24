package org.company.exchange.dto;

import java.math.BigDecimal;

public record RateDto(
        String title,
        String name,
        BigDecimal value
) {
}
