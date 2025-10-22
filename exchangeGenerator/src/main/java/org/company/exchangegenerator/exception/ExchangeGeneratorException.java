package org.company.exchangegenerator.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExchangeGeneratorException extends RuntimeException {

    private final HttpStatus status;

    public ExchangeGeneratorException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
