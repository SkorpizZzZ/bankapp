package org.company.exchangegenerator.dto;

import java.util.List;

public record CurrencyListDto(
        List<CurrencyDto> currencies
) {
}
