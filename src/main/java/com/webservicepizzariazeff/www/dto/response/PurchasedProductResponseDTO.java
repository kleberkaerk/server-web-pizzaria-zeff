package com.webservicepizzariazeff.www.dto.response;

public class PurchasedProductResponseDTO {

    private final String name;

    private PurchasedProductResponseDTO(String name){

        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "PurchasedProductResponseDTO{" +
                "name='" + name + '\'' +
                '}';
    }

    public static final class PurchasedProductResponseDTOBuilder {
        private String name;

        private PurchasedProductResponseDTOBuilder() {
        }

        public static PurchasedProductResponseDTOBuilder builder() {
            return new PurchasedProductResponseDTOBuilder();
        }

        public PurchasedProductResponseDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PurchasedProductResponseDTO build() {
            return new PurchasedProductResponseDTO(name);
        }
    }
}
