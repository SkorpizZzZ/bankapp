package org.example.front.controller;

import lombok.RequiredArgsConstructor;
import org.example.front.dto.UserDto;
import org.example.front.feign.FeignAccount;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final FeignAccount feignAccount;

    @GetMapping
    public String mainPage(Model model, Authentication authentication) {
        UserDto user = feignAccount.findUserByLogin(authentication.getName());
        model.addAttribute("login", user.login());
        model.addAttribute("name", user.name());
        model.addAttribute("birthdate", user.birthdate());
        return "main";
    }
}
