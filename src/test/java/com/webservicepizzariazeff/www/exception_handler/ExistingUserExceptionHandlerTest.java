package com.webservicepizzariazeff.www.exception_handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ExistingUserExceptionHandlerTest {

    private static ExistingUserExceptionHandler existingUserExceptionHandler;

    @BeforeAll
    static void setExistingUserExceptionHandler() {

        existingUserExceptionHandler = ExistingUserExceptionHandler.ExistingUserExceptionHandlerBuilder.builder()
                .message("message")
                .build();
    }

    @Test
    void getMessage() {

        Assertions.assertThat(existingUserExceptionHandler.getMessage())
                .isEqualTo("message");
    }
}