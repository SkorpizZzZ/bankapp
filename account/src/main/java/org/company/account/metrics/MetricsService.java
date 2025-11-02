package org.company.account.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetricsService {

    private final MeterRegistry meterRegistry;

    public void incrementLoginSuccess(String login) {
        Counter.builder("user_login_total")
                .tag("status", "success")
                .tag("login", login)
                .description("Total number of successful user logins")
                .register(meterRegistry)
                .increment();
    }

    public void incrementLoginFailure(String login) {
        Counter.builder("user_login_total")
                .tag("status", "failure")
                .tag("login", login)
                .description("Total number of failed user login attempts")
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
