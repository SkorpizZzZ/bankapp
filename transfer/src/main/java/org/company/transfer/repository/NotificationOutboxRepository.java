package org.company.transfer.repository;

import org.company.transfer.domain.NotificationOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationOutboxRepository extends JpaRepository<NotificationOutbox, Long> {

    List<NotificationOutbox> findAllByReceivedFalse();
}
