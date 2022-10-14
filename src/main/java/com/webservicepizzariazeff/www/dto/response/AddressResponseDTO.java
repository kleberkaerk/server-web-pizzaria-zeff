package com.webservicepizzariazeff.www.dto.response;

import java.util.Objects;

public class AddressResponseDTO {

    private final Long id;

    private final String number;

    private final String road;

    private final String district;

    private final String city;

    private final String state;

    private AddressResponseDTO(Long id, String number, String road, String district, String city, String state){

        this.id = id;
        this.number = number;
        this.road = road;
        this.district = district;
        this.city = city;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getRoad() {
        return road;
    }

    public String getDistrict() {
        return district;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressResponseDTO that = (AddressResponseDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(number, that.number) && Objects.equals(road, that.road) && Objects.equals(district, that.district) && Objects.equals(city, that.city) && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, road, district, city, state);
    }

    public static final class AddressResponseDTOBuilder {
        private Long id;
        private String number;
        private String road;
        private String district;
        private String city;
        private String state;

        private AddressResponseDTOBuilder() {
        }

        public static AddressResponseDTOBuilder builder() {
            return new AddressResponseDTOBuilder();
        }

        public AddressResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AddressResponseDTOBuilder number(String number) {
            this.number = number;
            return this;
        }

        public AddressResponseDTOBuilder road(String road) {
            this.road = road;
            return this;
        }

        public AddressResponseDTOBuilder district(String district) {
            this.district = district;
            return this;
        }

        public AddressResponseDTOBuilder city(String city) {
            this.city = city;
            return this;
        }

        public AddressResponseDTOBuilder state(String state) {
            this.state = state;
            return this;
        }

        public AddressResponseDTO build() {
            return new AddressResponseDTO(id, number, road, district, city, state);
        }
    }
}
