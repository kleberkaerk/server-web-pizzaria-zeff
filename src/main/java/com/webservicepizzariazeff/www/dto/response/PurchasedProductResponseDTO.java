package com.webservicepizzariazeff.www.dto.response;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchasedProductResponseDTO that = (PurchasedProductResponseDTO) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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
