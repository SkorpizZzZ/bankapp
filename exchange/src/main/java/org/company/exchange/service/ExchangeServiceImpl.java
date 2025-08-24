package org.company.exchange.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.exchange.constant.CurrencyEnum;
import org.company.exchange.dto.CurrencyDto;
import org.company.exchange.dto.RateDto;
import org.company.exchange.entity.Currency;
import org.company.exchange.exception.ExchangeException;
import org.company.exchange.mapper.CurrencyMapper;
import org.company.exchange.repository.CurrencyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService{

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public BigDecimal exchange(String from, String to, BigDecimal value) {
        log.info("Процесс конвертации из валюты {} в валюту {} значение {}", from, to, value);
        BigDecimal rateFrom = currencyRepository.findValueByName(from)
                .orElseThrow(() -> new ExchangeException(format("Валюты %s нет", from), HttpStatus.NOT_FOUND));
        log.debug("Найдена валюта из {}", rateFrom);
        BigDecimal rateTo = currencyRepository.findValueByName(to)
                .orElseThrow(() -> new ExchangeException(format("Валюты %s нет", to), HttpStatus.NOT_FOUND));
        log.debug("Найдена валюта в {}", rateTo);
        BigDecimal result = convert(rateFrom, rateTo, value);
        log.info("Валюта с конвертировалась из {}, в {}", value, result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CurrencyDto> findAll() {
        log.info("Процесс поиска всех доступных валют");
        List<CurrencyDto> result = currencyRepository.findAll().stream()
                .map(mapper::entityToDto)
                .toList();
        log.info("Найденные валюты {}", result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RateDto> findAllRates() {
        log.info("Процесс поиска курса валют по отношению к рублю");
        List<Currency> currencies = currencyRepository.findAll();
        Currency rub = currencies.stream()
                .filter(cur -> cur.getName().equalsIgnoreCase(CurrencyEnum.RUB.name()))
                .findFirst()
                .orElseThrow(() -> new ExchangeException(format("Валюты %s нет", CurrencyEnum.RUB.name()), HttpStatus.NOT_FOUND));
        log.debug("Найдена валюта рубля {}", rub);

        List<RateDto> result = currencies.stream()
                .filter(cur -> !cur.getName().equalsIgnoreCase(CurrencyEnum.RUB.name()))
                .map(cur -> buildRate(cur, rub))
                .toList();
        log.info("Подсчитан курс {}", result);
        return result;
    }

    private RateDto buildRate(Currency otherCurrency, Currency rub) {
        return new RateDto(
                otherCurrency.getTitle(),
                otherCurrency.getName(),
                otherCurrency.getValue().divide(rub.getValue(), 2, RoundingMode.HALF_UP)
        );
    }

    private BigDecimal convert(BigDecimal rateFrom, BigDecimal rateTo, BigDecimal value) {
        return value.multiply(rateTo).divide(rateFrom, 2, RoundingMode.HALF_UP);
    }
}
