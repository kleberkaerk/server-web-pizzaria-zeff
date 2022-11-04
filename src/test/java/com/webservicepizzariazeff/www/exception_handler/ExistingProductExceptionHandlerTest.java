package com.webservicepizzariazeff.www.exception_handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExistingProductExceptionHandlerTest {

    private ExistingProductExceptionHandler existingProductExceptionHandler;

    private ExistingProductExceptionHandler sameExistingProductExceptionHandler;

    private ExistingProductExceptionHandler differentExistingProductExceptionHandler;


    @BeforeEach
    void setExistingProductExceptionHandler(){

        this.existingProductExceptionHandler = ExistingProductExceptionHandler.ExistingProductExceptionHandlerBuilder.builder()
                .message("message1")
                .build();

        this.sameExistingProductExceptionHandler = ExistingProductExceptionHandler.ExistingProductExceptionHandlerBuilder.builder()
                .message("message1")
                .build();

        this.differentExistingProductExceptionHandler = ExistingProductExceptionHandler.ExistingProductExceptionHandlerBuilder.builder()
                .message("message2")
                .build();
    }

    @Test
    void getMessage() {

        Assertions.assertThat(this.existingProductExceptionHandler.getMessage())
                .isEqualTo("message1");
    }

    @Test
    void testEquals(){

        Assertions.assertThat(this.existingProductExceptionHandler.equals(this.sameExistingProductExceptionHandler))
                .isTrue();

        Assertions.assertThat(this.existingProductExceptionHandler.equals(this.differentExistingProductExceptionHandler))
                .isFalse();
    }

    @Test
    void testHashCode(){

        Assertions.assertThat(this.existingProductExceptionHandler)
                .hasSameHashCodeAs(this.sameExistingProductExceptionHandler);

        Assertions.assertThat(this.existingProductExceptionHandler.hashCode())
                .isNotEqualTo(this.differentExistingProductExceptionHandler.hashCode());
    }
}