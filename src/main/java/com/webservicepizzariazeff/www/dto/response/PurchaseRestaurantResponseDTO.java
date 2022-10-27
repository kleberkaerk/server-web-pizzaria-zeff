package com.webservicepizzariazeff.www.dto.response;

import java.util.List;

@SuppressWarnings("java:S107")
public class PurchaseRestaurantResponseDTO {

    private final Long id;
    private final String clientName;
    private final boolean isActive;
    private final boolean isFinished;
    private final boolean isDelivered;
    private final boolean isPaymentThroughTheWebsite;
    private final List<PurchasedProductResponseDTO> purchasedProductResponseDTOS;
    private final AddressResponseDTO addressResponseDTO;

    private PurchaseRestaurantResponseDTO(
            Long id,
            String clientName,
            boolean isActive,
            boolean isFinished,
            boolean isDelivered,
            boolean isPaymentThroughTheWebsite,
            List<PurchasedProductResponseDTO> purchasedProductResponseDTOS,
            AddressResponseDTO addressResponseDTO
    ) {

        this.id = id;
        this.clientName = clientName;
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

    public String getClientName() {
        return clientName;
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
        return "PurchaseResponseDTOForRestaurant{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", isActive=" + isActive +
                ", isFinished=" + isFinished +
                ", isDelivered=" + isDelivered +
                ", isPaymentThroughTheWebsite=" + isPaymentThroughTheWebsite +
                ", purchasedProductResponseDTOS=" + purchasedProductResponseDTOS +
                ", addressResponseDTO=" + addressResponseDTO +
                '}';
    }

    public static final class PurchaseRestaurantResponseDTOBuilder {
        private Long id;
        private String clientName;
        private boolean isActive;
        private boolean isFinished;
        private boolean isDelivered;
        private boolean isPaymentThroughTheWebsite;
        private List<PurchasedProductResponseDTO> purchasedProductResponseDTOS;
        private AddressResponseDTO addressResponseDTO;

        private PurchaseRestaurantResponseDTOBuilder() {
        }

        public static PurchaseRestaurantResponseDTOBuilder builder() {
            return new PurchaseRestaurantResponseDTOBuilder();
        }

        public PurchaseRestaurantResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PurchaseRestaurantResponseDTOBuilder clientName(String clientName) {
            this.clientName = clientName;
            return this;
        }

        public PurchaseRestaurantResponseDTOBuilder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public PurchaseRestaurantResponseDTOBuilder isFinished(boolean isFinished) {
            this.isFinished = isFinished;
            return this;
        }

        public PurchaseRestaurantResponseDTOBuilder isDelivered(boolean isDelivered) {
            this.isDelivered = isDelivered;
            return this;
        }

        public PurchaseRestaurantResponseDTOBuilder isPaymentThroughTheWebsite(boolean isPaymentThroughTheWebsite) {
            this.isPaymentThroughTheWebsite = isPaymentThroughTheWebsite;
            return this;
        }

        public PurchaseRestaurantResponseDTOBuilder purchasedProductResponseDTOS(List<PurchasedProductResponseDTO> purchasedProductResponseDTOS) {
            this.purchasedProductResponseDTOS = purchasedProductResponseDTOS;
            return this;
        }

        public PurchaseRestaurantResponseDTOBuilder addressResponseDTO(AddressResponseDTO addressResponseDTO) {
            this.addressResponseDTO = addressResponseDTO;
            return this;
        }

        public PurchaseRestaurantResponseDTO build() {
            return new PurchaseRestaurantResponseDTO(
                    id,
                    clientName,
                    isActive,
                    isFinished,
                    isDelivered,
                    isPaymentThroughTheWebsite,
                    purchasedProductResponseDTOS,
                    addressResponseDTO
            );
        }
    }
}
