package com.webservicepizzariazeff.www.dto.response;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddressResponseDTOTest {

    private AddressResponseDTO addressResponseDTO;

    @BeforeEach
    void setAddressResponseDTO() {

        this.addressResponseDTO = AddressResponseDTO.AddressResponseDTOBuilder.builder()
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

        Assertions.assertThat(this.addressResponseDTO.getId())
                .isEqualTo(1L);
    }

    @Test
    void getNumber() {

        Assertions.assertThat(this.addressResponseDTO.getNumber())
                .isEqualTo("1");
    }

    @Test
    void getRoad() {

        Assertions.assertThat(this.addressResponseDTO.getRoad())
                .isEqualTo("road");
    }

    @Test
    void getDistrict() {

        Assertions.assertThat(this.addressResponseDTO.getDistrict())
                .isEqualTo("district");
    }

    @Test
    void getCity() {

        Assertions.assertThat(this.addressResponseDTO.getCity())
                .isEqualTo("city");
    }

    @Test
    void getState() {

        Assertions.assertThat(this.addressResponseDTO.getState())
                .isEqualTo("state");
    }

    @Test
    void testEquals() {

        Assertions.assertThat(this.addressResponseDTO)
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

        Assertions.assertThat(this.addressResponseDTO)
                .hasSameHashCodeAs(AddressResponseDTO.AddressResponseDTOBuilder.builder()
                        .id(1L)
                        .number("1")
                        .road("road")
                        .district("district")
                        .city("city")
                        .state("state")
                        .build());
    }

    @Test
    void testToString() {

        Assertions.assertThat(this.addressResponseDTO)
                .hasToString("AddressResponseDTO{" +
                        "id=" + this.addressResponseDTO.getId() +
                        ", number='" + this.addressResponseDTO.getNumber() + '\'' +
                        ", road='" + this.addressResponseDTO.getRoad() + '\'' +
                        ", district='" + this.addressResponseDTO.getDistrict() + '\'' +
                        ", city='" + this.addressResponseDTO.getCity() + '\'' +
                        ", state='" + this.addressResponseDTO.getState() + '\'' +
                        '}');
    }
}