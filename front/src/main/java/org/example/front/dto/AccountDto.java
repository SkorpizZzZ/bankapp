package org.example.front.dto;

import java.math.BigDecimal;

public record AccountDto(
        Long accountId,
        String currency,
        BigDecimal balance
) {
}
