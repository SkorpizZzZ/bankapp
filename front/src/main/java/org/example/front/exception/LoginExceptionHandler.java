package org.example.front.exception;

import org.example.front.controller.LoginController;
import org.example.front.dto.CreateUserDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@ControllerAdvice(assignableTypes = LoginController.class)
public class LoginExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            RedirectAttributes redirectAttributes
    ) {
        List<String> errors = exception.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        redirectAttributes.addFlashAttribute("errors", errors);
        if (exception.getBindingResult().getTarget() != null) {
            CreateUserDto userDto = (CreateUserDto) exception.getBindingResult().getTarget();
            redirectAttributes.addFlashAttribute("login", userDto.login());
            redirectAttributes.addFlashAttribute("name", userDto.name());
            redirectAttributes.addFlashAttribute("birthdate", userDto.birthdate());
        }
        return "redirect:/signup";
    }
}
