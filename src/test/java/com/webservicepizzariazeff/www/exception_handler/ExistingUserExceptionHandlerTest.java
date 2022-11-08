package com.webservicepizzariazeff.www.exception_handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExistingUserExceptionHandlerTest {

    private ExistingUserExceptionHandler existingUserExceptionHandler;
    private ExistingUserExceptionHandler sameExistingUserExceptionHandler;
    private ExistingUserExceptionHandler differentExistingUserExceptionHandler;

    @BeforeEach
    void setObjects() {

        this.existingUserExceptionHandler = ExistingUserExceptionHandler.ExistingUserExceptionHandlerBuilder.builder()
                .message("message1")
                .build();

        this.sameExistingUserExceptionHandler = ExistingUserExceptionHandler.ExistingUserExceptionHandlerBuilder.builder()
                .message("message1")
                .build();

        this.differentExistingUserExceptionHandler = ExistingUserExceptionHandler.ExistingUserExceptionHandlerBuilder.builder()
                .message("message2")
                .build();
    }

    @Test
    void getMessage() {

        Assertions.assertThat(this.existingUserExceptionHandler.getMessage())
                .isEqualTo("message1");
    }

    @Test
    void testEquals(){

        Assertions.assertThat(this.existingUserExceptionHandler.equals(this.sameExistingUserExceptionHandler))
                .isTrue();

        Assertions.assertThat(this.existingUserExceptionHandler.equals(this.differentExistingUserExceptionHandler))
                .isFalse();
    }

    @Test
    void testHashCode(){

        Assertions.assertThat(this.existingUserExceptionHandler)
                        .hasSameHashCodeAs(this.sameExistingUserExceptionHandler);

        Assertions.assertThat(this.existingUserExceptionHandler.hashCode())
                .isNotEqualTo(this.differentExistingUserExceptionHandler.hashCode());
    }
}