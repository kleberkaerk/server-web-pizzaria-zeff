package com.webservicepizzariazeff.www.dto.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CardRequestDTOTest {

    private CardRequestDTO cardRequestDTO;

    @BeforeEach
    void setCardRequestDTO() {

        this.cardRequestDTO = CardRequestDTO.CardRequestDTOBuilder.builder()
                .nameOfCardHolder("nameOfCardHolder")
                .cardNumber("1234567890123456")
                .dueDate("12/34")
                .securityCode("123")
                .formOfPaymentDTO(FormOfPaymentDTO.DEBIT)
                .build();
    }

    @Test
    void getNameOfCardHolder() {

        Assertions.assertThat(this.cardRequestDTO.getNameOfCardHolder())
                .isEqualTo("nameOfCardHolder");
    }

    @Test
    void getCardNumber() {

        Assertions.assertThat(this.cardRequestDTO.getCardNumber())
                .isEqualTo("1234567890123456");
    }

    @Test
    void getDueDate() {

        Assertions.assertThat(this.cardRequestDTO.getDueDate())
                .isEqualTo("12/34");
    }

    @Test
    void getSecurityCode() {

        Assertions.assertThat(this.cardRequestDTO.getSecurityCode())
                .isEqualTo("123");
    }

    @Test
    void getFormOfPayment() {

        Assertions.assertThat(this.cardRequestDTO.getFormOfPayment())
                .isEqualTo(FormOfPaymentDTO.DEBIT);
    }

    @Test
    void testToString(){

        Assertions.assertThat(this.cardRequestDTO)
                .hasToString("CardRequestDTO{" +
                        "nameOfCardHolder='" + this.cardRequestDTO.getNameOfCardHolder() + '\'' +
                        ", cardNumber='" + this.cardRequestDTO.getCardNumber() + '\'' +
                        ", dueDate='" + this.cardRequestDTO.getDueDate() + '\'' +
                        ", securityCode='" + this.cardRequestDTO.getSecurityCode() + '\'' +
                        ", formOfPayment=" + this.cardRequestDTO.getFormOfPayment() +
                        '}');
    }
}