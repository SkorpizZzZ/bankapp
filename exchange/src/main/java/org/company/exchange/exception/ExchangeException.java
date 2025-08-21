package org.company.exchange.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExchangeException extends RuntimeException {

    private final HttpStatus status;

    public ExchangeException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
