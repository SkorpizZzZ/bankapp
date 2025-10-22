package org.company.exchange.dto;

import java.util.List;

public record CurrencyListDto(
        List<CurrencyDto> currencies
) {
}
