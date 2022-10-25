package com.webservicepizzariazeff.www.util;


import com.webservicepizzariazeff.www.domain.*;
import com.webservicepizzariazeff.www.dto.request.AddressRequestDTO;
import com.webservicepizzariazeff.www.dto.response.AddressResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchaseResponseDTOForUser;
import com.webservicepizzariazeff.www.dto.response.PurchasedProductResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class MapperTest {

    private static AddressRequestDTO addressRequestDTO;

    private static User user;

    private static Address address;

    private static Product product;

    private static Purchase purchase;

    private static PurchaseResponseDTOForUser purchaseResponseDTOForUser;

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

        List<PurchasedProduct> purchasedProductList = List.of(
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(1L)
                        .name("name1")
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(2L)
                        .name("name2")
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(3L)
                        .name("name3")
                        .build()
        );

        purchase = Purchase.PurchaseBuilder.builder()
                .id(1L)
                .amount(new BigDecimal("10.00"))
                .dateAndTime("12/34/5678T90:12")
                .cardName("cardName")
                .isActive(true)
                .isFinished(true)
                .isDelivered(true)
                .isPaymentThroughTheWebsite(true)
                .purchasedProducts(purchasedProductList)
                .user(user)
                .address(address)
                .build();

        purchaseResponseDTOForUser = PurchaseResponseDTOForUser.PurchaseResponseDTOForUserBuilder.builder()
                .id(purchase.getId())
                .amount(purchase.getAmount())
                .dateAndTime(purchase.getDateAndTime())
                .cardName(purchase.getCardName())
                .isActive(purchase.isActive())
                .isFinished(purchase.isFinished())
                .isDelivered(purchase.isDelivered())
                .isPaymentThroughTheWebsite(purchase.isPaymentThroughTheWebsite())
                .purchasedProductResponseDTOS(List.of(
                        PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                                .name(purchasedProductList.get(0).getName())
                                .build(),
                        PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                                .name(purchasedProductList.get(1).getName())
                                .build(),
                        PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                                .name(purchasedProductList.get(2).getName())
                                .build()
                ))
                .addressResponseDTO(AddressResponseDTO.AddressResponseDTOBuilder.builder()
                        .id(address.getId())
                        .number(address.getNumber())
                        .road(address.getRoad())
                        .district(address.getDistrict())
                        .city(address.getCity())
                        .state(address.getState())
                        .build()
                ).build();
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

    @Test
    void ofThePurchaseForPurchaseResponseDTOForUser_mapsFromPurchaseToPurchaseResponseDTOForUser_wheneverCalled() {

        Assertions.assertThat(Mapper.ofThePurchaseForPurchaseResponseDTOForUser(purchase).toString())
                .hasToString(purchaseResponseDTOForUser.toString());
    }
}