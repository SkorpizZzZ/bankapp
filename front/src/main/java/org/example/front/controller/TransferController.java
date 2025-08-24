package org.example.front.controller;

import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.front.dto.TransferDto;
import org.example.front.feign.TransferFeign;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class TransferController {

    private final TransferFeign transferFeign;

    @PostMapping("/transfer")
    public String transfer(
            @ModelAttribute @Valid TransferDto transferDto,
            Authentication authentication,
            RedirectAttributes model
    ) {
        try {
            transferFeign.transfer(authentication.getName(), transferDto);
        } catch (FeignException.ServiceUnavailable e) {
            log.error("Трансфер сервис не доступен");
            model.addFlashAttribute(
                    authentication.getName().equals(transferDto.toLogin()) ?
                            "transferErrors" :
                            "transferOtherErrors",
                    Collections.singletonList("Трансфер сервис не доступен")
            );
            return "redirect:/";
        } catch (FeignException e) {
            String errorContent = e.contentUTF8();
            log.warn(errorContent);
            model.addFlashAttribute(
                    authentication.getName().equals(transferDto.toLogin()) ?
                            "transferErrors" :
                            "transferOtherErrors",
                    Collections.singletonList(errorContent)
            );
            return "redirect:/";
        }
        return "redirect:/";
    }
}
