package com.webservicepizzariazeff.www.repository;

import com.webservicepizzariazeff.www.dto.request.CardDTO;
import com.webservicepizzariazeff.www.dto.request.FormOfPayment;
import com.webservicepizzariazeff.www.exception.InvalidCardException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class PaymentSimulationRepositoryTest {

    private PaymentSimulationRepository paymentSimulationRepository;

    private static CardDTO cardDTOWithNameOfCardHolderInvalid;

    private static CardDTO cardDTOWithCardNumberInvalid;

    private static CardDTO cardDTOWithSecurityCodeInvalid;

    private static CardDTO cardDTOWithDueDateInvalid;

    @BeforeAll
    static void setObjects() {

        cardDTOWithNameOfCardHolderInvalid = CardDTO.CardDTOBuilder.builder()
                .nameOfCardHolder("ABCD 123")
                .cardNumber("1234567890123456")
                .dueDate("01/23")
                .securityCode("1234")
                .formOfPayment(FormOfPayment.DEBIT)
                .build();

        cardDTOWithCardNumberInvalid = CardDTO.CardDTOBuilder.builder()
                .nameOfCardHolder("ABCD E. FGHI")
                .cardNumber("123ABC7890123456")
                .dueDate("01/23")
                .securityCode("1234")
                .formOfPayment(FormOfPayment.DEBIT)
                .build();

        cardDTOWithDueDateInvalid = CardDTO.CardDTOBuilder.builder()
                .nameOfCardHolder("ABCD E. FGHI")
                .cardNumber("1234567890123456")
                .dueDate("01/234")
                .securityCode("1234")
                .formOfPayment(FormOfPayment.DEBIT)
                .build();

        cardDTOWithSecurityCodeInvalid = CardDTO.CardDTOBuilder.builder()
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

        CardDTO cardDTO = CardDTO.CardDTOBuilder.builder()
                .nameOfCardHolder("ABCD E. FGHI")
                .cardNumber("1234567890123456")
                .dueDate("01/23")
                .securityCode("1234")
                .formOfPayment(FormOfPayment.DEBIT)
                .build();

        Assertions.assertThatCode(() -> this.paymentSimulationRepository.payment(cardDTO, new BigDecimal("10.00"), "pt", "BR"))
                .doesNotThrowAnyException();
    }

    @Test
    void payment_throwsInvalidCardException_whenSomeCardDTOValueIsIncorrect() {

        BigDecimal value = new BigDecimal("10.00");

        Assertions.assertThatExceptionOfType(InvalidCardException.class)
                .isThrownBy(() -> this.paymentSimulationRepository.payment(cardDTOWithNameOfCardHolderInvalid, value, "pt", "BR"))
                .withMessage("Cartão inválido.");

        Assertions.assertThatExceptionOfType(InvalidCardException.class)
                .isThrownBy(() -> this.paymentSimulationRepository.payment(cardDTOWithCardNumberInvalid, value, "pt", "BR"))
                .withMessage("Cartão inválido.");

        Assertions.assertThatExceptionOfType(InvalidCardException.class)
                .isThrownBy(() -> this.paymentSimulationRepository.payment(cardDTOWithDueDateInvalid, value, "pt", "BR"))
                .withMessage("Cartão inválido.");

        Assertions.assertThatExceptionOfType(InvalidCardException.class)
                .isThrownBy(() -> this.paymentSimulationRepository.payment(cardDTOWithSecurityCodeInvalid, value, "pt", "BR"))
                .withMessage("Cartão inválido.");
    }
}