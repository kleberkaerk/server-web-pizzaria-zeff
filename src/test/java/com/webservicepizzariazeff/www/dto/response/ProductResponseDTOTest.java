package com.webservicepizzariazeff.www.dto.response;

import com.webservicepizzariazeff.www.domain.PriceRating;
import com.webservicepizzariazeff.www.domain.Type;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ProductResponseDTOTest {

    private ProductResponseDTO productResponseDTO;

    private ProductResponseDTO sameProductResponseDTO;

    private ProductResponseDTO differentProductResponseDTO;

    @BeforeEach
    void setObjects() {

        this.productResponseDTO = ProductResponseDTO.ProductResponseDTOBuilder.builder()
                .id(1L)
                .name("name1")
                .description("description1")
                .price(new BigDecimal("10"))
                .type(Type.SWEET_PIZZA)
                .priceRating(PriceRating.REGULAR_PRICE)
                .image("/image1")
                .isStocked(true)
                .build();

        this.sameProductResponseDTO = ProductResponseDTO.ProductResponseDTOBuilder.builder()
                .id(1L)
                .name("name1")
                .description("description1")
                .price(new BigDecimal("10"))
                .type(Type.SWEET_PIZZA)
                .priceRating(PriceRating.REGULAR_PRICE)
                .image("/image1")
                .isStocked(true)
                .build();

        this.differentProductResponseDTO = ProductResponseDTO.ProductResponseDTOBuilder.builder()
                .id(2L)
                .name("name2")
                .description("description2")
                .price(new BigDecimal("20"))
                .type(Type.SWEET_PIZZA)
                .priceRating(PriceRating.REGULAR_PRICE)
                .image("/image2")
                .isStocked(true)
                .build();
    }

    @Test
    void getId() {

        Assertions.assertThat(this.productResponseDTO.getId())
                .isEqualTo(1L);
    }

    @Test
    void getName() {

        Assertions.assertThat(this.productResponseDTO.getName())
                .isEqualTo("name1");
    }

    @Test
    void getDescription() {

        Assertions.assertThat(this.productResponseDTO.getDescription())
                .isEqualTo("description1");
    }

    @Test
    void getPrice() {

        Assertions.assertThat(this.productResponseDTO.getPrice())
                .isEqualTo(new BigDecimal("10"));
    }

    @Test
    void getType() {

        Assertions.assertThat(this.productResponseDTO.getType())
                .isEqualTo(Type.SWEET_PIZZA);
    }

    @Test
    void getPriceRating() {

        Assertions.assertThat(this.productResponseDTO.getPriceRating())
                .isEqualTo(PriceRating.REGULAR_PRICE);
    }

    @Test
    void getImage() {

        Assertions.assertThat(this.productResponseDTO.getImage())
                .isEqualTo("/image1");
    }

    @Test
    void isStocked() {

        Assertions.assertThat(this.productResponseDTO.isStocked())
                .isTrue();
    }

    @Test
    void testToString() {

        Assertions.assertThat(this.productResponseDTO)
                .hasToString("ProductResponseDTO{" +
                        "id=" + this.productResponseDTO.getId() +
                        ", name='" + this.productResponseDTO.getName() + '\'' +
                        ", description='" + this.productResponseDTO.getDescription() + '\'' +
                        ", price=" + this.productResponseDTO.getPrice() +
                        ", type=" + this.productResponseDTO.getType() +
                        ", priceRating=" + this.productResponseDTO.getPriceRating() +
                        ", image='" + this.productResponseDTO.getImage() + '\'' +
                        ", isStocked=" + this.productResponseDTO.isStocked() +
                        '}');
    }

    @Test
    void testEquals() {

        Assertions.assertThat(this.productResponseDTO.equals(this.sameProductResponseDTO))
                .isTrue();

        Assertions.assertThat(this.productResponseDTO.equals(this.differentProductResponseDTO))
                .isFalse();
    }

    @Test
    void testHashCode() {

        Assertions.assertThat(this.productResponseDTO)
                .hasSameHashCodeAs(this.sameProductResponseDTO);
    }
}