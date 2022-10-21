package com.webservicepizzariazeff.www.exception_handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NullPointerExceptionHandlerTest {

    private NullPointerExceptionHandler nullPointerExceptionHandler;

    @BeforeEach
    void setNullPointerExceptionHandler(){

        this.nullPointerExceptionHandler = NullPointerExceptionHandler.NullPointerExceptionHandlerBuilder.builder()
                .message("message")
                .build();
    }

    @Test
    void getMessage() {

        Assertions.assertThat(this.nullPointerExceptionHandler.getMessage())
                .isEqualTo("message");
    }
}