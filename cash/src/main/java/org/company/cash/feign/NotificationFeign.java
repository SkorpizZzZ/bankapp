package org.company.cash.feign;

import org.company.cash.dto.NotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "gateway", contextId = "notification")
public interface NotificationFeign {

    @PostMapping("/notification/create")
    void create(@RequestBody NotificationDto notificationDto);
}
