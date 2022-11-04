package com.webservicepizzariazeff.www.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExistingProductException extends RuntimeException {

    public ExistingProductException(String message) {
        super(message);
    }
}
