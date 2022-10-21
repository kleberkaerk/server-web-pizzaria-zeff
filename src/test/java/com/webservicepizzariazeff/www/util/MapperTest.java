package com.webservicepizzariazeff.www.util;


import com.webservicepizzariazeff.www.domain.*;
import com.webservicepizzariazeff.www.dto.request.AddressRequestDTO;
import com.webservicepizzariazeff.www.dto.response.AddressResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class MapperTest {

    private static AddressRequestDTO addressRequestDTO;

    private static User user;

    private static Address address;

    private static Product product;

    private static Purchase purchase;

    @BeforeAll
    static void setObjects() {

        addressRequestDTO = AddressRequestDTO.AddressRequestDTOBuilder.builder()
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .build();

        user = User.UserBuilder.builder()
                .id(1L)
                .name("name")
                .username("this.username")
                .password("password")
                .authorities("ROLE_USER")
                .build();

        address = Address.AddressBuilder.builder()
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

        product = Product.ProductBuilder.builder()
                .id(1L)
                .name("name")
                .description("description")
                .type(Type.SALTY_PIZZA)
                .price(new BigDecimal("10.00"))
                .image("/image")
                .priceRating(PriceRating.REGULAR_PRICE)
                .build();

        purchase = Purchase.PurchaseBuilder.builder()
                .id(1L).build();
    }

    @Test
    void ofTheAddressDTOForAddress_mapsFromAAddressDTOToAnAddress_WheneverCalled() {

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOForAddress(addressRequestDTO, user).getNumber())
                .isEqualTo("1");

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOForAddress(addressRequestDTO, user).getRoad())
                .isEqualTo("road");

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOForAddress(addressRequestDTO, user).getDistrict())
                .isEqualTo("district");

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOForAddress(addressRequestDTO, user).getCity())
                .isEqualTo("city");

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOForAddress(addressRequestDTO, user).getState())
                .isEqualTo("state");

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOForAddress(addressRequestDTO, user).getUser())
                .isEqualTo(user);
    }

    @Test
    void ofTheUserDetailsForUser_mapsFromAUserDetailsToAnUser_WheneverCalled() {

        Assertions.assertThat(Mapper.ofTheUserDetailsForUser(user))
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

    @Test
    void ofTheProductForPurchasedProduct_mapsFromProductToPurchasedProduct_wheneverCalled() {

        Assertions.assertThat(Mapper.ofTheProductForPurchasedProduct(product, purchase).getName())
                .isEqualTo(product.getName());
    }
}