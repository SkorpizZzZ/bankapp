package org.example.front.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FrontException extends RuntimeException {
    private String message;
    private Integer status;
}
