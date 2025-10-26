package org.company.transfer.mapper;

import org.company.notificationpublisher.dto.NotificationDto;
import org.company.transfer.domain.NotificationOutbox;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "sourceId", source = "id")
    @Mapping(target = "serviceName", constant = "transfer")
    NotificationDto notificationOutboxToNotificationDto(NotificationOutbox notificationOutbox);
}
