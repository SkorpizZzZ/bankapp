package org.company.account.dto;

import java.math.BigDecimal;

public record AccountDto(
        Long accountId,
        CurrencyDto currency,
        BigDecimal balance,
        boolean exist
) {
    boolean isExist() {
        return exist;
    }
}
