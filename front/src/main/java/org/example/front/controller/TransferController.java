package org.example.front.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.front.dto.TransferDto;
import org.example.front.feign.TransferFeign;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class TransferController {

    private final TransferFeign transferFeign;

    @PostMapping("/transfer")
    public String transfer(
            @ModelAttribute @Valid TransferDto transferDto,
            Authentication authentication
    ) {
        transferFeign.transfer(authentication.getName(), transferDto);
        return "redirect:/";
    }
}
