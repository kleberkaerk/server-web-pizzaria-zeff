package com.webservicepizzariazeff.www.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ProductTest {

    private Product product;

    private Product sameProduct;

    private Product differentProduct;

    @BeforeEach
    void setObjects() {

        this.product = Product.ProductBuilder.builder()
                .id(1L)
                .name("name")
                .description("description")
                .price(new BigDecimal("10"))
                .type(Type.SALTY_PIZZA)
                .priceRating(PriceRating.REGULAR_PRICE)
                .image("/image.png")
                .build();

        this.sameProduct = Product.ProductBuilder.builder()
                .id(1L)
                .name("name")
                .description("description")
                .price(new BigDecimal("10"))
                .type(Type.SALTY_PIZZA)
                .priceRating(PriceRating.REGULAR_PRICE)
                .image("/image.png")
                .build();

        this.differentProduct = Product.ProductBuilder.builder()
                .id(2L)
                .name("name2")
                .description("description2")
                .price(new BigDecimal("10"))
                .type(Type.SALTY_PIZZA)
                .priceRating(PriceRating.REGULAR_PRICE)
                .image("/image.png")
                .build();
    }

    @Test
    void getId() {

        Assertions.assertThat(product.getId())
                .isEqualTo(1L);
    }

    @Test
    void getName() {

        Assertions.assertThat(product.getName())
                .isEqualTo("name");
    }

    @Test
    void getDescription() {

        Assertions.assertThat(product.getDescription())
                .isEqualTo("description");
    }

    @Test
    void getPrice() {

        Assertions.assertThat(product.getPrice())
                .isEqualTo(new BigDecimal("10"));
    }

    @Test
    void getType() {

        Assertions.assertThat(product.getType())
                .isEqualTo(Type.SALTY_PIZZA);
    }

    @Test
    void getPriceRating() {

        Assertions.assertThat(product.getPriceRating())
                .isEqualTo(PriceRating.REGULAR_PRICE);
    }

    @Test
    void getImage() {

        Assertions.assertThat(product.getImage())
                .isEqualTo("/image.png");
    }

    @Test
    void testEquals() {

        Assertions.assertThat(product.equals(this.sameProduct))
                .isTrue();

        Assertions.assertThat(product.equals(this.differentProduct))
                .isFalse();
    }

    @Test
    void testHashCode() {

        Assertions.assertThat(product)
                .hasSameHashCodeAs(this.sameProduct);
    }

    @Test
    void testToString() {

        Assertions.assertThat(product)
                .hasToString("Product{id=1, name='name', description='description', price=10, type=SALTY_PIZZA, priceRating=REGULAR_PRICE, image='/image.png'}");
    }
}