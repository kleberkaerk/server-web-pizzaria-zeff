package com.webservicepizzariazeff.www.dto.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AddressRequestDTOTest {

    public static AddressRequestDTO addressRequestDTO;

    @BeforeAll
    static void setAddressDTO() {

        addressRequestDTO = AddressRequestDTO.AddressRequestDTOBuilder.builder()
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .build();
    }

    @Test
    void getNumber() {

        Assertions.assertThat(addressRequestDTO.getNumber())
                .isEqualTo("1");
    }

    @Test
    void getRoad() {
        Assertions.assertThat(addressRequestDTO.getRoad())
                .isEqualTo("road");
    }

    @Test
    void getDistrict() {
        Assertions.assertThat(addressRequestDTO.getDistrict())
                .isEqualTo("district");
    }

    @Test
    void getCity() {
        Assertions.assertThat(addressRequestDTO.getCity())
                .isEqualTo("city");
    }

    @Test
    void getState() {
        Assertions.assertThat(addressRequestDTO.getState())
                .isEqualTo("state");
    }

    @Test
    void testToString() {

        Assertions.assertThat(addressRequestDTO)
                .hasToString("AddressDTO{number='1', road='road', district='district', city='city', state='state'}");
    }
}