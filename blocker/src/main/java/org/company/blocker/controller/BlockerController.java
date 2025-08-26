package org.company.blocker.controller;

import lombok.RequiredArgsConstructor;
import org.company.blocker.service.BlockerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BlockerController {

    private final BlockerService blockerService;

    @GetMapping
    public Boolean isSuspicious() {
        return blockerService.isSuspicious();
    }
}
