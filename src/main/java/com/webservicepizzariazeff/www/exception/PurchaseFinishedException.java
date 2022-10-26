package com.webservicepizzariazeff.www.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PurchaseFinishedException extends RuntimeException{

    public PurchaseFinishedException(String message) {
        super(message);
    }
}
