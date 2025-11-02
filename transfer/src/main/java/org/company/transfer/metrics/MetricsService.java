package org.company.transfer.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetricsService {

    private final MeterRegistry meterRegistry;

    public void incrementTransferFailure(String fromLogin, String toLogin, String fromAccount, String toAccount) {
        Counter.builder("transfer_failed_total")
                .tag("from_login", fromLogin)
                .tag("to_login", toLogin)
                .tag("from_account", fromAccount)
                .tag("to_account", toAccount)
                .description("Total number of failed money transfers")
                .register(meterRegistry)
                .increment();
    }

    public void incrementSuspiciousOperation(String fromLogin, String toLogin, String fromAccount, String toAccount) {
        Counter.builder("transfer_blocked_total")
                .tag("from_login", fromLogin)
                .tag("to_login", toLogin)
                .tag("from_account", fromAccount)
                .tag("to_account", toAccount)
                .description("Total number of blocked suspicious operations")
                .register(meterRegistry)
                .increment();
    }

    public void incrementNotificationPublishFailure(String login) {
        Counter.builder("notification_publish_failed_total")
                .tag("login", login)
                .description("Total number of failed notification publish attempts to Kafka")
                .register(meterRegistry)
                .increment();
    }
}
