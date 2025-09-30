package org.company.exchangegenerator.feign;

import org.company.exchangegenerator.dto.CurrencyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "exchange", url = "${app.feign.exchange-service.url}")
public interface ExchangeFeign {

    @GetMapping
    List<CurrencyDto> findAll();

    @PutMapping("/currencies")
    void updateCurrencies(@RequestBody List<CurrencyDto> updatedCurrencies);
}
