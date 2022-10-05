package com.webservicepizzariazeff.www.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

    private User user;

    @BeforeEach
    void setUser() {
        this.user = User.UserBuilder.builder()
                .id(1L)
                .password("password")
                .username("username")
                .authorities("ROLE_USER")
                .build();
    }

    @Test
    void getUsername() {

        Assertions.assertThat(this.user.getUsername())
                .isEqualTo("username");
    }

    @Test
    void getId() {

        Assertions.assertThat(this.user.getId())
                .isEqualTo(1L);
    }

    @Test
    void testToString() {
        Assertions.assertThat(this.user)
                .hasToString("User{id=1, username='username', password='password', authorities='ROLE_USER'}");
    }

    @Test
    void testEquals() {

        User sameUser = User.UserBuilder.builder()
                .id(1L)
                .username("username")
                .authorities("ROLE_USER")
                .build();

        Assertions.assertThat(this.user.equals(sameUser)).isTrue();

        User differentUser = User.UserBuilder.builder()
                .id(2L)
                .username("username2")
                .authorities("ROLE_USER")
                .build();

        Assertions.assertThat(this.user.equals(differentUser)).isFalse();
    }

    @Test
    void testHashCode() {

        User sameUser = User.UserBuilder.builder()
                .id(1L)
                .username("username")
                .authorities("ROLE_USER")
                .build();

        Assertions.assertThat(this.user).hasSameHashCodeAs(sameUser);

        User differentUser = User.UserBuilder.builder()
                .id(2L)
                .username("username2")
                .authorities("ROLE_USER")
                .build();

        Assertions.assertThat(this.user.hashCode()).isNotEqualTo(differentUser.hashCode());
    }
}