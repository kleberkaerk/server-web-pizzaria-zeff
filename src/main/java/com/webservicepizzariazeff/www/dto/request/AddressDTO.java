package com.webservicepizzariazeff.www.dto.request;

import javax.validation.constraints.NotEmpty;

public class AddressDTO {

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

    private AddressDTO(String number, String road, String district, String city, String state){

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

    public static final class AddressDTOBuilder {
        private @NotEmpty String number;
        private @NotEmpty String road;
        private @NotEmpty String district;
        private @NotEmpty String city;
        private @NotEmpty String state;

        private AddressDTOBuilder() {
        }

        public static AddressDTOBuilder builder() {
            return new AddressDTOBuilder();
        }

        public AddressDTOBuilder number(String number) {
            this.number = number;
            return this;
        }

        public AddressDTOBuilder road(String road) {
            this.road = road;
            return this;
        }

        public AddressDTOBuilder district(String district) {
            this.district = district;
            return this;
        }

        public AddressDTOBuilder city(String city) {
            this.city = city;
            return this;
        }

        public AddressDTOBuilder state(String state) {
            this.state = state;
            return this;
        }

        public AddressDTO build() {
            return new AddressDTO(number, road, district, city, state);
        }
    }
}
