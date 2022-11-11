package com.webservicepizzariazeff.www.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class SaleRequestDTO {

    @NotEmpty
    private final List<Long> productsId;

    @NotNull
    private final Long addressId;

    private final CardRequestDTO cardRequestDTO;

    private final boolean isPaymentThroughTheWebsite;

    @JsonCreator
    private SaleRequestDTO(
            @JsonProperty("productsId") List<Long> productsId,
            @JsonProperty("addressId") Long addressId,
            @JsonProperty("cardRequestDTO") CardRequestDTO cardRequestDTO,
            @JsonProperty("paymentThroughTheWebsite") boolean isPaymentThroughTheWebsite) {
        this.productsId = productsId;
        this.addressId = addressId;
        this.cardRequestDTO = cardRequestDTO;
        this.isPaymentThroughTheWebsite = isPaymentThroughTheWebsite;
    }

    public List<Long> getProductsId() {
        return productsId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public CardRequestDTO getCardRequestDTO() {
        return cardRequestDTO;
    }

    public boolean isPaymentThroughTheWebsite() {
        return isPaymentThroughTheWebsite;
    }

    @Override
    public String toString() {
        return "SaleRequestDTO{" +
                "productsId=" + productsId +
                ", addressId=" + addressId +
                ", cardRequestDTO=" + cardRequestDTO +
                ", isPaymentThroughTheWebsite=" + isPaymentThroughTheWebsite +
                '}';
    }

    public static final class SaleRequestDTOBuilder {
        private @NotEmpty List<Long> productsId;
        private @NotNull Long addressId;
        private CardRequestDTO cardRequestDTO;
        private boolean isPaymentThroughTheWebsite;

        private SaleRequestDTOBuilder() {
        }

        public static SaleRequestDTOBuilder builder() {
            return new SaleRequestDTOBuilder();
        }

        public SaleRequestDTOBuilder productsId(List<Long> productsId) {
            this.productsId = productsId;
            return this;
        }

        public SaleRequestDTOBuilder addressId(Long addressId) {
            this.addressId = addressId;
            return this;
        }

        public SaleRequestDTOBuilder cardRequestDTO(CardRequestDTO cardRequestDTO) {
            this.cardRequestDTO = cardRequestDTO;
            return this;
        }

        public SaleRequestDTOBuilder isPaymentThroughTheWebsite(boolean isPaymentThroughTheWebsite) {
            this.isPaymentThroughTheWebsite = isPaymentThroughTheWebsite;
            return this;
        }

        public SaleRequestDTO build() {
            return new SaleRequestDTO(productsId, addressId, cardRequestDTO, isPaymentThroughTheWebsite);
        }
    }
}
