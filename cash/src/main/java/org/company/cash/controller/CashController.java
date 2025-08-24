package org.company.cash.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.cash.dto.CashDto;
import org.company.cash.service.CashService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class CashController {

    private final CashService cashService;

    @PutMapping("/withdraw/{login}")
    ResponseEntity<Void> withdraw(@PathVariable("login") String login, @RequestBody CashDto cash) {
        cashService.withdraw(login, cash);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/deposit/{login}")
    ResponseEntity<Void> deposit(@PathVariable("login") String login, @RequestBody CashDto cash) {
        cashService.deposit(login, cash);
        return ResponseEntity.ok().build();
    }
}
