package org.example.front.feign;

import org.example.front.dto.CurrencyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "gateway", contextId = "exchange")
public interface ExchangeFeign {

    @GetMapping("/exchange")
    List<CurrencyDto> findAll();
}
