package org.company.account.dto;

import java.math.BigDecimal;

public record AccountDto(
        Long accountId,
        String currency,
        BigDecimal balance
) {
}
