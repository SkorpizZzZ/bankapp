package org.company.exchange.service;

import org.company.exchange.dto.CurrencyDto;
import org.company.exchange.dto.RateDto;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeService {

    BigDecimal exchange(String from , String to, BigDecimal value);

    List<CurrencyDto> findAll();

    List<RateDto> findAllRates();

    void updateCurrencies(List<CurrencyDto> currencies);
}
