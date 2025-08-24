package org.company.notification.mapper;

import org.company.notification.domain.Notification;
import org.company.notification.dto.NotificationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    Notification notificationDtoToEntity(NotificationDto dto);
}
