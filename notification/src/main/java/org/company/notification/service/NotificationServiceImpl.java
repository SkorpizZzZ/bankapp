package org.company.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.notification.domain.Notification;
import org.company.notification.dto.NotificationDto;
import org.company.notification.mapper.NotificationMapper;
import org.company.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final NotificationMapper mapper;

    @Override
    @Transactional
    public void create(NotificationDto notificationDto) {
        log.info("Процесс добавления уведомления {}", notificationDto);
        Notification saved = notificationRepository.save(mapper.notificationDtoToEntity(notificationDto));
        log.info("Уведомление добавлено {}", saved);
    }
}
