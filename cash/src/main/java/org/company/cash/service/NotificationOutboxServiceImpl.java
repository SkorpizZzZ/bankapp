package org.company.cash.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.cash.domain.NotificationOutbox;
import org.company.cash.exception.CashException;
import org.company.cash.mapper.NotificationMapper;
import org.company.cash.repository.NotificationOutboxRepository;
import org.company.notificationpublisher.publisher.NotificationPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationOutboxServiceImpl implements NotificationOutboxService {

    private final NotificationOutboxRepository notificationOutboxRepository;

    private final NotificationPublisher notificationPublisher;

    private final NotificationMapper notificationMapper;

    @Override
    @Transactional
    public void createMessage(String login, String message) {
        log.info("Процесс сохранения уведомления {} для пользователя {} в notification_outbox", message, login);
        notificationOutboxRepository.save(
                NotificationOutbox.builder()
                        .login(login)
                        .message(message)
                        .messageTime(LocalDateTime.now())
                        .received(false)
                        .build()
        );
    }

    @Override
    @Transactional
    @Scheduled(fixedDelay = 60000)
    public void sendNotification() {
        log.info("Процесс отправки уведомлений");
        List<NotificationOutbox> notifications = notificationOutboxRepository.findAllByReceivedFalse();
        log.debug("Список не отправленных уведомлений {}", notifications);
        sendNotificationsChangeReceivedStatus(notifications);
    }

    private void sendNotificationsChangeReceivedStatus(List<NotificationOutbox> notifications) {
        List<NotificationOutbox> successfullySent = new ArrayList<>(notifications.size());
        for (NotificationOutbox notification : notifications) {
            try {
                notificationPublisher.publish(notificationMapper.notificationOutboxToNotificationDto(notification));
                notification.setReceived(true);
                notification.setReceivedTime(LocalDateTime.now());
                successfullySent.add(notification);
                log.info("Уведомление отправлено {}", notification);
            } catch (CashException e) {
                log.warn("Ошибка отправки уведомления в Kafka: {}", notification);
            }
        }
        notificationOutboxRepository.saveAll(successfullySent);
    }
}
