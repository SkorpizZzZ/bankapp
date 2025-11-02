package org.company.exchangegenerator.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetricsService {

    private final MeterRegistry meterRegistry;

    public void incrementExchangeRateUpdateFailure() {
        Counter.builder("exchange_rate_update_failed_total")
                .description("Total number of failed exchange rate updates")
                .register(meterRegistry)
                .increment();
    }
}
