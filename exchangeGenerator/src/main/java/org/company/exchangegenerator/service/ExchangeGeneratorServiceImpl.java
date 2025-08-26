package org.company.exchangegenerator.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.company.exchangegenerator.dto.CurrencyDto;
import org.company.exchangegenerator.feign.ExchangeFeign;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeGeneratorServiceImpl implements ExchangeGeneratorService {

    private final ExchangeFeign exchangeFeign;

    @Override
    @Scheduled(fixedDelay = 60000)
    public void sendNewRates() {
        List<CurrencyDto> currencies;
        currencies = findCurrencies();
        sendUpdatedRates(currencies);
    }

    private void sendUpdatedRates(List<CurrencyDto> currencies) {
        if (CollectionUtils.isEmpty(currencies)) {
            log.warn("Список валют пуст, нечего обновлять");
            return;
        }
        log.debug("Валюты до обновлений {}", currencies);
        List<CurrencyDto> updatedCurrencies = currencies.stream()
                .map(currencyDto -> buildCurrency(currencyDto))
                .toList();
        log.debug("Валюты после обновления {}", updatedCurrencies);
        try {
            log.debug("Процесс отправки обновленных валют");
            exchangeFeign.updateCurrencies(updatedCurrencies);
        } catch (FeignException.ServiceUnavailable e) {
            log.warn("Сервис валют не доступен");
        } catch (FeignException e) {
            log.warn(e.contentUTF8());
        }
    }

    private CurrencyDto buildCurrency(CurrencyDto currencyDto) {
        return new CurrencyDto(currencyDto.title(), currencyDto.name(), calculateValue(currencyDto));
    }

    private BigDecimal calculateValue(CurrencyDto currencyDto) {
        return currencyDto.value().multiply(multiplyWithRandom());
    }

    private BigDecimal multiplyWithRandom() {
        Random random = new Random();
        double randomMultiplier = 0.9 + random.nextDouble() * 0.2;
        return BigDecimal.valueOf(randomMultiplier).setScale(2, RoundingMode.HALF_UP);
    }

    private List<CurrencyDto> findCurrencies() {
        log.info("Процесс поиска всех валют");
        try {
            return exchangeFeign.findAll();
        } catch (FeignException.ServiceUnavailable e) {
            log.warn("Сервис валют не доступен");
        } catch (FeignException e) {
            log.warn(e.contentUTF8());
        }
        return Collections.emptyList();
    }
}
