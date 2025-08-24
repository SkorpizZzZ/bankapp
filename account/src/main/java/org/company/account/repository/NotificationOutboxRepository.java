package org.company.account.repository;

import org.company.account.domain.NotificationOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationOutboxRepository extends JpaRepository<NotificationOutbox, Long> {

    List<NotificationOutbox> findAllByReceivedFalse();
}
