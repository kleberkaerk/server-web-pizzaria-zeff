package com.webservicepizzariazeff.www.dto.request;

public class CardRequestDTO {

    private final String nameOfCardHolder;

    private final String cardNumber;

    private final String dueDate;

    private final String securityCode;

    private final FormOfPaymentDTO formOfPaymentDTO;

    private CardRequestDTO(String nameOfCardHolder, String cardNumber, String dueDate, String securityCode, FormOfPaymentDTO formOfPaymentDTO) {

        this.nameOfCardHolder = nameOfCardHolder;
        this.cardNumber = cardNumber;
        this.dueDate = dueDate;
        this.securityCode = securityCode;
        this.formOfPaymentDTO = formOfPaymentDTO;
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

    public FormOfPaymentDTO getFormOfPaymentDTO() {
        return formOfPaymentDTO;
    }

    @Override
    public String toString() {
        return "CardRequestDTO{" +
                "nameOfCardHolder='" + nameOfCardHolder + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", formOfPayment=" + formOfPaymentDTO +
                '}';
    }

    public static final class CardRequestDTOBuilder {
        private String nameOfCardHolder;
        private String cardNumber;
        private String dueDate;
        private String securityCode;
        private FormOfPaymentDTO formOfPaymentDTO;

        private CardRequestDTOBuilder() {
        }

        public static CardRequestDTOBuilder builder() {
            return new CardRequestDTOBuilder();
        }

        public CardRequestDTOBuilder nameOfCardHolder(String nameOfCardHolder) {
            this.nameOfCardHolder = nameOfCardHolder;
            return this;
        }

        public CardRequestDTOBuilder cardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public CardRequestDTOBuilder dueDate(String dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public CardRequestDTOBuilder securityCode(String securityCode) {
            this.securityCode = securityCode;
            return this;
        }

        public CardRequestDTOBuilder formOfPaymentDTO(FormOfPaymentDTO formOfPaymentDTO) {
            this.formOfPaymentDTO = formOfPaymentDTO;
            return this;
        }

        public CardRequestDTO build() {
            return new CardRequestDTO(nameOfCardHolder, cardNumber, dueDate, securityCode, formOfPaymentDTO);
        }
    }
}
