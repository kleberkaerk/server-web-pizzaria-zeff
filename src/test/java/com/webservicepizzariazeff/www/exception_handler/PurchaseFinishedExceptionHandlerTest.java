package com.webservicepizzariazeff.www.exception_handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseFinishedExceptionHandlerTest {

    private PurchaseFinishedExceptionHandler purchaseFinishedExceptionHandler;

    @BeforeEach
    void setPurchaseFinishedExceptionHandler(){

        this.purchaseFinishedExceptionHandler = PurchaseFinishedExceptionHandler.PurchaseFinishedExceptionHandlerBuilder.builder()
                .message("message")
                .build();
    }

    @Test
    void getMessage() {

        Assertions.assertThat(this.purchaseFinishedExceptionHandler.getMessage())
                .isEqualTo("message");
    }
}