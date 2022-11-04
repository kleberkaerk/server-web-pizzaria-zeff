package com.webservicepizzariazeff.www.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

class UserTest {

    private User user;

    private User sameUser;

    private User differentUser;

    @BeforeEach
    void setObjects() {

        this.user = User.UserBuilder.builder()
                .id(1L)
                .name("name")
                .password("password")
                .username("username")
                .authorities("ROLE_USER,ROLE_ADMIN")
                .build();

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
    void getId() {

        Assertions.assertThat(user.getId())
                .isEqualTo(1L);
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
    void getPassword() {

        Assertions.assertThat(user.getPassword())
                .isEqualTo("password");
    }

    @Test
    void getAuthorities() {

        Assertions.assertThat(user.getAuthorities())
                .isEqualTo(List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    void isAccountNonExpired() {

        Assertions.assertThat(user.isAccountNonExpired())
                .isTrue();
    }

    @Test
    void isAccountNonLocked() {

        Assertions.assertThat(user.isAccountNonLocked())
                .isTrue();
    }

    @Test
    void isCredentialsNonExpired() {

        Assertions.assertThat(user.isCredentialsNonExpired())
                .isTrue();
    }

    @Test
    void isEnabled() {

        Assertions.assertThat(user.isEnabled())
                .isTrue();
    }

    @Test
    void testToString() {

        String authorities = this.user.getAuthorities().toString().substring(1, 22).replaceFirst(" ", "");

        Assertions.assertThat(this.user)
                .hasToString("User{" +
                        "id=" + this.user.getId() +
                        ", name='" + this.user.getName() + '\'' +
                        ", username='" + this.user.getUsername() + '\'' +
                        ", password='" + this.user.getPassword() + '\'' +
                        ", authorities='" + authorities + '\'' +
                        '}');
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