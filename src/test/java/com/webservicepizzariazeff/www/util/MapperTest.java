package com.webservicepizzariazeff.www.util;


import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.request.AddressDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MapperTest {

    private AddressDTO addressDTO;

    private User user;

    @BeforeEach
    void setObjects() {
        this.addressDTO = AddressDTO.AddressDTOBuilder.builder()
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

        Assertions.assertThat(Mapper.ofTheAddressDTOForAddress(this.addressDTO, this.user).getNumber())
                .isEqualTo("1");

        Assertions.assertThat(Mapper.ofTheAddressDTOForAddress(this.addressDTO, this.user).getRoad())
                .isEqualTo("road");

        Assertions.assertThat(Mapper.ofTheAddressDTOForAddress(this.addressDTO, this.user).getDistrict())
                .isEqualTo("district");

        Assertions.assertThat(Mapper.ofTheAddressDTOForAddress(this.addressDTO, this.user).getCity())
                .isEqualTo("city");

        Assertions.assertThat(Mapper.ofTheAddressDTOForAddress(this.addressDTO, this.user).getState())
                .isEqualTo("state");

        Assertions.assertThat(Mapper.ofTheAddressDTOForAddress(this.addressDTO, this.user).getUser())
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
}