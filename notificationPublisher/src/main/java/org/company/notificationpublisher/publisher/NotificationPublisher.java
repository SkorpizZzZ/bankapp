package org.company.notificationpublisher.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.notificationpublisher.dto.NotificationDto;
import org.company.notificationpublisher.exception.NotificationPublisherException;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public class NotificationPublisher {

    private final KafkaTemplate<String, NotificationDto> kafkaTemplate;

    public void publish(NotificationDto notificationDto) {
        try {
            kafkaTemplate.send("notification-topic", notificationDto)
                    .get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new NotificationPublisherException(
                    format("Ошибка отправки уведомления в топик %s", e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }
}
