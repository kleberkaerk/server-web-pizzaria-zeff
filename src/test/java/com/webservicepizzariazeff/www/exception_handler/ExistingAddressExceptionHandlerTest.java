package com.webservicepizzariazeff.www.exception_handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExistingAddressExceptionHandlerTest {

    private ExistingAddressExceptionHandler existingAddressExceptionHandler;

    @BeforeEach
    void setExistingAddressExceptionHandler() {

        this.existingAddressExceptionHandler = ExistingAddressExceptionHandler.ExistingAddressExceptionHandlerBuilder.builder()
                .message("message")
                .build();
    }

    @Test
    void getMessage() {

        Assertions.assertThat(this.existingAddressExceptionHandler.getMessage())
                .isEqualTo("message");
    }
}