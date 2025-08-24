package org.example.front.dto;

import java.math.BigDecimal;

public record RateDto(
        String title,
        String name,
        BigDecimal value
) {
}
