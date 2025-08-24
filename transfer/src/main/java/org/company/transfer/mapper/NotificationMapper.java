package org.company.transfer.mapper;

import org.company.transfer.domain.NotificationOutbox;
import org.company.transfer.dto.NotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "sourceId", source = "id")
    NotificationDto notificationOutboxToNotificationDto(NotificationOutbox notificationOutbox);
}
