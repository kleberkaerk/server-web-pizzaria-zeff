package com.webservicepizzariazeff.www.exception_handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MethodArgumentNotValidExceptionHandlerTest {

    private MethodArgumentNotValidExceptionHandler methodArgumentNotValidExceptionHandler;

    private MethodArgumentNotValidExceptionHandler sameMethodArgumentNotValidExceptionHandler;

    private MethodArgumentNotValidExceptionHandler differentMethodArgumentNotValidExceptionHandler;

    @BeforeEach
    void setObjects() {

        this.methodArgumentNotValidExceptionHandler = MethodArgumentNotValidExceptionHandler.MethodArgumentNotValidExceptionHandlerBuilder.builder()
                .message("message1")
                .build();

        this.sameMethodArgumentNotValidExceptionHandler = MethodArgumentNotValidExceptionHandler.MethodArgumentNotValidExceptionHandlerBuilder.builder()
                .message("message1")
                .build();

        this.differentMethodArgumentNotValidExceptionHandler = MethodArgumentNotValidExceptionHandler.MethodArgumentNotValidExceptionHandlerBuilder.builder()
                .message("message2")
                .build();
    }

    @Test
    void getMessage() {

        Assertions.assertThat(this.methodArgumentNotValidExceptionHandler.getMessage())
                .isEqualTo("message1");
    }

    @Test
    void testEquals(){

        Assertions.assertThat(this.methodArgumentNotValidExceptionHandler.equals(this.sameMethodArgumentNotValidExceptionHandler))
                .isTrue();

        Assertions.assertThat(this.methodArgumentNotValidExceptionHandler.equals(this.differentMethodArgumentNotValidExceptionHandler))
                .isFalse();
    }

    @Test
    void tesHashCode(){

        Assertions.assertThat(this.methodArgumentNotValidExceptionHandler)
                .hasSameHashCodeAs(this.sameMethodArgumentNotValidExceptionHandler);

        Assertions.assertThat(this.methodArgumentNotValidExceptionHandler.hashCode())
                .isNotEqualTo(this.differentMethodArgumentNotValidExceptionHandler.hashCode());
    }
}