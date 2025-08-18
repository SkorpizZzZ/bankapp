package org.example.front.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.example.front.controller.UserController;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@ControllerAdvice(assignableTypes = UserController.class)
public class UserExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidExceptionUpdatePassword(
            MethodArgumentNotValidException exception,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ) {
        String requestUri = request.getRequestURI();

        if (requestUri.endsWith("/editPassword")) {
            return handlePasswordErrors(exception, redirectAttributes);
        } else if (requestUri.endsWith("/editUserAccounts")) {
            return handleUserAccountsErrors(exception, redirectAttributes);
        }
        return "redirect:/";
    }

    private String handleUserAccountsErrors(
            MethodArgumentNotValidException exception,
            RedirectAttributes redirectAttributes
    ) {
        List<String> errors = exception.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        redirectAttributes.addFlashAttribute("userAccountsErrors", errors);
        return "redirect:/";
    }

    private String handlePasswordErrors(
            MethodArgumentNotValidException exception,
            RedirectAttributes redirectAttributes
    ) {
        List<String> errors = exception.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        redirectAttributes.addFlashAttribute("passwordErrors", errors);
        return "redirect:/";
    }
}
