package org.company.account.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.account.domain.NotificationOutbox;
import org.company.account.feign.NotificationFeign;
import org.company.account.mapper.NotificationMapper;
import org.company.account.repository.NotificationOutboxRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationOutboxServiceImpl implements NotificationOutboxService {

    private final NotificationOutboxRepository notificationOutboxRepository;

    private final NotificationFeign notificationFeign;

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
        notificationOutboxRepository.saveAll(notifications);
    }

    private void sendNotificationsChangeReceivedStatus(List<NotificationOutbox> notifications) {
        for (NotificationOutbox notification : notifications) {
            try {
                notificationFeign.create(notificationMapper.notificationOutboxToNotificationDto(notification));
                notification.setReceived(true);
                notification.setReceivedTime(LocalDateTime.now());
                log.info("Уведомление отправлено {}", notification);
            } catch (FeignException.ServiceUnavailable e) {
                log.warn("Сервис уведомлений не доступен");
            } catch (FeignException e) {
                log.warn(e.contentUTF8());
            }
        }
    }
}
