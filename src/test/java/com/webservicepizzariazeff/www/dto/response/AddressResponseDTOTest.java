package com.webservicepizzariazeff.www.dto.response;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AddressResponseDTOTest {

    private static AddressResponseDTO addressResponseDTO;

    @BeforeAll
    static void setAddressResponseDTO(){

        addressResponseDTO = AddressResponseDTO.AddressResponseDTOBuilder.builder()
                .id(1L)
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .build();
    }

    @Test
    void getId() {

        Assertions.assertThat(addressResponseDTO.getId())
                .isEqualTo(1L);
    }

    @Test
    void getNumber() {

        Assertions.assertThat(addressResponseDTO.getNumber())
                .isEqualTo("1");
    }

    @Test
    void getRoad() {

        Assertions.assertThat(addressResponseDTO.getRoad())
                .isEqualTo("road");
    }

    @Test
    void getDistrict() {

        Assertions.assertThat(addressResponseDTO.getDistrict())
                .isEqualTo("district");
    }

    @Test
    void getCity() {

        Assertions.assertThat(addressResponseDTO.getCity())
                .isEqualTo("city");
    }

    @Test
    void getState() {

        Assertions.assertThat(addressResponseDTO.getState())
                .isEqualTo("state");
    }

    @Test
    void testEquals() {

        Assertions.assertThat(addressResponseDTO)
                .isEqualTo(AddressResponseDTO.AddressResponseDTOBuilder.builder()
                        .id(1L)
                        .number("1")
                        .road("road")
                        .district("district")
                        .city("city")
                        .state("state")
                        .build());
    }

    @Test
    void testHashCode() {

        Assertions.assertThat(addressResponseDTO)
                .hasSameHashCodeAs(AddressResponseDTO.AddressResponseDTOBuilder.builder()
                        .id(1L)
                        .number("1")
                        .road("road")
                        .district("district")
                        .city("city")
                        .state("state")
                        .build());
    }
}