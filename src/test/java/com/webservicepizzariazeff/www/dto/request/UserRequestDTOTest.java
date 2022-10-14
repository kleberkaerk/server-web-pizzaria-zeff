package com.webservicepizzariazeff.www.dto.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserRequestDTOTest {

    private static UserRequestDTO userRequestDTO;

    @BeforeAll
    static void setUserDTO() {

        userRequestDTO = UserRequestDTO.UserDTOBuilder.builder()
                .name("name")
                .password("password")
                .username("username")
                .build();
    }

    @Test
    void getUsername() {

        Assertions.assertThat(userRequestDTO.getUsername())
                .isEqualTo("username");
    }

    @Test
    void getPassword() {

        Assertions.assertThat(userRequestDTO.getPassword())
                .isEqualTo("password");
    }

    @Test
    void getName() {

        Assertions.assertThat(userRequestDTO.getName())
                .isEqualTo("name");
    }
}