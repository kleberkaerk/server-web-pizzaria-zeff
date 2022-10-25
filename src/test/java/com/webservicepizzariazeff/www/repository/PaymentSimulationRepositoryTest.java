package com.webservicepizzariazeff.www.repository;

import com.webservicepizzariazeff.www.dto.request.CardRequestDTO;
import com.webservicepizzariazeff.www.dto.request.FormOfPayment;
import com.webservicepizzariazeff.www.exception.InvalidCardException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class PaymentSimulationRepositoryTest {

    private PaymentSimulationRepository paymentSimulationRepository;

    private static CardRequestDTO cardRequestDTOWithNameOfCardHolderInvalidRequest;

    private static CardRequestDTO cardRequestDTOWithCardNumberInvalidRequest;

    private static CardRequestDTO cardRequestDTOWithSecurityCodeInvalid;

    private static CardRequestDTO cardRequestDTOWithDueDateInvalid;

    @BeforeAll
    static void setObjects() {

        cardRequestDTOWithNameOfCardHolderInvalidRequest = CardRequestDTO.CardRequestDTOBuilder.builder()
                .nameOfCardHolder("ABCD 123")
                .cardNumber("1234567890123456")
                .dueDate("01/23")
                .securityCode("1234")
                .formOfPayment(FormOfPayment.DEBIT)
                .build();

        cardRequestDTOWithCardNumberInvalidRequest = CardRequestDTO.CardRequestDTOBuilder.builder()
                .nameOfCardHolder("ABCD E. FGHI")
                .cardNumber("123ABC7890123456")
                .dueDate("01/23")
                .securityCode("1234")
                .formOfPayment(FormOfPayment.DEBIT)
                .build();

        cardRequestDTOWithDueDateInvalid = CardRequestDTO.CardRequestDTOBuilder.builder()
                .nameOfCardHolder("ABCD E. FGHI")
                .cardNumber("1234567890123456")
                .dueDate("01/234")
                .securityCode("1234")
                .formOfPayment(FormOfPayment.DEBIT)
                .build();

        cardRequestDTOWithSecurityCodeInvalid = CardRequestDTO.CardRequestDTOBuilder.builder()
                .nameOfCardHolder("ABCD E. FGHI")
                .cardNumber("1234567890123456")
                .dueDate("01/23")
                .securityCode("12345")
                .formOfPayment(FormOfPayment.DEBIT)
                .build();
    }

    @BeforeEach
    void setPaymentSimulationRepository() {

        this.paymentSimulationRepository = new PaymentSimulationRepository();
    }

    @Test
    void payment_makesAPaymentSimulationAndDoesNotThrowAnyExceptions_whenThenArgumentsAreRight() {

        CardRequestDTO cardRequestDTO = CardRequestDTO.CardRequestDTOBuilder.builder()
                .nameOfCardHolder("ABCD E. FGHI")
                .cardNumber("1234567890123456")
                .dueDate("01/23")
                .securityCode("1234")
                .formOfPayment(FormOfPayment.DEBIT)
                .build();

        Assertions.assertThatCode(() -> this.paymentSimulationRepository.payment(cardRequestDTO, new BigDecimal("10.00"), "pt", "BR"))
                .doesNotThrowAnyException();
    }

    @Test
    void payment_throwsInvalidCardException_whenSomeCardRequestDTOValueIsIncorrect() {

        BigDecimal value = new BigDecimal("10.00");

        Assertions.assertThatExceptionOfType(InvalidCardException.class)
                .isThrownBy(() -> this.paymentSimulationRepository.payment(cardRequestDTOWithNameOfCardHolderInvalidRequest, value, "pt", "BR"))
                .withMessage("Cartão inválido.");

        Assertions.assertThatExceptionOfType(InvalidCardException.class)
                .isThrownBy(() -> this.paymentSimulationRepository.payment(cardRequestDTOWithCardNumberInvalidRequest, value, "pt", "BR"))
                .withMessage("Cartão inválido.");

        Assertions.assertThatExceptionOfType(InvalidCardException.class)
                .isThrownBy(() -> this.paymentSimulationRepository.payment(cardRequestDTOWithDueDateInvalid, value, "pt", "BR"))
                .withMessage("Cartão inválido.");

        Assertions.assertThatExceptionOfType(InvalidCardException.class)
                .isThrownBy(() -> this.paymentSimulationRepository.payment(cardRequestDTOWithSecurityCodeInvalid, value, "pt", "BR"))
                .withMessage("Cartão inválido.");
    }
}