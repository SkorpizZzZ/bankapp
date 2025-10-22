package org.company.exchangegenerator.kafka.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.exchangegenerator.dto.CurrencyListDto;
import org.company.exchangegenerator.exception.ExchangeGeneratorException;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatePublisher {

    private final KafkaTemplate<String, CurrencyListDto> kafkaTemplate;

    public void publish(CurrencyListDto updatedCurrencies) {
        try {
            kafkaTemplate.send("exchange-topic", updatedCurrencies)
                    .get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new ExchangeGeneratorException(
                    format("Ошибка отправки уведомления в топик %s", e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }
}
