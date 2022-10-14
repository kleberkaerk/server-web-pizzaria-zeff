package com.webservicepizzariazeff.www.dto.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserDTOTest {

    private static UserDTO userDTO;

    @BeforeAll
    static void setUserDTO() {

        userDTO = UserDTO.UserDTOBuilder.builder()
                .name("name")
                .password("password")
                .username("username")
                .build();
    }

    @Test
    void getUsername() {

        Assertions.assertThat(userDTO.getUsername())
                .isEqualTo("username");
    }

    @Test
    void getPassword() {

        Assertions.assertThat(userDTO.getPassword())
                .isEqualTo("password");
    }

    @Test
    void getName() {

        Assertions.assertThat(userDTO.getName())
                .isEqualTo("name");
    }
}