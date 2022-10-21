package com.webservicepizzariazeff.www.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchasedProductTest {

    private PurchasedProduct purchasedProduct;

    @BeforeEach
    void setObjects() {

        this.purchasedProduct = PurchasedProduct.PurchasedProductBuilder.builder()
                .id(1L)
                .name("name")
                .purchase(Purchase.PurchaseBuilder.builder()
                        .id(1L)
                        .build())
                .build();
    }

    @Test
    void getName() {

        Assertions.assertThat(this.purchasedProduct.getName())
                .isEqualTo("name");
    }

    @Test
    void getId() {

        Assertions.assertThat(this.purchasedProduct.getId())
                .isEqualTo(1L);
    }
}