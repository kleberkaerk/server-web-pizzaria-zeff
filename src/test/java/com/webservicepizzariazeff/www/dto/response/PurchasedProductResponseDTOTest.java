package com.webservicepizzariazeff.www.dto.response;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchasedProductResponseDTOTest {

    private PurchasedProductResponseDTO purchasedProductResponseDTO;

    private PurchasedProductResponseDTO samePurchasedProductResponseDTO;

    private PurchasedProductResponseDTO differentPurchasedProductResponseDTO;

    @BeforeEach
    void setObjects() {

        this.purchasedProductResponseDTO = PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                .name("name1")
                .build();


        this.samePurchasedProductResponseDTO = PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                .name("name1")
                .build();

        this.differentPurchasedProductResponseDTO = PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                .name("name2")
                .build();
    }

    @Test
    void getName() {

        Assertions.assertThat(this.purchasedProductResponseDTO.getName())
                .isEqualTo("name1");
    }

    @Test
    void testToString() {

        Assertions.assertThat(this.purchasedProductResponseDTO)
                .hasToString("PurchasedProductResponseDTO{" +
                        "name='" + this.purchasedProductResponseDTO.getName() + '\'' +
                        '}');
    }

    @Test
    void testEquals() {

        Assertions.assertThat(this.purchasedProductResponseDTO.equals(this.samePurchasedProductResponseDTO))
                .isTrue();

        Assertions.assertThat(this.purchasedProductResponseDTO.equals(this.differentPurchasedProductResponseDTO))
                .isFalse();
    }

    @Test
    void testHashCode() {

        Assertions.assertThat(this.purchasedProductResponseDTO)
                .hasSameHashCodeAs(this.samePurchasedProductResponseDTO);
    }
}