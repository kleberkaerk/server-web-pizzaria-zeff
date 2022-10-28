package com.webservicepizzariazeff.www.util;


import com.webservicepizzariazeff.www.domain.*;
import com.webservicepizzariazeff.www.dto.request.AddressRequestDTO;
import com.webservicepizzariazeff.www.dto.response.AddressResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchaseRestaurantResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchaseUserResponseDTO;
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

    private static List<PurchasedProduct> purchasedProductList;

    private static Purchase purchase;

    private static PurchaseUserResponseDTO purchaseUserResponseDTO;

    private static List<PurchasedProductResponseDTO> purchasedProductResponseDTOS;

    private static AddressResponseDTO addressResponseDTO;

    static void setAddressRequestDTO() {

        addressRequestDTO = AddressRequestDTO.AddressRequestDTOBuilder.builder()
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .build();
    }

    static void setUser() {

        user = User.UserBuilder.builder()
                .id(1L)
                .name("name")
                .username("username")
                .password("password")
                .authorities("ROLE_USER")
                .build();
    }

    static void setAddress() {

        address = Address.AddressBuilder.builder()
                .id(1L)
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .user(user)
                .build();
    }

    static void setProduct() {

        product = Product.ProductBuilder.builder()
                .id(1L)
                .name("name")
                .description("description")
                .type(Type.SALTY_PIZZA)
                .price(new BigDecimal("10.00"))
                .image("/image")
                .priceRating(PriceRating.REGULAR_PRICE)
                .build();
    }

    static void setPurchasedProductList() {

        purchasedProductList = List.of(
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
    }

    static void setPurchase() {

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
    }

    static void setPurchaseUserResponseDTO() {

        purchaseUserResponseDTO = PurchaseUserResponseDTO.PurchaseUserResponseDTOBuilder.builder()
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

    static void setPurchasedProductResponseDTOS() {

        purchasedProductResponseDTOS = List.of(
                PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                        .name(purchasedProductList.get(0).getName())
                        .build(),
                PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                        .name(purchasedProductList.get(1).getName())
                        .build(),
                PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                        .name(purchasedProductList.get(2).getName())
                        .build()
        );
    }

    static void setAddressResponseDTO() {

        addressResponseDTO = AddressResponseDTO.AddressResponseDTOBuilder.builder()
                .id(address.getId())
                .number(address.getNumber())
                .road(address.getRoad())
                .district(address.getDistrict())
                .city(address.getCity())
                .state(address.getState())
                .build();
    }

    @BeforeAll
    static void initializeObjects() {

        setAddressRequestDTO();
        setUser();
        setAddress();
        setProduct();
        setPurchasedProductList();
        setPurchase();
        setPurchaseUserResponseDTO();
        setPurchasedProductResponseDTOS();
        setAddressResponseDTO();
    }

    @Test
    void ofTheAddressDTOForAddress_mapsFromAAddressRequestDTOToAnAddress_WheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.ofTheAddressRequestDTOForAddress(addressRequestDTO, user))
                .doesNotThrowAnyException();

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

        Assertions.assertThatCode(() -> Mapper.ofTheUserDetailsForUser(user))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.ofTheUserDetailsForUser(user))
                .isEqualTo(User.UserBuilder.builder()
                        .id(1L)
                        .name("name")
                        .username("username")
                        .password("password")
                        .authorities("ROLE_USER")
                        .build());
    }

    @Test
    void ofTheAddressForAddressResponseDTO_mapsFromAddressToAnAddressResponseDTO_wheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.ofTheAddressForAddressResponseDTO(address))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.ofTheAddressForAddressResponseDTO(address))
                .isEqualTo(addressResponseDTO);
    }

    @Test
    void ofTheProductForPurchasedProduct_mapsFromProductToPurchasedProduct_wheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.ofTheProductForPurchasedProduct(product, purchase))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.ofTheProductForPurchasedProduct(product, purchase))
                .hasToString(PurchasedProduct.PurchasedProductBuilder.builder()
                        .name(product.getName())
                        .purchase(purchase)
                        .build().toString());
    }

    @Test
    void ofThePurchaseForPurchaseResponseDTOForUser_mapsFromPurchaseToPurchaseUserResponseDTO_wheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.ofThePurchaseForPurchaseUserResponseDTO(purchase))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.ofThePurchaseForPurchaseUserResponseDTO(purchase).toString())
                .hasToString(purchaseUserResponseDTO.toString());
    }

    @Test
    void ofThePurchaseForPurchaseResponseDTOForRestaurant_mapsFromPurchaseToPurchaseRestaurantResponseDTO_wheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.ofThePurchaseForPurchaseRestaurantResponseDTO(purchase))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.ofThePurchaseForPurchaseRestaurantResponseDTO(purchase))
                .isNotNull()
                .hasToString(PurchaseRestaurantResponseDTO.PurchaseRestaurantResponseDTOBuilder.builder()
                        .id(purchase.getId())
                        .clientName(purchase.getUser().getName())
                        .isActive(purchase.isActive())
                        .isFinished(purchase.isFinished())
                        .isDelivered(purchase.isDelivered())
                        .isPaymentThroughTheWebsite(purchase.isPaymentThroughTheWebsite())
                        .purchasedProductResponseDTOS(purchasedProductResponseDTOS)
                        .addressResponseDTO(addressResponseDTO)
                        .build().toString());
    }
}