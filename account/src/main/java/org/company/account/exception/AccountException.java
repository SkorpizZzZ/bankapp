package org.company.account.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountException extends RuntimeException{

    private final Integer status;

    public AccountException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
