package org.company.transfer.feign;

import org.company.transfer.dto.NotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "notification", url = "${app.feign.notification-service.url}")
public interface NotificationFeign {

    @PostMapping("/create")
    void create(@RequestBody NotificationDto notificationDto);
}
