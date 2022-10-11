package com.webservicepizzariazeff.www.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

    private static User user;

    private User sameUser;
    private User differentUser;

    @BeforeAll
    static void setUser() {
        user = User.UserBuilder.builder()
                .id(1L)
                .name("name")
                .password("password")
                .username("username")
                .authorities("ROLE_USER")
                .build();
    }

    @BeforeEach
    void setObjects() {

        this.sameUser = User.UserBuilder.builder()
                .id(1L)
                .username("username")
                .authorities("ROLE_USER")
                .build();

        this.differentUser = User.UserBuilder.builder()
                .id(2L)
                .username("username2")
                .authorities("ROLE_USER")
                .build();
    }

    @Test
    void getName() {

        Assertions.assertThat(user.getName())
                .isEqualTo("name");
    }

    @Test
    void getUsername() {

        Assertions.assertThat(user.getUsername())
                .isEqualTo("username");
    }

    @Test
    void getId() {

        Assertions.assertThat(user.getId())
                .isEqualTo(1L);
    }

    @Test
    void testToString() {

        Assertions.assertThat(user)
                .hasToString("User{id=1, name='name', username='username', password='password', authorities='ROLE_USER'}");
    }

    @Test
    void testEquals() {

        Assertions.assertThat(user.equals(sameUser)).isTrue();

        Assertions.assertThat(user.equals(differentUser)).isFalse();
    }

    @Test
    void testHashCode() {

        Assertions.assertThat(user).hasSameHashCodeAs(sameUser);

        Assertions.assertThat(user.hashCode()).isNotEqualTo(differentUser.hashCode());
    }
}