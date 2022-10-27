package com.webservicepizzariazeff.www.dto.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class SaleRequestDTOTest {

    private SaleRequestDTO saleRequestDTO;

    private static List<Long> productsId;

    private static CardRequestDTO cardRequestDTO;

    @BeforeAll
    static void setObjects() {

        productsId = List.of(1L, 2L, 3L);

        cardRequestDTO = CardRequestDTO.CardRequestDTOBuilder.builder()
                .nameOfCardHolder("nameOfCardHolder")
                .cardNumber("cardNumber")
                .dueDate("dueDate")
                .securityCode("securityCode")
                .formOfPaymentDTO(FormOfPaymentDTO.DEBIT)
                .build();
    }

    @BeforeEach
    void setSaleRequestDTO() {

        this.saleRequestDTO = SaleRequestDTO.SaleRequestDTOBuilder.builder()
                .productsId(productsId)
                .addressId(1L)
                .cardRequestDTO(cardRequestDTO)
                .isPaymentThroughTheWebsite(true)
                .build();
    }

    @Test
    void getProductsId() {

        Assertions.assertThat(this.saleRequestDTO.getProductsId())
                .isNotNull()
                .asList()
                .isEqualTo(productsId)
                .contains(2L);
    }

    @Test
    void getAddressId() {

        Assertions.assertThat(this.saleRequestDTO.getAddressId())
                .isEqualTo(1L);
    }

    @Test
    void getCardRequestDTO() {

        Assertions.assertThat(this.saleRequestDTO.getCardRequestDTO())
                .isEqualTo(cardRequestDTO);
    }

    @Test
    void isPaymentThroughTheWebsite() {

        Assertions.assertThat(this.saleRequestDTO.isPaymentThroughTheWebsite())
                .isTrue();
    }

    @Test
    void testToString(){

        Assertions.assertThat(this.saleRequestDTO)
                .hasToString("SaleRequestDTO{" +
                        "productsId=" + this.saleRequestDTO.getProductsId() +
                        ", addressId=" + this.saleRequestDTO.getAddressId() +
                        ", cardRequestDTO=" + this.saleRequestDTO.getCardRequestDTO() +
                        ", isPaymentThroughTheWebsite=" + this.saleRequestDTO.isPaymentThroughTheWebsite() +
                        '}');
    }
}