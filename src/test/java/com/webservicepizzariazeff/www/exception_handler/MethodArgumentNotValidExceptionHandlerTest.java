package com.webservicepizzariazeff.www.exception_handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MethodArgumentNotValidExceptionHandlerTest {

    private static MethodArgumentNotValidExceptionHandler methodArgumentNotValidExceptionHandler;

    @BeforeAll
    static void setMethodArgumentNotValidExceptionHandler() {

        methodArgumentNotValidExceptionHandler = MethodArgumentNotValidExceptionHandler.MethodArgumentNotValidExceptionHandlerBuilder.builder()
                .message("message")
                .build();
    }

    @Test
    void getMessage() {

        Assertions.assertThat(methodArgumentNotValidExceptionHandler.getMessage())
                .isEqualTo("message");
    }
}