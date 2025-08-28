package org.company.cash.mapper;

import org.company.cash.domain.NotificationOutbox;
import org.company.cash.dto.NotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "sourceId", source = "id")
    @Mapping(target = "serviceName", constant = "cash")
    NotificationDto notificationOutboxToNotificationDto(NotificationOutbox notificationOutbox);
}
