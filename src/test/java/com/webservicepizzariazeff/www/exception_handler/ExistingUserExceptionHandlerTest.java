package com.webservicepizzariazeff.www.exception_handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExistingUserExceptionHandlerTest {

    ExistingUserExceptionHandler existingUserExceptionHandler;

    @BeforeEach
    void setExistingUserExceptionHandler() {

        this.existingUserExceptionHandler = ExistingUserExceptionHandler.ExistingUserExceptionHandlerBuilder.builder()
                .message("message")
                .build();
    }

    @Test
    void getMessage() {

        Assertions.assertThat(this.existingUserExceptionHandler.getMessage())
                .isEqualTo("message");
    }
}