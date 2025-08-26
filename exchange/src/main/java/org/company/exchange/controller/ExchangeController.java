package org.company.exchange.controller;

import lombok.RequiredArgsConstructor;
import org.company.exchange.dto.CurrencyDto;
import org.company.exchange.dto.RateDto;
import org.company.exchange.service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PutMapping("/currencies")
    public ResponseEntity<Void> updateCurrencies(@RequestBody List<CurrencyDto> currencies) {
        exchangeService.updateCurrencies(currencies);
        return ResponseEntity.ok().build();
    }

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
