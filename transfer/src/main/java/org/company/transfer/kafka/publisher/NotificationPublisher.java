package org.company.transfer.kafka.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.transfer.dto.NotificationDto;
import org.company.transfer.exception.TransferException;
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
public class NotificationPublisher {

    private final KafkaTemplate<String, NotificationDto> kafkaTemplate;

    public void publish(NotificationDto notificationDto) {
        try {
            kafkaTemplate.send("notification-topic", notificationDto)
                    .get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new TransferException(
                    format("Ошибка отправки уведомления в топик %s", e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }
}
