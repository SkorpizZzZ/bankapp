package org.company.transfer.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransferException.class)
    public ResponseEntity<String> handleAccountException(TransferException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(exception.getMessage());
    }
}
