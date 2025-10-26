package org.company.notificationpublisher.configuration;

import org.company.notificationpublisher.dto.NotificationDto;
import org.company.notificationpublisher.publisher.NotificationPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class NotificationPublisherAutoConfiguration {

    @Bean
    public NotificationPublisher notificationPublisher(KafkaTemplate<String, NotificationDto> kafkaTemplate) {
        return new NotificationPublisher(kafkaTemplate);
    }
}
