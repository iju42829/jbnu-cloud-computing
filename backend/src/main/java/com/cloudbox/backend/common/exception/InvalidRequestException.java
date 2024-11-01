package com.cloudbox.backend.common.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class InvalidRequestException extends RuntimeException {

    private final BindingResult bindingResult;

    public InvalidRequestException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public InvalidRequestException(String message, BindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }

    public InvalidRequestException(String message, Throwable cause, BindingResult bindingResult) {
        super(message, cause);
        this.bindingResult = bindingResult;
    }

    public InvalidRequestException(Throwable cause, BindingResult bindingResult) {
        super(cause);
        this.bindingResult = bindingResult;
    }
}
