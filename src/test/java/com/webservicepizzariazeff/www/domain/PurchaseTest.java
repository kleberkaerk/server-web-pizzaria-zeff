package com.webservicepizzariazeff.www.domain;

import org.apache.logging.log4j.LogManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

class PurchaseTest {

    private Purchase purchase;

    private Purchase samePurchase;

    private Purchase differentPurchase;

    private static User user;

    private static Address address;

    private static List<PurchasedProduct> purchasedProducts;

    static void setUser() {

        user = User.UserBuilder.builder()
                .id(1L)
                .name("name")
                .username("username")
                .password("password")
                .authorities("ROLE_USER")
                .build();
    }

    static void setAddress() {

        address = Address.AddressBuilder.builder()
                .id(1L)
                .user(user)
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .build();
    }

    static void setPurchasedProducts() {

        purchasedProducts = List.of(
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(1L)
                        .name("name1")
                        .purchase(Purchase.PurchaseBuilder.builder().id(1L).build())
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(2L)
                        .name("name2")
                        .purchase(Purchase.PurchaseBuilder.builder().id(1L).build())
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(3L)
                        .name("name3")
                        .purchase(Purchase.PurchaseBuilder.builder().id(1L).build())
                        .build()
        );
    }

    @BeforeAll
    static void initializeObjects() {

        setUser();
        setAddress();
        setPurchasedProducts();
    }

    @BeforeEach
    void setPurchase() {

        this.purchase = Purchase.PurchaseBuilder.builder()
                .id(1L)
                .amount(new BigDecimal("1.0"))
                .dateAndTime("12/34/5678T12:34")
                .cardName("cardName")
                .isActive(true)
                .isFinished(true)
                .isDelivered(true)
                .isPaymentThroughTheWebsite(true)
                .user(user)
                .address(address)
                .purchasedProducts(purchasedProducts)
                .build();

        samePurchase = Purchase.PurchaseBuilder.builder()
                .id(1L)
                .amount(new BigDecimal("1.0"))
                .dateAndTime("12/34/5678T12:34")
                .cardName("cardName")
                .isActive(true)
                .isFinished(true)
                .isDelivered(true)
                .isPaymentThroughTheWebsite(true)
                .user(user)
                .address(address)
                .purchasedProducts(purchasedProducts)
                .build();

        differentPurchase = Purchase.PurchaseBuilder.builder()
                .id(2L)
                .amount(new BigDecimal("2.0"))
                .dateAndTime("12/34/5678T12:34")
                .cardName("cardName2")
                .isActive(false)
                .isFinished(false)
                .isDelivered(false)
                .isPaymentThroughTheWebsite(false)
                .user(user)
                .address(address)
                .purchasedProducts(purchasedProducts)
                .build();
    }

    @Test
    void getId() {

        Assertions.assertThat(this.purchase.getId())
                .isEqualTo(1L);
    }

    @Test
    void getAmount() {

        Assertions.assertThat(this.purchase.getAmount())
                .isEqualTo(new BigDecimal("1.0"));
    }

    @Test
    void getDateAndTime() {

        Assertions.assertThat(this.purchase.getDateAndTime())
                .isEqualTo("12/34/5678T12:34");
    }

    @Test
    void getCardName() {

        Assertions.assertThat(this.purchase.getCardName())
                .isEqualTo("cardName");
    }

    @Test
    void isActive() {

        Assertions.assertThat(this.purchase.isActive())
                .isTrue();
    }

    @Test
    void isFinished() {

        Assertions.assertThat(this.purchase.isFinished())
                .isTrue();
    }

    @Test
    void isDelivered() {

        Assertions.assertThat(this.purchase.isDelivered())
                .isTrue();
    }

    @Test
    void isPaymentThroughTheWebsite() {

        Assertions.assertThat(this.purchase.isPaymentThroughTheWebsite())
                .isTrue();
    }

    @Test
    void getUser() {

        Assertions.assertThat(this.purchase.getUser())
                .isEqualTo(user);
    }

    @Test
    void getAddress() {

        Assertions.assertThat(this.purchase.getAddress())
                .isEqualTo(address);
    }

    @Test
    void getPurchasedProducts() {

        Assertions.assertThat(this.purchase.getPurchasedProducts())
                .isEqualTo(purchasedProducts);
    }

    @Test
    void prePersist() {

        Purchase purchase2 = Purchase.PurchaseBuilder.builder().build();

        purchase2.prePersist();

        Assertions.assertThat(purchase2.isActive())
                .isTrue();

        Assertions.assertThat(purchase2.isFinished())
                .isFalse();

        Assertions.assertThat(purchase2.isDelivered())
                .isFalse();

        Assertions.assertThat(purchase2.getDateAndTime())
                .isEqualTo(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm")));
    }

    @Test
    void testToString() {

        LogManager.getLogger(PurchaseTest.class)
                .info(this.purchase);

        Assertions.assertThat(this.purchase)
                .hasToString("Purchase{" +
                        "id=" + this.purchase.getId() +
                        ", amount=" + this.purchase.getAmount() +
                        ", dateAndTime='" + this.purchase.getDateAndTime() + '\'' +
                        ", cardName='" + this.purchase.getCardName() + '\'' +
                        ", isActive=" + this.purchase.isActive() +
                        ", isFinished=" + this.purchase.isFinished() +
                        ", isDelivered=" + this.purchase.isDelivered() +
                        ", isPaymentThroughTheWebsite=" + this.purchase.isPaymentThroughTheWebsite() +
                        ", purchasedProducts=" + this.purchase.getPurchasedProducts() +
                        ", user=" + this.purchase.getUser() +
                        ", address=" + this.purchase.getAddress() +
                        '}');
    }

    @Test
    void testEquals(){

        Assertions.assertThat(this.purchase.equals(this.samePurchase))
                .isTrue();

        Assertions.assertThat(this.purchase.equals(this.differentPurchase))
                .isFalse();
    }

    @Test
    void testHashCode(){

        Assertions.assertThat(this.purchase)
                .hasSameHashCodeAs(this.samePurchase);
    }
}