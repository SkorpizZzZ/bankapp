package org.company.account.feign;

import org.company.account.dto.NotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "gateway", contextId = "notification")
public interface NotificationFeign {

    @PostMapping("/notification/create")
    ResponseEntity<Void> create(@RequestBody NotificationDto notificationDto);
}
