package com.webservicepizzariazeff.www.dto.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AddressDTOTest {

    public static AddressDTO addressDTO;

    @BeforeAll
    static void setAddressDTO() {

        addressDTO = AddressDTO.AddressDTOBuilder.builder()
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .build();
    }

    @Test
    void getNumber() {

        Assertions.assertThat(addressDTO.getNumber())
                .isEqualTo("1");
    }

    @Test
    void getRoad() {
        Assertions.assertThat(addressDTO.getRoad())
                .isEqualTo("road");
    }

    @Test
    void getDistrict() {
        Assertions.assertThat(addressDTO.getDistrict())
                .isEqualTo("district");
    }

    @Test
    void getCity() {
        Assertions.assertThat(addressDTO.getCity())
                .isEqualTo("city");
    }

    @Test
    void getState() {
        Assertions.assertThat(addressDTO.getState())
                .isEqualTo("state");
    }

    @Test
    void testToString() {

        Assertions.assertThat(addressDTO)
                .hasToString("AddressDTO{number='1', road='road', district='district', city='city', state='state'}");
    }
}