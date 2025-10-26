package org.company.exchangegenerator.kafka.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.exchangegenerator.dto.CurrencyListDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatePublisher {

    private final KafkaTemplate<String, CurrencyListDto> kafkaTemplate;

    public void publish(CurrencyListDto updatedCurrencies) {
        kafkaTemplate.send("exchange-topic", updatedCurrencies)
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        log.error("Ошибка отправки уведомления в топик exchange-topic", throwable);
                    } else {
                        log.debug("Сообщение успешно отправлено в топик exchange-topic, offset: {}",
                                result.getRecordMetadata().offset());
                    }
                });
    }
}
