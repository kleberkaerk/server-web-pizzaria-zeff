package com.webservicepizzariazeff.www.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Validator {

    private Validator() {
    }

    public static void validateAcceptLanguage(String acceptLanguage) {

        if (!acceptLanguage.equals("pt-BR") && !acceptLanguage.equals("en-US")) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
