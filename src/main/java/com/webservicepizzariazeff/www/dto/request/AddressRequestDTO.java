package com.webservicepizzariazeff.www.dto.request;

import javax.validation.constraints.NotEmpty;

public class AddressRequestDTO {

    @NotEmpty()
    private final String number;

    @NotEmpty()
    private final String road;

    @NotEmpty()
    private final String district;

    @NotEmpty()
    private final String city;

    @NotEmpty()
    private final String state;

    private AddressRequestDTO(String number, String road, String district, String city, String state){

        this.number = number;
        this.road = road;
        this.district = district;
        this.city = city;
        this.state = state;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "number='" + number + '\'' +
                ", road='" + road + '\'' +
                ", district='" + district + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
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

    public static final class AddressRequestDTOBuilder {
        private @NotEmpty() String number;
        private @NotEmpty() String road;
        private @NotEmpty() String district;
        private @NotEmpty() String city;
        private @NotEmpty() String state;

        private AddressRequestDTOBuilder() {
        }

        public static AddressRequestDTOBuilder builder() {
            return new AddressRequestDTOBuilder();
        }

        public AddressRequestDTOBuilder number(String number) {
            this.number = number;
            return this;
        }

        public AddressRequestDTOBuilder road(String road) {
            this.road = road;
            return this;
        }

        public AddressRequestDTOBuilder district(String district) {
            this.district = district;
            return this;
        }

        public AddressRequestDTOBuilder city(String city) {
            this.city = city;
            return this;
        }

        public AddressRequestDTOBuilder state(String state) {
            this.state = state;
            return this;
        }

        public AddressRequestDTO build() {
            return new AddressRequestDTO(number, road, district, city, state);
        }
    }
}
