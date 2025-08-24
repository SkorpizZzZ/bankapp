package org.company.transfer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransferException extends RuntimeException{

    private String message;
    private Integer status;
}
