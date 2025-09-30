package org.company.account.feign;

import org.company.account.dto.CurrencyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "exchange", url = "${app.feign.exchange-service.url}")
public interface ExchangeFeign {

    @GetMapping
    List<CurrencyDto> findAll();
}
