package org.company.exchange.controller;

import lombok.RequiredArgsConstructor;
import org.company.exchange.dto.CurrencyDto;
import org.company.exchange.dto.RateDto;
import org.company.exchange.service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping("/rates")
    public ResponseEntity<List<RateDto>> findAllRates() {
        return ResponseEntity.ok(exchangeService.findAllRates());
    }

    @GetMapping
    public ResponseEntity<List<CurrencyDto>> findAll() {
        return ResponseEntity.ok(exchangeService.findAll());
    }

    @GetMapping("/{from}/{to}/{value}")
    public ResponseEntity<BigDecimal> exchange(
            @PathVariable("from") String from,
            @PathVariable("to") String to,
            @PathVariable("value") BigDecimal value
    ) {
        return ResponseEntity.ok(exchangeService.exchange(from, to, value));
    }
}
