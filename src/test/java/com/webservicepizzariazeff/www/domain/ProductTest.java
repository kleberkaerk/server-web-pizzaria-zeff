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
                .isStocked(true)
                .image("/image.png")
                .build();

        this.sameProduct = Product.ProductBuilder.builder()
                .id(1L)
                .name("name")
                .description("description")
                .price(new BigDecimal("10"))
                .type(Type.SALTY_PIZZA)
                .priceRating(PriceRating.REGULAR_PRICE)
                .isStocked(true)
                .image("/image.png")
                .build();

        this.differentProduct = Product.ProductBuilder.builder()
                .id(2L)
                .name("name2")
                .description("description2")
                .price(new BigDecimal("10"))
                .type(Type.SALTY_PIZZA)
                .priceRating(PriceRating.REGULAR_PRICE)
                .isStocked(false)
                .image("/image.png")
                .build();
    }

    @Test
    void getId() {

        Assertions.assertThat(this.product.getId())
                .isEqualTo(1L);
    }

    @Test
    void getName() {

        Assertions.assertThat(this.product.getName())
                .isEqualTo("name");
    }

    @Test
    void getDescription() {

        Assertions.assertThat(this.product.getDescription())
                .isEqualTo("description");
    }

    @Test
    void getPrice() {

        Assertions.assertThat(this.product.getPrice())
                .isEqualTo(new BigDecimal("10"));
    }

    @Test
    void getType() {

        Assertions.assertThat(this.product.getType())
                .isEqualTo(Type.SALTY_PIZZA);
    }

    @Test
    void getPriceRating() {

        Assertions.assertThat(this.product.getPriceRating())
                .isEqualTo(PriceRating.REGULAR_PRICE);
    }

    @Test
    void isStocked() {

        Assertions.assertThat(this.product.isStocked())
                .isTrue();
    }

    @Test
    void getImage() {

        Assertions.assertThat(this.product.getImage())
                .isEqualTo("/image.png");
    }

    @Test
    void testEquals() {

        Assertions.assertThat(this.product.equals(this.sameProduct))
                .isTrue();

        Assertions.assertThat(this.product.equals(this.differentProduct))
                .isFalse();
    }

    @Test
    void testHashCode() {

        Assertions.assertThat(this.product)
                .hasSameHashCodeAs(this.sameProduct);
    }

    @Test
    void testToString() {

        Assertions.assertThat(this.product)
                .hasToString("Product{" +
                        "id=" + this.product.getId() +
                        ", name='" + this.product.getName() + '\'' +
                        ", description='" + this.product.getDescription() + '\'' +
                        ", price=" + this.product.getPrice() +
                        ", type=" + this.product.getType() +
                        ", priceRating=" + this.product.getPriceRating() +
                        ", isStocked=" + this.product.isStocked() +
                        ", image='" + this.product.getImage() + '\'' +
                        '}');
    }

    @Test
    void prePersist(){

        Product product2 = Product.ProductBuilder.builder().build();

        product2.prePersist();

        Assertions.assertThat(product2.isStocked())
                .isTrue();
    }
}