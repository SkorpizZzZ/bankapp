package org.example.front.controller;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.front.dto.RateDto;
import org.example.front.feign.ExchangeFeign;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
public class RateController {

    private final ExchangeFeign exchangeFeign;

    @GetMapping
    public List<RateDto> findAll(Model model) {
        List<RateDto> rates = new ArrayList<>();
        try {
            rates = exchangeFeign.findAllRates();
        } catch (FeignException.ServiceUnavailable e) {
            log.warn("Сервис валют не доступен");
            model.addAttribute("globalErrors", Collections.singletonList("Сервис валют не доступен"));
        } catch (FeignException e) {
            log.warn(e.contentUTF8());
            model.addAttribute("globalErrors", Collections.singletonList(e.contentUTF8()));
        }
        return rates;
    }
}
