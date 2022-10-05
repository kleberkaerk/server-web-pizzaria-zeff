package com.webservicepizzariazeff.www.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDTOTest {

    UserDTO userDTO;

    @BeforeEach
    void setUserDTO() {

        this.userDTO = UserDTO.UserDTOBuilder.builder()
                .password("password")
                .username("username")
                .build();
    }

    @Test
    void getUsername() {

        Assertions.assertThat(this.userDTO.getUsername())
                .isEqualTo("username");
    }

    @Test
    void getPassword() {

        Assertions.assertThat(this.userDTO.getPassword())
                .isEqualTo("password");
    }

    @Test
    void testToString() {

        Assertions.assertThat(this.userDTO)
                .hasToString("UserDTO{username='username', password='password'}");
    }
}