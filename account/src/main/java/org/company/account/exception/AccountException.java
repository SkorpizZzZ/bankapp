package org.company.account.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AccountException extends RuntimeException{

    private final HttpStatus status;

    public AccountException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
