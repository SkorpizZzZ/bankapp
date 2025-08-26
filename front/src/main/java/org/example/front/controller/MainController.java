package org.example.front.controller;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.example.front.dto.CurrencyDto;
import org.example.front.dto.EditUserAccountDto;
import org.example.front.dto.UserDto;
import org.example.front.feign.AccountFeign;
import org.example.front.feign.ExchangeFeign;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final AccountFeign accountFeign;
    private final ExchangeFeign exchangeFeign;

    @GetMapping
    public String mainPage(Model model, Authentication authentication) {
        UserDto user = null;
        List<EditUserAccountDto> users = null;
        List<CurrencyDto> currencies = null;
        List<String> globalErrors = new ArrayList<>();
        try {
            user = accountFeign.findUserByLogin(authentication.getName());
            users = accountFeign.findAllUsersData().stream()
                    .filter(editUserAccountDto ->
                            !StringUtils.equals(
                                    editUserAccountDto.login(),
                                    authentication.getName()
                            )
                    )
                    .toList();
        } catch (FeignException.ServiceUnavailable e) {
            log.warn("Аккаунт сервис не доступен");
            globalErrors.add("Аккаунт сервис не доступен");
            model.addAttribute("globalErrors", globalErrors);
        } catch (FeignException e) {
            log.warn(e.contentUTF8());
            globalErrors.add(e.contentUTF8());
            model.addAttribute("globalErrors", globalErrors);
        }
        try {
            currencies = exchangeFeign.findAll().stream()
                    .sorted(Comparator.comparing(CurrencyDto::currencyId))
                    .toList();
        } catch (FeignException.ServiceUnavailable e) {
            log.warn("Сервис валют не доступен");
            globalErrors.add("Сервис валют не доступен");
            model.addAttribute("globalErrors", globalErrors);
        } catch (FeignException e) {
            log.warn(e.contentUTF8());
            globalErrors.add(e.contentUTF8());
            model.addAttribute("globalErrors", globalErrors);
        }
        buildModel(model,
                Objects.isNull(user) ?
                        new UserDto(null, authentication.getName(), null, null, null, Collections.emptyList()) :
                        user
        );
        model.addAttribute("currency", CollectionUtils.isNotEmpty(currencies) ? currencies : Collections.emptyList());
        model.addAttribute("users", CollectionUtils.isNotEmpty(users) ? users : Collections.emptyList());
        return "main";
    }

    private static void buildModel(Model model, UserDto user) {
        model.addAttribute("login", user.login());
        model.addAttribute("name", user.name());
        model.addAttribute("birthdate", user.birthdate());
        model.addAttribute("accounts", user.accounts());
    }
}
