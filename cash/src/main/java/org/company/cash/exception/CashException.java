package org.company.cash.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CashException extends RuntimeException {
    private String message;
    private Integer status;
}
