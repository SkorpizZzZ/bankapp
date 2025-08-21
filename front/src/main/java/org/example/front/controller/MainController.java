package org.example.front.controller;

import lombok.RequiredArgsConstructor;
import org.example.front.dto.CurrencyDto;
import org.example.front.dto.EditUserAccountDto;
import org.example.front.dto.UserDto;
import org.example.front.feign.AccountFeign;
import org.example.front.feign.ExchangeFeign;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final AccountFeign accountFeign;
    private final ExchangeFeign exchangeFeign;

    @GetMapping
    public String mainPage(Model model, Authentication authentication) {
        UserDto user = accountFeign.findUserByLogin(authentication.getName());
        List<CurrencyDto> currencies = exchangeFeign.findAll();
        List<EditUserAccountDto> users = accountFeign.findAllUsersData();
        model.addAttribute("login", user.login());
        model.addAttribute("name", user.name());
        model.addAttribute("birthdate", user.birthdate());
        model.addAttribute("accounts", user.accounts());
        model.addAttribute("currency", currencies);
        model.addAttribute("users", users);
        return "main";
    }
}
