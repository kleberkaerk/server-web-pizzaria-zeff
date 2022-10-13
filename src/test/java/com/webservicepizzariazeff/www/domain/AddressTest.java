package com.webservicepizzariazeff.www.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddressTest {

    private static final User userWhoOwnsTheAddress = User.UserBuilder.builder()
            .id(1L)
            .name("name")
            .username("username")
            .password("password")
            .authorities("ROLE_USER")
            .build();

    private static Address address;

    private Address sameAddress;

    private Address differentAddress;

    @BeforeAll
    static void setAddress() {

        address = Address.AddressBuilder.builder()
                .id(1L)
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .user(userWhoOwnsTheAddress)
                .build();
    }

    @BeforeEach
    void setObjects() {

        this.sameAddress = Address.AddressBuilder.builder()
                .id(1L)
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .user(userWhoOwnsTheAddress)
                .build();

        this.differentAddress = Address.AddressBuilder.builder()
                .id(2L)
                .number("2")
                .road("road2")
                .district("district2")
                .city("city2")
                .state("state2")
                .user(userWhoOwnsTheAddress)
                .build();
    }

    @Test
    void getId() {

        Assertions.assertThat(address.getId())
                .isEqualTo(1L);
    }

    @Test
    void getNumber() {

        Assertions.assertThat(address.getNumber())
                .isEqualTo("1");
    }

    @Test
    void getRoad() {

        Assertions.assertThat(address.getRoad())
                .isEqualTo("road");
    }

    @Test
    void getDistrict() {

        Assertions.assertThat(address.getDistrict())
                .isEqualTo("district");
    }

    @Test
    void getCity() {

        Assertions.assertThat(address.getCity())
                .isEqualTo("city");
    }

    @Test
    void getState() {

        Assertions.assertThat(address.getState())
                .isEqualTo("state");
    }

    @Test
    void getUser() {

        Assertions.assertThat(address.getUser())
                .isEqualTo(User.UserBuilder.builder()
                        .id(1L)
                        .name("name")
                        .username("username")
                        .password("password")
                        .authorities("ROLE_USER")
                        .build());
    }

    @Test
    void testToString() {

        Assertions.assertThat(address)
                .hasToString("Address{id=1, number='1', road='road', district='district', city='city', state='state', user=User{id=1, name='name', username='username', password='password', authorities='ROLE_USER'}}");
    }

    @Test
    void testEquals() {

        Assertions.assertThat(address.equals(this.sameAddress))
                .isTrue();

        Assertions.assertThat(address.equals(this.differentAddress))
                .isFalse();
    }

    @Test
    void testHashCode() {

        Assertions.assertThat(address)
                .hasSameHashCodeAs(this.sameAddress);

        Assertions.assertThat(address.hashCode())
                .isNotEqualTo(this.differentAddress.hashCode());
    }
}