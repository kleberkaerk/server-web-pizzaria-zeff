package com.webservicepizzariazeff.www.exception_handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ExistingAddressExceptionHandlerTest {

    private static ExistingAddressExceptionHandler existingAddressExceptionHandler;

    @BeforeAll
    static void setExistingAddressExceptionHandler() {
        existingAddressExceptionHandler = ExistingAddressExceptionHandler.ExistingAddressExceptionHandlerBuilder.builder()
                .message("message")
                .build();
    }

    @Test
    void getMessage() {

        Assertions.assertThat(existingAddressExceptionHandler.getMessage())
                .isEqualTo("message");
    }
}