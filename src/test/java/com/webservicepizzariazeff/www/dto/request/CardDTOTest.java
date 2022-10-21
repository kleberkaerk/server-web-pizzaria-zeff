package com.webservicepizzariazeff.www.dto.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CardDTOTest {

    private CardDTO cardDTO;

    @BeforeEach
    void setCardDTO() {

        this.cardDTO = CardDTO.CardDTOBuilder.builder()
                .nameOfCardHolder("nameOfCardHolder")
                .cardNumber("1234567890123456")
                .dueDate("12/34")
                .securityCode("123")
                .formOfPayment(FormOfPayment.DEBIT)
                .build();
    }

    @Test
    void getNameOfCardHolder() {

        Assertions.assertThat(this.cardDTO.getNameOfCardHolder())
                .isEqualTo("nameOfCardHolder");
    }

    @Test
    void getCardNumber() {

        Assertions.assertThat(this.cardDTO.getCardNumber())
                .isEqualTo("1234567890123456");
    }

    @Test
    void getDueDate() {

        Assertions.assertThat(this.cardDTO.getDueDate())
                .isEqualTo("12/34");
    }

    @Test
    void getSecurityCode() {

        Assertions.assertThat(this.cardDTO.getSecurityCode())
                .isEqualTo("123");
    }

    @Test
    void getFormOfPayment() {

        Assertions.assertThat(this.cardDTO.getFormOfPayment())
                .isEqualTo(FormOfPayment.DEBIT);
    }
}