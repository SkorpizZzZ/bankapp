package org.company.exchange.mapper;

import org.company.exchange.dto.CurrencyDto;
import org.company.exchange.entity.Currency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyDto entityToDto(Currency currency);
}
