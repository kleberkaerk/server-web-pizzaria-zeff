package com.webservicepizzariazeff.www.dto.response;

import java.math.BigDecimal;
import java.util.List;

@SuppressWarnings("java:S107")
public class PurchaseUserResponseDTO {

    private final Long id;
    private final BigDecimal amount;
    private final String dateAndTime;
    private final String cardName;
    private final boolean isActive;
    private final boolean isFinished;
    private final boolean isDelivered;
    private final boolean isPaymentThroughTheWebsite;
    private final List<PurchasedProductResponseDTO> purchasedProductResponseDTOS;
    private final AddressResponseDTO addressResponseDTO;

    private PurchaseUserResponseDTO(
            Long id,
            BigDecimal amount,
            String dateAndTime,
            String cardName,
            boolean isActive,
            boolean isFinished,
            boolean isDelivered,
            boolean isPaymentThroughTheWebsite,
            List<PurchasedProductResponseDTO> purchasedProductResponseDTOS,
            AddressResponseDTO addressResponseDTO
    ) {
        this.id = id;
        this.amount = amount;
        this.dateAndTime = dateAndTime;
        this.cardName = cardName;
        this.isActive = isActive;
        this.isFinished = isFinished;
        this.isDelivered = isDelivered;
        this.isPaymentThroughTheWebsite = isPaymentThroughTheWebsite;
        this.purchasedProductResponseDTOS = purchasedProductResponseDTOS;
        this.addressResponseDTO = addressResponseDTO;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getCardName() {
        return cardName;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public boolean isPaymentThroughTheWebsite() {
        return isPaymentThroughTheWebsite;
    }

    public List<PurchasedProductResponseDTO> getPurchasedProductResponseDTOS() {
        return purchasedProductResponseDTOS;
    }

    public AddressResponseDTO getAddressResponseDTO() {
        return addressResponseDTO;
    }

    @Override
    public String toString() {
        return "PurchaseResponseDTOForUser{" +
                "id=" + id +
                ", amount=" + amount +
                ", dateAndTime='" + dateAndTime + '\'' +
                ", cardName='" + cardName + '\'' +
                ", isActive=" + isActive +
                ", isFinished=" + isFinished +
                ", isDelivered=" + isDelivered +
                ", isPaymentThroughTheWebsite=" + isPaymentThroughTheWebsite +
                ", purchasedProductResponseDTOS=" + purchasedProductResponseDTOS +
                ", addressResponseDTO=" + addressResponseDTO +
                '}';
    }

    public static final class PurchaseUserResponseDTOBuilder {
        private Long id;
        private BigDecimal amount;
        private String dateAndTime;
        private String cardName;
        private boolean isActive;
        private boolean isFinished;
        private boolean isDelivered;
        private boolean isPaymentThroughTheWebsite;
        private List<PurchasedProductResponseDTO> purchasedProductResponseDTOS;
        private AddressResponseDTO addressResponseDTO;

        private PurchaseUserResponseDTOBuilder() {
        }

        public static PurchaseUserResponseDTOBuilder builder() {
            return new PurchaseUserResponseDTOBuilder();
        }

        public PurchaseUserResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PurchaseUserResponseDTOBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public PurchaseUserResponseDTOBuilder dateAndTime(String dateAndTime) {
            this.dateAndTime = dateAndTime;
            return this;
        }

        public PurchaseUserResponseDTOBuilder cardName(String cardName) {
            this.cardName = cardName;
            return this;
        }

        public PurchaseUserResponseDTOBuilder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public PurchaseUserResponseDTOBuilder isFinished(boolean isFinished) {
            this.isFinished = isFinished;
            return this;
        }

        public PurchaseUserResponseDTOBuilder isDelivered(boolean isDelivered) {
            this.isDelivered = isDelivered;
            return this;
        }

        public PurchaseUserResponseDTOBuilder isPaymentThroughTheWebsite(boolean isPaymentThroughTheWebsite) {
            this.isPaymentThroughTheWebsite = isPaymentThroughTheWebsite;
            return this;
        }

        public PurchaseUserResponseDTOBuilder purchasedProductResponseDTOS(List<PurchasedProductResponseDTO> purchasedProductResponseDTOS) {
            this.purchasedProductResponseDTOS = purchasedProductResponseDTOS;
            return this;
        }

        public PurchaseUserResponseDTOBuilder addressResponseDTO(AddressResponseDTO addressResponseDTO) {
            this.addressResponseDTO = addressResponseDTO;
            return this;
        }

        public PurchaseUserResponseDTO build() {
            return new PurchaseUserResponseDTO(id, amount, dateAndTime, cardName, isActive, isFinished, isDelivered, isPaymentThroughTheWebsite, purchasedProductResponseDTOS, addressResponseDTO);
        }
    }
}
