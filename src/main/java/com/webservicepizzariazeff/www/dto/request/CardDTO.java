package com.webservicepizzariazeff.www.dto.request;

import javax.validation.constraints.NotEmpty;

public class CardDTO {

    @NotEmpty
    private final String nameOfCardHolder;

    @NotEmpty
    private final String cardNumber;

    @NotEmpty
    private final String dueDate;

    @NotEmpty
    private final String securityCode;

    private final FormOfPayment formOfPayment;

    private CardDTO(String nameOfCardHolder, String cardNumber, String dueDate, String securityCode, FormOfPayment formOfPayment) {

        this.nameOfCardHolder = nameOfCardHolder;
        this.cardNumber = cardNumber;
        this.dueDate = dueDate;
        this.securityCode = securityCode;
        this.formOfPayment = formOfPayment;
    }

    public String getNameOfCardHolder() {
        return nameOfCardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public FormOfPayment getFormOfPayment() {
        return formOfPayment;
    }

    public static final class CardDTOBuilder {
        private @NotEmpty String nameOfCardHolder;
        private @NotEmpty String cardNumber;
        private @NotEmpty String dueDate;
        private @NotEmpty String securityCode;
        private FormOfPayment formOfPayment;

        private CardDTOBuilder() {
        }

        public static CardDTOBuilder builder() {
            return new CardDTOBuilder();
        }

        public CardDTOBuilder nameOfCardHolder(String nameOfCardHolder) {
            this.nameOfCardHolder = nameOfCardHolder;
            return this;
        }

        public CardDTOBuilder cardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public CardDTOBuilder dueDate(String dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public CardDTOBuilder securityCode(String securityCode) {
            this.securityCode = securityCode;
            return this;
        }

        public CardDTOBuilder formOfPayment(FormOfPayment formOfPayment) {
            this.formOfPayment = formOfPayment;
            return this;
        }

        public CardDTO build() {
            return new CardDTO(nameOfCardHolder, cardNumber, dueDate, securityCode, formOfPayment);
        }
    }
}
