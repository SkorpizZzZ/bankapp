package org.company.exchange.service;

import org.company.exchange.dto.CurrencyDto;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeService {

    BigDecimal exchange(String from , String to, BigDecimal value);

    List<CurrencyDto> findAll();
}
