package org.example.front.dto;

import java.math.BigDecimal;

public record AccountDto(
        Long accountId,
        CurrencyDto currency,
        BigDecimal balance,
        boolean exist
) {
    public boolean isExists() {
        return exist;
    }
}
