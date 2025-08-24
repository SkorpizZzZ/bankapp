package org.company.notification.controller;

import lombok.RequiredArgsConstructor;
import org.company.notification.dto.NotificationDto;
import org.company.notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody NotificationDto notificationDto) {
        notificationService.create(notificationDto);
        return ResponseEntity.ok().build();
    }
}
