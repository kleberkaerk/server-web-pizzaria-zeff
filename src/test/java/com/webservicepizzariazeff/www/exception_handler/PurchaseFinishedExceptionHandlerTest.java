package com.webservicepizzariazeff.www.exception_handler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseFinishedExceptionHandlerTest {

    private PurchaseFinishedExceptionHandler purchaseFinishedExceptionHandler;

    private PurchaseFinishedExceptionHandler samePurchaseFinishedExceptionHandler;

    private PurchaseFinishedExceptionHandler differentPurchaseFinishedExceptionHandler;

    @BeforeEach
    void setPurchaseFinishedExceptionHandler() {

        this.purchaseFinishedExceptionHandler = PurchaseFinishedExceptionHandler.PurchaseFinishedExceptionHandlerBuilder.builder()
                .message("message1")
                .build();

        this.samePurchaseFinishedExceptionHandler = PurchaseFinishedExceptionHandler.PurchaseFinishedExceptionHandlerBuilder.builder()
                .message("message1")
                .build();

        this.differentPurchaseFinishedExceptionHandler = PurchaseFinishedExceptionHandler.PurchaseFinishedExceptionHandlerBuilder.builder()
                .message("message2")
                .build();
    }

    @Test
    void getMessage() {

        Assertions.assertThat(this.purchaseFinishedExceptionHandler.getMessage())
                .isEqualTo("message1");
    }

    @Test
    void testEquals(){

        Assertions.assertThat(this.purchaseFinishedExceptionHandler.equals(this.samePurchaseFinishedExceptionHandler))
                .isTrue();

        Assertions.assertThat(this.purchaseFinishedExceptionHandler.equals(this.differentPurchaseFinishedExceptionHandler))
                .isFalse();
    }

    @Test
    void tesHashCode(){

        Assertions.assertThat(this.purchaseFinishedExceptionHandler)
                .hasSameHashCodeAs(this.samePurchaseFinishedExceptionHandler);

        Assertions.assertThat(this.purchaseFinishedExceptionHandler.hashCode())
                .isNotEqualTo(this.differentPurchaseFinishedExceptionHandler.hashCode());
    }
}