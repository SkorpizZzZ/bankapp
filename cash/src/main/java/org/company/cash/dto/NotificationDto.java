package org.company.cash.dto;

import java.time.LocalDateTime;

public record NotificationDto(
        Long sourceId,
        String serviceName,
        String login,
        String message,
        LocalDateTime messageTime
) {
}
