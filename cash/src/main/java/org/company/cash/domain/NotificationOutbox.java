package org.company.cash.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "notification_outbox")
public class NotificationOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;
    @Column(nullable = false, updatable = false)
    private String login;
    @Column(nullable = false, updatable = false)
    private String message;
    @Column(name = "message_time", nullable = false, updatable = false)
    private LocalDateTime messageTime = LocalDateTime.now();
    @Column(nullable = false)
    private Boolean received = false;
    @Column(name = "received_time")
    private LocalDateTime receivedTime;
}
