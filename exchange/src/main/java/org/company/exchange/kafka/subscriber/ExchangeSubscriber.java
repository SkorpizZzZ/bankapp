package org.company.exchange.kafka.subscriber;

import lombok.RequiredArgsConstructor;
import org.company.exchange.dto.CurrencyListDto;
import org.company.exchange.service.ExchangeService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeSubscriber {

    private final ExchangeService exchangeService;

    @KafkaListener(
            topics = "exchange-command",
            containerFactory = "stringKafkaListenerContainerFactory"
    )
    @SendTo
    public CurrencyListDto findAll() {
        return new CurrencyListDto(exchangeService.findAll());
    }

    @KafkaListener(
            topics = "exchange-topic",
            containerFactory = "jsonKafkaListenerContainerFactory"
    )
    public void updateCurrencies(CurrencyListDto currencies) {
        exchangeService.updateCurrencies(currencies.currencies());
    }
}
