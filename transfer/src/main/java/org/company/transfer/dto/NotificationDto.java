package org.company.transfer.dto;

import java.time.LocalDateTime;

public record NotificationDto(
        Long sourceId,
        String login,
        String message,
        LocalDateTime messageTime
) {
}
