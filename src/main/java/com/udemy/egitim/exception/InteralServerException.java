package com.udemy.egitim.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InteralServerException extends RuntimeException {
    public InteralServerException(Throwable cause) {
        super(cause);
    }
}
