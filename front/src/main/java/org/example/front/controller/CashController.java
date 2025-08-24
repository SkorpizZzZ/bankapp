package org.example.front.controller;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.front.dto.CashDto;
import org.example.front.service.CashServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CashController {

    private final CashServiceImpl cashService;

    @PostMapping("/user/cash")
    public String processCash(
            @ModelAttribute CashDto cash,
            Authentication authentication,
            RedirectAttributes model
    ) {
        try {
            cashService.processCash(authentication.getName(), cash);
        } catch (FeignException.ServiceUnavailable e) {
            log.error("Кэш сервис не доступен");
            model.addFlashAttribute("cashErrors", Collections.singletonList("Кэш сервис не доступен"));
            return "redirect:/";
        } catch (FeignException e) {
            String errorContent = e.contentUTF8();
            log.warn(errorContent);
            model.addFlashAttribute("cashErrors", Collections.singletonList(errorContent));
            return "redirect:/";
        }
        return "redirect:/";
    }
}
