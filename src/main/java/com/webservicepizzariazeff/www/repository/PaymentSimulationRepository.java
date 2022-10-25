package com.webservicepizzariazeff.www.repository;

import com.webservicepizzariazeff.www.dto.request.CardRequestDTO;
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
                cardRequestDTO.getCardNumber().matches("\\D+|\\D+\\d*|\\d*\\D+|\\d*\\D+\\d*") ||
                        cardRequestDTO.getNameOfCardHolder().matches("\\d+|\\d+\\D*|\\D*\\d+|\\D*\\d+\\D*") ||
                        cardRequestDTO.getDueDate().length() > 5 ||
                        cardRequestDTO.getSecurityCode().length() > 4
        ) {

            throw new InvalidCardException(ResourceBundle.getBundle("messages", new Locale(language, country)).getString("invalid.card"));
        }

        log.info("Purchase in value {} successfully completed.", value);
    }
}
