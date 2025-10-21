package org.company.notification.kafka.subscriber;

import lombok.RequiredArgsConstructor;
import org.company.notification.dto.NotificationDto;
import org.company.notification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "notification-topic")
    public void create(NotificationDto notificationDto) {
        notificationService.create(notificationDto);
    }
}
