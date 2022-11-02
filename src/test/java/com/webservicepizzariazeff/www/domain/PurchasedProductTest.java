package com.webservicepizzariazeff.www.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class PurchasedProductTest {

    private PurchasedProduct purchasedProduct;

    private PurchasedProduct samePurchasedProduct;

    private PurchasedProduct differentPurchasedProduct;

    private static Purchase purchase;

    @BeforeAll
    static void setPurchase(){

        purchase = Purchase.PurchaseBuilder.builder()
                .id(1L)
                .amount(new BigDecimal("10"))
                .dateAndTime("12/34/5678T90:12")
                .cardName("cardName")
                .isActive(true)
                .isFinished(false)
                .isDelivered(true)
                .isPaymentThroughTheWebsite(false)
                .user(User.UserBuilder.builder().build())
                .address(Address.AddressBuilder.builder().build())
                .purchasedProducts(null)
                .build();
    }

    @BeforeEach
    void setObjects() {

        this.purchasedProduct = PurchasedProduct.PurchasedProductBuilder.builder()
                .id(1L)
                .name("name")
                .purchase(purchase)
                .build();

        this.samePurchasedProduct = PurchasedProduct.PurchasedProductBuilder.builder()
                .id(1L)
                .name("name")
                .purchase(purchase)
                .build();

        this.differentPurchasedProduct = PurchasedProduct.PurchasedProductBuilder.builder()
                .id(2L)
                .name("nam2")
                .purchase(purchase)
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

    @Test
    void testToString() {

        Assertions.assertThat(this.purchasedProduct)
                .hasToString("PurchasedProduct{" +
                        "id=" + this.purchasedProduct.getId() +
                        ", name='" + this.purchasedProduct.getName() + '\'' +
                        '}');
    }

    @Test
    void testEquals(){

        Assertions.assertThat(this.purchasedProduct.equals(this.samePurchasedProduct))
                .isTrue();

        Assertions.assertThat(this.purchasedProduct.equals(this.differentPurchasedProduct))
                .isFalse();
    }


    @Test
    void testHashCode(){

        Assertions.assertThat(this.purchasedProduct)
                .hasSameHashCodeAs(this.samePurchasedProduct);
    }
}