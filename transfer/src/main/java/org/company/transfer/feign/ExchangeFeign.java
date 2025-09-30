package org.company.transfer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(value = "exchange", url = "${app.feign.exchange-service.url}")
public interface ExchangeFeign {

    @GetMapping("/{from}/{to}/{value}")
    BigDecimal exchange(
            @PathVariable("from") String from,
            @PathVariable("to") String to,
            @PathVariable("value") BigDecimal value
    );
}
