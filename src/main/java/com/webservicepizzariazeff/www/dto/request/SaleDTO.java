package com.webservicepizzariazeff.www.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class SaleDTO {

    @NotEmpty
    private final List<Long> productsId;

    @NotNull
    private final Long addressId;

    private final CardDTO cardDTO;

    private final boolean isPaymentThroughTheWebsite;

    private SaleDTO(List<Long> productsId, Long addressId, CardDTO cardDTO, boolean isPaymentThroughTheWebsite) {
        this.productsId = productsId;
        this.addressId = addressId;
        this.cardDTO = cardDTO;
        this.isPaymentThroughTheWebsite = isPaymentThroughTheWebsite;
    }

    public List<Long> getProductsId() {
        return productsId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public CardDTO getCardDTO() {
        return cardDTO;
    }

    public boolean isPaymentThroughTheWebsite() {
        return isPaymentThroughTheWebsite;
    }

    public static final class SaleDTOBuilder {
        private @NotEmpty List<Long> productsId;
        private @NotNull Long addressId;
        private CardDTO cardDTO;
        private boolean isPaymentThroughTheWebsite;

        private SaleDTOBuilder() {
        }

        public static SaleDTOBuilder builder() {
            return new SaleDTOBuilder();
        }

        public SaleDTOBuilder productsId(List<Long> productsId) {
            this.productsId = productsId;
            return this;
        }

        public SaleDTOBuilder addressId(Long addressId) {
            this.addressId = addressId;
            return this;
        }

        public SaleDTOBuilder cardDTO(CardDTO cardDTO) {
            this.cardDTO = cardDTO;
            return this;
        }

        public SaleDTOBuilder isPaymentThroughTheWebsite(boolean isPaymentThroughTheWebsite) {
            this.isPaymentThroughTheWebsite = isPaymentThroughTheWebsite;
            return this;
        }

        public SaleDTO build() {
            return new SaleDTO(productsId, addressId, cardDTO, isPaymentThroughTheWebsite);
        }
    }
}
