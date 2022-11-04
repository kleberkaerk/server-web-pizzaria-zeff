package com.webservicepizzariazeff.www.dto.request;

import com.webservicepizzariazeff.www.domain.PriceRating;
import com.webservicepizzariazeff.www.domain.Type;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ProductRequestDTOTest {

    private ProductRequestDTO productRequestDTO;

    @BeforeEach
    void setProductRequestDTO(){

        this.productRequestDTO = ProductRequestDTO.ProductRequestDTOBuilder.builder()
                .name("name")
                .description("description")
                .price(new BigDecimal("10"))
                .type(Type.SALTY_ESFIHA)
                .priceRating(PriceRating.REGULAR_PRICE)
                .build();
    }

    @Test
    void getName() {

        Assertions.assertThat(this.productRequestDTO.getName())
                .isEqualTo("name");
    }

    @Test
    void getDescription() {

        Assertions.assertThat(this.productRequestDTO.getDescription())
                .isEqualTo("description");
    }

    @Test
    void getPrice() {

        Assertions.assertThat(this.productRequestDTO.getPrice())
                .isEqualTo(new BigDecimal("10"));
    }

    @Test
    void getType() {

        Assertions.assertThat(this.productRequestDTO.getType())
                .isEqualTo(Type.SALTY_ESFIHA);
    }

    @Test
    void getPriceRating() {

        Assertions.assertThat(this.productRequestDTO.getPriceRating())
                .isEqualTo(PriceRating.REGULAR_PRICE);
    }

    @Test
    void testToString() {

        Assertions.assertThat(this.productRequestDTO)
                .hasToString("ProductRequestDTO{" +
                        "name='" + this.productRequestDTO.getName() + '\'' +
                        ", description='" + this.productRequestDTO.getDescription() + '\'' +
                        ", price=" + this.productRequestDTO.getPrice() +
                        ", type=" + this.productRequestDTO.getType() +
                        ", priceRating=" + this.productRequestDTO.getPriceRating() +
                        '}');
    }
}