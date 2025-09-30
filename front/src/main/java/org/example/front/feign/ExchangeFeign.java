package org.example.front.feign;

import org.example.front.dto.CurrencyDto;
import org.example.front.dto.RateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "exchange", url = "${app.feign.exchange-service.url}")
public interface ExchangeFeign {

    @GetMapping
    List<CurrencyDto> findAll();

    @GetMapping("/rates")
    List<RateDto> findAllRates();
}
