package org.company.cash.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CashException.class)
    public ResponseEntity<String> handleAccountException(CashException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(exception.getMessage());
    }

}
