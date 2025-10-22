package org.company.exchangegenerator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.company.exchangegenerator.dto.CurrencyDto;
import org.company.exchangegenerator.dto.CurrencyListDto;
import org.company.exchangegenerator.exception.ExchangeGeneratorException;
import org.company.exchangegenerator.kafka.publisher.RatePublisher;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeGeneratorServiceImpl implements ExchangeGeneratorService {

    private final ReplyingKafkaTemplate<String, String, CurrencyListDto> replyingKafkaTemplate;
    private final RatePublisher ratePublisher;

    @Override
    @Scheduled(fixedDelay = 60000)
    public void sendNewRates() {
        List<CurrencyDto> currencies = findCurrencies().currencies();
        sendUpdatedRates(currencies);
    }

    private void sendUpdatedRates(List<CurrencyDto> currencies) {
        if (CollectionUtils.isEmpty(currencies)) {
            log.warn("Список валют пуст, нечего обновлять");
            return;
        }
        log.debug("Валюты до обновлений {}", currencies);
        List<CurrencyDto> updatedCurrencies = currencies.stream()
                .map(this::buildCurrency)
                .toList();
        log.debug("Валюты после обновления {}", updatedCurrencies);
        log.debug("Процесс отправки обновленных валют");
        ratePublisher.publish(new CurrencyListDto(updatedCurrencies));
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

    private CurrencyListDto findCurrencies() {
        log.info("Процесс поиска всех валют");
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("exchange-command", "exchange");
        try {
            RequestReplyFuture<String, String, CurrencyListDto> future = replyingKafkaTemplate.sendAndReceive(producerRecord);
            SendResult<String, String> sendResult = future.getSendFuture().get(10, TimeUnit.SECONDS);
            log.debug("Сообщение отправлено: {}", sendResult.getRecordMetadata());

            ConsumerRecord<String, CurrencyListDto> reply = future.get(30, TimeUnit.SECONDS);
            log.debug("Получен список валют {}", reply.value());
            return reply.value();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Запрос прерван", e);
            throw new ExchangeGeneratorException("Запрос прерван", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ExecutionException e) {
            log.error("Ошибка выполнения запроса", e);
            throw new ExchangeGeneratorException("Ошибка сервиса валют", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (TimeoutException e) {
            log.error("Таймаут ожидания ответа", e);
            throw new ExchangeGeneratorException("Сервис валют не отвечает", HttpStatus.REQUEST_TIMEOUT);
        }
    }
}
