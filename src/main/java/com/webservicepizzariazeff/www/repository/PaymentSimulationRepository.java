package com.webservicepizzariazeff.www.repository;

import com.webservicepizzariazeff.www.dto.request.CardRequestDTO;
import com.webservicepizzariazeff.www.dto.request.FormOfPaymentDTO;
import com.webservicepizzariazeff.www.exception.InvalidCardException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

@Repository
public class PaymentSimulationRepository {

    private final Logger log = LogManager.getLogger(PaymentSimulationRepository.class);

    protected PaymentSimulationRepository() {
    }

    public void payment(CardRequestDTO cardRequestDTO, BigDecimal value, String language, String country) {

        if (
                this.checkIfTheCardNumberIsInvalid(cardRequestDTO.getCardNumber()) ||
                        this.checkIfTheNameOfCardHolderIsInvalid(cardRequestDTO.getNameOfCardHolder()) ||
                        this.checkIfTheDueDateIsInvalid(cardRequestDTO.getDueDate()) ||
                        this.checkIfTheSecurityCodeIsInvalid(cardRequestDTO.getSecurityCode()) ||
                        this.checkIfTheFormOfPaymentIsInvalid(cardRequestDTO.getFormOfPaymentDTO())
        ) {

            throw new InvalidCardException(ResourceBundle.getBundle("messages", new Locale(language, country)).getString("invalid.card"));
        }

        log.info("Purchase in value {} successfully completed.", value);
    }

    private boolean checkIfTheCardNumberIsInvalid(String cardNumber) {

        return cardNumber == null ||
                cardNumber.length() < 13 ||
                cardNumber.length() > 16 ||
                cardNumber.matches("\\D+|\\D+\\d*|\\d*\\D+|\\d*\\D+\\d*");
    }

    private boolean checkIfTheNameOfCardHolderIsInvalid(String nameOfCardHolder) {

        return nameOfCardHolder == null ||
                nameOfCardHolder.isEmpty() ||
                nameOfCardHolder.matches("\\d+|\\d+\\D*|\\D*\\d+|\\D*\\d+\\D*");
    }

    private boolean checkIfTheDueDateIsInvalid(String dueDate) {

        return dueDate == null || dueDate.length() != 5;
    }

    private boolean checkIfTheSecurityCodeIsInvalid(String securityCode) {

        return securityCode == null ||
                securityCode.length() < 3 ||
                securityCode.length() > 4;
    }

    private boolean checkIfTheFormOfPaymentIsInvalid(FormOfPaymentDTO formOfPaymentDTO) {

        return formOfPaymentDTO == null;
    }
}
