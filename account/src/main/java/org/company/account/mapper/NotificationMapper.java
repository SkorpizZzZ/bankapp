package org.company.account.mapper;

import org.company.account.domain.NotificationOutbox;
import org.company.account.dto.NotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "sourceId", source = "id")
    NotificationDto notificationOutboxToNotificationDto(NotificationOutbox notificationOutbox);
}
