package com.webservicepizzariazeff.www.dto.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserRequestDTOTest {

    private UserRequestDTO userRequestDTO;

    @BeforeEach
    void setUserRequestDTO() {

        this.userRequestDTO = UserRequestDTO.UserDTOBuilder.builder()
                .name("name")
                .password("password")
                .username("username")
                .build();
    }

    @Test
    void getUsername() {

        Assertions.assertThat(this.userRequestDTO.getUsername())
                .isEqualTo("username");
    }

    @Test
    void getPassword() {

        Assertions.assertThat(this.userRequestDTO.getPassword())
                .isEqualTo("password");
    }

    @Test
    void getName() {

        Assertions.assertThat(this.userRequestDTO.getName())
                .isEqualTo("name");
    }
}