package org.company.notificationpublisher.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotificationPublisherException extends RuntimeException {

    private String message;
    private Integer status;
}
