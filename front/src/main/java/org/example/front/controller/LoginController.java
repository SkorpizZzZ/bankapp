package org.example.front.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.front.dto.CreateUserDto;
import org.example.front.exception.FrontException;
import org.example.front.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        HttpServletRequest request,
                        Model model
    ) {
        if (error != null) {
            String errorMessage = (String) request.getSession().getAttribute("error");
            if (StringUtils.isNotBlank(errorMessage)) {
                if (errorMessage.contains("Service Unavailable")) {
                    model.addAttribute("error", "Аккаунт сервис не доступен");
                } else {
                    model.addAttribute("error", errorMessage);
                }
                request.getSession().removeAttribute("error");
            }
        }
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        return "signup";
    }

    @PostMapping("/signup")
    public String registration(@ModelAttribute @Valid CreateUserDto user, RedirectAttributes redirectAttributes) {
        try {
            userService.createUser(user);
        } catch (FrontException e) {
            redirectAttributes.addFlashAttribute("errors", Collections.singletonList(e.getMessage()));
            buildModel(user, redirectAttributes);
            return "redirect:/signup";
        }
        return "redirect:/login";
    }

    private static void buildModel(CreateUserDto user, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("login", user.login());
        redirectAttributes.addFlashAttribute("name", user.name());
        redirectAttributes.addFlashAttribute("birthdate", user.birthdate());
    }
}

