package com.webservicepizzariazeff.www.exception_handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExistingAddressExceptionHandlerTest {

    private ExistingAddressExceptionHandler existingAddressExceptionHandler;

    private ExistingAddressExceptionHandler sameExistingAddressExceptionHandler;

    private ExistingAddressExceptionHandler differentExistingAddressExceptionHandler;

    @BeforeEach
    void setExistingAddressExceptionHandler() {

        this.existingAddressExceptionHandler = ExistingAddressExceptionHandler.ExistingAddressExceptionHandlerBuilder.builder()
                .message("message1")
                .build();

        this.sameExistingAddressExceptionHandler = ExistingAddressExceptionHandler.ExistingAddressExceptionHandlerBuilder.builder()
                .message("message1")
                .build();

        this.differentExistingAddressExceptionHandler = ExistingAddressExceptionHandler.ExistingAddressExceptionHandlerBuilder.builder()
                .message("message2")
                .build();
    }

    @Test
    void getMessage() {

        Assertions.assertThat(this.existingAddressExceptionHandler.getMessage())
                .isEqualTo("message1");
    }

    @Test
    void testEquals(){

        Assertions.assertThat(this.existingAddressExceptionHandler.equals(this.sameExistingAddressExceptionHandler))
                .isTrue();

        Assertions.assertThat(this.existingAddressExceptionHandler.equals(this.differentExistingAddressExceptionHandler))
                .isFalse();
    }

    @Test
    void testHashCode(){

        Assertions.assertThat(this.existingAddressExceptionHandler)
                .hasSameHashCodeAs(this.sameExistingAddressExceptionHandler);

        Assertions.assertThat(this.existingAddressExceptionHandler.hashCode())
                .isNotEqualTo(this.differentExistingAddressExceptionHandler.hashCode());
    }
}