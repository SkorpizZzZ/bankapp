package org.company.notification.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;
    @Column(name = "source_id", nullable = false, updatable = false, unique = true)
    private Long sourceId;
    @Column(nullable = false, updatable = false)
    private String login;
    @Column(nullable = false, updatable = false)
    private String message;
    @Column(name = "message_time", nullable = false, updatable = false)
    private LocalDateTime messageTime;

}