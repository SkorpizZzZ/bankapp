package org.company.cash.controller;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.cash.dto.CashDto;
import org.company.cash.exception.CashException;
import org.company.cash.feign.AccountFeign;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class CashController {

    private final AccountFeign accountFeign;

    @PutMapping("/withdraw/{login}")
    ResponseEntity<Void> withdraw(@PathVariable("login") String login, @RequestBody CashDto cash) {
        try {
            accountFeign.withdraw(login, cash);
        } catch (FeignException.ServiceUnavailable e) {
            log.warn("Аккаунт сервис не доступен");
            throw new CashException("Аккаунт сервис не доступен", e.status());
        } catch (FeignException e) {
            log.warn(e.getMessage());
            throw new CashException(e.contentUTF8(), e.status());
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/deposit/{login}")
    ResponseEntity<Void> deposit(@PathVariable("login") String login, @RequestBody CashDto cash) {
        try {
            accountFeign.deposit(login, cash);
        } catch (FeignException.ServiceUnavailable e) {
            log.warn("Аккаунт сервис не доступен");
            throw new CashException("Аккаунт сервис не доступен", e.status());
        } catch (FeignException e) {
            log.warn(e.getMessage());
            throw new CashException(e.contentUTF8(), e.status());
        }
        return ResponseEntity.ok().build();
    }
}
