package org.company.transfer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(value = "gateway", contextId = "exchange")
public interface ExchangeFeign {

    @GetMapping("/exchange/{from}/{to}/{value}")
    BigDecimal exchange(
            @PathVariable("from") String from,
            @PathVariable("to") String to,
            @PathVariable("value") BigDecimal value
    );
}
