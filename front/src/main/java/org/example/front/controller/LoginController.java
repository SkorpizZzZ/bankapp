package org.example.front.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.front.dto.CreateUserDto;
import org.example.front.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                model.addAttribute("error", errorMessage);
                request.getSession().removeAttribute("error");
            }
        }
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String registration(@ModelAttribute @Valid CreateUserDto user) {
        userService.createUser(user);
        return "redirect:/login";
    }
}

