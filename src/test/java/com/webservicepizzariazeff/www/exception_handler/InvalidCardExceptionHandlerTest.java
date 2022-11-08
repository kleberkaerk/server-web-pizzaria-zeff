package com.webservicepizzariazeff.www.exception_handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidCardExceptionHandlerTest {

    private InvalidCardExceptionHandler invalidCardExceptionHandler;
    private InvalidCardExceptionHandler sameInvalidCardExceptionHandler;
    private InvalidCardExceptionHandler differentInvalidCardExceptionHandler;

    @BeforeEach
    void setObjects() {

        invalidCardExceptionHandler = InvalidCardExceptionHandler.InvalidCardExceptionHandlerBuilder.builder()
                .message("message1")
                .build();

        sameInvalidCardExceptionHandler = InvalidCardExceptionHandler.InvalidCardExceptionHandlerBuilder.builder()
                .message("message1")
                .build();

        differentInvalidCardExceptionHandler = InvalidCardExceptionHandler.InvalidCardExceptionHandlerBuilder.builder()
                .message("message2")
                .build();
    }

    @Test
    void getMessage() {

        Assertions.assertThat(this.invalidCardExceptionHandler.getMessage())
                .isEqualTo("message1");
    }

    @Test
    void testEquals() {

        Assertions.assertThat(this.invalidCardExceptionHandler.equals(this.sameInvalidCardExceptionHandler))
                .isTrue();

        Assertions.assertThat(this.invalidCardExceptionHandler.equals(this.differentInvalidCardExceptionHandler))
                .isFalse();
    }

    @Test
    void testHashCode() {

        Assertions.assertThat(this.invalidCardExceptionHandler)
                .hasSameHashCodeAs(this.sameInvalidCardExceptionHandler);

        Assertions.assertThat(this.invalidCardExceptionHandler.hashCode())
                .isNotEqualTo(this.differentInvalidCardExceptionHandler.hashCode());
    }
}