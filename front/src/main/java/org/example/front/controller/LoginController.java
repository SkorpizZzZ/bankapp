package org.example.front.controller;

import jakarta.validation.Valid;
import org.example.front.dto.CreateUserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String registration(@ModelAttribute @Valid CreateUserDto user) {
        return "redirect:/login";
    }
}

