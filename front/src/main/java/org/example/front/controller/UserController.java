package org.example.front.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.front.dto.EditUserAccountDto;
import org.example.front.dto.UpdatePasswordDto;
import org.example.front.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/editPassword")
    public String editPassword(@ModelAttribute @Valid UpdatePasswordDto user, Authentication authentication) {
        userService.updatePassword(authentication.getName(), user);
        return "redirect:/login";
    }

    @PostMapping("/editUserAccounts")
    public String editUserAccounts(@ModelAttribute @Valid EditUserAccountDto dto, Authentication authentication) {
        userService.updateUserAccounts(authentication.getName(), dto);
        return "redirect:/";
    }
}
