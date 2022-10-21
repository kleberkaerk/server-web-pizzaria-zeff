package com.webservicepizzariazeff.www.dto.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class SaleDTOTest {

    private SaleDTO saleDTO;

    private static List<Long> productsId;

    private static CardDTO cardDTO;

    @BeforeAll
    static void setObjects() {

        productsId = List.of(1L, 2L, 3L);

        cardDTO = CardDTO.CardDTOBuilder.builder()
                .nameOfCardHolder("nameOfCardHolder")
                .cardNumber("cardNumber")
                .dueDate("dueDate")
                .securityCode("securityCode")
                .formOfPayment(FormOfPayment.DEBIT)
                .build();
    }

    @BeforeEach
    void setSaleDTO() {

        this.saleDTO = SaleDTO.SaleDTOBuilder.builder()
                .productsId(productsId)
                .addressId(1L)
                .cardDTO(cardDTO)
                .isPaymentThroughTheWebsite(true)
                .build();
    }

    @Test
    void getProductsId() {

        Assertions.assertThat(this.saleDTO.getProductsId())
                .isNotNull()
                .asList()
                .isEqualTo(productsId)
                .contains(2L);
    }

    @Test
    void getAddressId() {

        Assertions.assertThat(this.saleDTO.getAddressId())
                .isEqualTo(1L);
    }

    @Test
    void getCardDTO() {

        Assertions.assertThat(this.saleDTO.getCardDTO())
                .isEqualTo(cardDTO);
    }

    @Test
    void isPaymentThroughTheWebsite() {

        Assertions.assertThat(this.saleDTO.isPaymentThroughTheWebsite())
                .isTrue();
    }
}