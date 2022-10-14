package com.webservicepizzariazeff.www.util;


import com.webservicepizzariazeff.www.domain.Address;
import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.request.AddressRequestDTO;
import com.webservicepizzariazeff.www.dto.response.AddressResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MapperTest {

    private AddressRequestDTO addressRequestDTO;

    private User user;

    @BeforeEach
    void setObjects() {
        this.addressRequestDTO = AddressRequestDTO.AddressRequestDTOBuilder.builder()
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .build();

        this.user = User.UserBuilder.builder()
                .id(1L)
                .name("name")
                .username("this.username")
                .password("password")
                .authorities("ROLE_USER")
                .build();
    }

    @Test
    void ofTheAddressDTOForAddress_mapsFromAAddressDTOToAnAddress_WheneverCalled() {

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOForAddress(this.addressRequestDTO, this.user).getNumber())
                .isEqualTo("1");

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOForAddress(this.addressRequestDTO, this.user).getRoad())
                .isEqualTo("road");

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOForAddress(this.addressRequestDTO, this.user).getDistrict())
                .isEqualTo("district");

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOForAddress(this.addressRequestDTO, this.user).getCity())
                .isEqualTo("city");

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOForAddress(this.addressRequestDTO, this.user).getState())
                .isEqualTo("state");

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOForAddress(this.addressRequestDTO, this.user).getUser())
                .isEqualTo(this.user);
    }

    @Test
    void ofTheUserDetailsForUser_mapsFromAUserDetailsToAnUser_WheneverCalled() {

        Assertions.assertThat(Mapper.ofTheUserDetailsForUser(this.user))
                .isEqualTo(User.UserBuilder.builder()
                        .id(1L)
                        .name("name")
                        .username("this.username")
                        .password("password")
                        .authorities("ROLE_USER")
                        .build());
    }

    @Test
    void ofTheAddressForAddressResponseDTO_mapsFromAddressToAnAddressResponseDTO_wheneverCalled() {

        Address address = Address.AddressBuilder.builder()
                .id(1L)
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .user(User.UserBuilder.builder()
                        .id(1L)
                        .name("name")
                        .username("username")
                        .password("password")
                        .authorities("ROLE_USER")
                        .build())
                .build();

        Assertions.assertThat(Mapper.ofTheAddressForAddressResponseDTO(address))
                .isEqualTo(AddressResponseDTO.AddressResponseDTOBuilder.builder()
                        .id(1L)
                        .number("1")
                        .road("road")
                        .district("district")
                        .city("city")
                        .state("state")
                        .build()
                );
    }
}