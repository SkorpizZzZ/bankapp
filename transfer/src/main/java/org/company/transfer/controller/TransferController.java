package org.company.transfer.controller;

import lombok.RequiredArgsConstructor;
import org.company.transfer.dto.TransferDto;
import org.company.transfer.dto.TransferExchangeDto;
import org.company.transfer.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/{login}")
    public ResponseEntity<TransferExchangeDto> transfer(@PathVariable("login") String login, @RequestBody TransferDto transferDto) {
        return ResponseEntity.ok(transferService.transfer(login, transferDto));
    }
}
