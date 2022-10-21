package com.webservicepizzariazeff.www.repository;

import com.webservicepizzariazeff.www.dto.request.CardDTO;
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

    public void payment(CardDTO cardDTO, BigDecimal value, String language, String country) {

        if (
                cardDTO.getCardNumber().matches("\\D+|\\D+\\d*|\\d*\\D+|\\d*\\D+\\d*") ||
                        cardDTO.getNameOfCardHolder().matches("\\d+|\\d+\\D*|\\D*\\d+|\\D*\\d+\\D*") ||
                        cardDTO.getDueDate().length() > 5 ||
                        cardDTO.getSecurityCode().length() > 4
        ) {

            throw new InvalidCardException(ResourceBundle.getBundle("messages", new Locale(language, country)).getString("invalid.card"));
        }

        log.info("Purchase in value {} successfully completed.", value);
    }
}
