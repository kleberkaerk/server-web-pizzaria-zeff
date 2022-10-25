package com.webservicepizzariazeff.www.dto.response;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchasedProductResponseDTOTest {

    private PurchasedProductResponseDTO purchasedProductResponseDTO;

    @BeforeEach
    void setPurchasedProductResponseDTO() {

        this.purchasedProductResponseDTO = PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                .name("name")
                .build();
    }

    @Test
    void getName() {

        Assertions.assertThat(this.purchasedProductResponseDTO.getName())
                .isEqualTo("name");
    }

    @Test
    void testToString() {

        Assertions.assertThat(this.purchasedProductResponseDTO)
                .hasToString("PurchasedProductResponseDTO{" +
                        "name='" + this.purchasedProductResponseDTO.getName() + '\'' +
                        '}');
    }
}