package com.webservicepizzariazeff.www.util;


import com.webservicepizzariazeff.www.domain.*;
import com.webservicepizzariazeff.www.dto.request.AddressRequestDTO;
import com.webservicepizzariazeff.www.dto.response.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class MapperTest {

    private static AddressRequestDTO addressRequestDTO;

    private static User user;

    private static Address address;

    private static AddressResponseDTO addressResponseDTOToComparisonInOfTheAddressToAddressResponseDTO;

    private static Product product;

    private static List<PurchasedProduct> purchasedProducts;

    private static Purchase purchase;

    private static PurchaseUserResponseDTO purchaseUserResponseDTOToComparisonInOfThePurchaseToPurchaseUserResponseDTO;

    private static PurchaseRestaurantResponseDTO purchaseRestaurantResponseDTOToComparisonInfThePurchaseToPurchaseRestaurantResponseDTO;

    private static ProductResponseDTO productResponseDTOToComparisonInOfTheProductToProductResponseDTO;

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
                .build();
    }

    static void setAddressResponseDTOToComparisonInOfTheAddressToAddressResponseDTO() {

        addressResponseDTOToComparisonInOfTheAddressToAddressResponseDTO = AddressResponseDTO.AddressResponseDTOBuilder.builder()
                .id(1L)
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .build();
    }

    static void setProduct() {

        product = Product.ProductBuilder.builder()
                .id(1L)
                .name("name")
                .description("")
                .price(new BigDecimal("10"))
                .type(Type.SALTY_ESFIHA)
                .priceRating(PriceRating.PROMOTION)
                .isStocked(true)
                .image("/image1")
                .build();
    }

    static void setPurchasedProducts() {

        purchasedProducts = List.of(
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(1L)
                        .name("name")
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(2L)
                        .name("name")
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(3L)
                        .name("name")
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(4L)
                        .name("name")
                        .build()
        );
    }

    static void setPurchase() {

        purchase = Purchase.PurchaseBuilder.builder()
                .id(1L)
                .amount(new BigDecimal("10"))
                .dateAndTime("12/34/5678T90:12")
                .cardName("cardName")
                .isActive(true)
                .isFinished(false)
                .isDelivered(false)
                .isPaymentThroughTheWebsite(false)
                .user(user)
                .address(address)
                .purchasedProducts(purchasedProducts)
                .build();
    }

    static void setPurchaseUserResponseDTOToComparisonInOfThePurchaseToPurchaseUserResponseDTO() {

        purchaseUserResponseDTOToComparisonInOfThePurchaseToPurchaseUserResponseDTO = PurchaseUserResponseDTO.PurchaseUserResponseDTOBuilder.builder()
                .id(purchase.getId())
                .amount(purchase.getAmount())
                .dateAndTime(purchase.getDateAndTime())
                .cardName(purchase.getCardName())
                .isActive(purchase.isActive())
                .isFinished(purchase.isFinished())
                .isDelivered(purchase.isDelivered())
                .isPaymentThroughTheWebsite(purchase.isPaymentThroughTheWebsite())
                .purchasedProductResponseDTOS(
                        purchasedProducts.stream()
                                .map(purchasedProduct -> PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                                        .name(purchasedProduct.getName())
                                        .build())
                                .toList()
                )
                .addressResponseDTO(Mapper.ofTheAddressToAddressResponseDTO(purchase.getAddress()))
                .build();
    }

    static void setPurchaseRestaurantResponseDTOToComparisonInfThePurchaseToPurchaseRestaurantResponseDTO() {

        purchaseRestaurantResponseDTOToComparisonInfThePurchaseToPurchaseRestaurantResponseDTO =
                PurchaseRestaurantResponseDTO.PurchaseRestaurantResponseDTOBuilder.builder()
                        .id(purchase.getId())
                        .clientName(purchase.getUser().getName())
                        .isActive(purchase.isActive())
                        .isFinished(purchase.isFinished())
                        .isDelivered(purchase.isDelivered())
                        .isPaymentThroughTheWebsite(purchase.isPaymentThroughTheWebsite())
                        .purchasedProductResponseDTOS(
                                purchasedProducts.stream()
                                        .map(purchasedProduct -> PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                                                .name(purchasedProduct.getName())
                                                .build())
                                        .toList()
                        )
                        .addressResponseDTO(Mapper.ofTheAddressToAddressResponseDTO(purchase.getAddress()))
                        .build();
    }

    static void setProductResponseDTOToComparisonInOfTheProductToProductResponseDTO() {

        productResponseDTOToComparisonInOfTheProductToProductResponseDTO = ProductResponseDTO.ProductResponseDTOBuilder.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .type(product.getType())
                .priceRating(product.getPriceRating())
                .isStocked(product.isStocked())
                .image(product.getImage())
                .build();
    }

    @BeforeAll
    static void initializeObjects() {

        setAddressRequestDTO();
        setUser();
        setAddress();
        setAddressResponseDTOToComparisonInOfTheAddressToAddressResponseDTO();
        setProduct();
        setPurchasedProducts();
        setPurchase();
        setPurchaseUserResponseDTOToComparisonInOfThePurchaseToPurchaseUserResponseDTO();
        setPurchaseRestaurantResponseDTOToComparisonInfThePurchaseToPurchaseRestaurantResponseDTO();
        setProductResponseDTOToComparisonInOfTheProductToProductResponseDTO();
    }

    @Test
    void ofTheAddressRequestDTOToAddress_mapsFromAAddressRequestDTOToAnAddress_WheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.ofTheAddressRequestDTOToAddress(addressRequestDTO, user))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOToAddress(addressRequestDTO, user).getId())
                .isNull();

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOToAddress(addressRequestDTO, user).getNumber())
                .isNotNull()
                .isEqualTo("1");

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOToAddress(addressRequestDTO, user).getRoad())
                .isNotNull()
                .isEqualTo("road");

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOToAddress(addressRequestDTO, user).getDistrict())
                .isNotNull()
                .isEqualTo("district");

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOToAddress(addressRequestDTO, user).getCity())
                .isNotNull()
                .isEqualTo("city");

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOToAddress(addressRequestDTO, user).getState())
                .isNotNull()
                .isEqualTo("state");

        Assertions.assertThat(Mapper.ofTheAddressRequestDTOToAddress(addressRequestDTO, user).getUser())
                .isNotNull()
                .isEqualTo(user);
    }

    @Test
    void ofTheUserDetailsToUser_mapsFromAUserDetailsToAnUser_WheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.ofTheUserDetailsToUser(user))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.ofTheUserDetailsToUser(user))
                .isNotNull()
                .isEqualTo(user);
    }

    @Test
    void ofTheAddressToAddressResponseDTO_mapsFromAddressToAnAddressResponseDTO_wheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.ofTheAddressToAddressResponseDTO(address))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.ofTheAddressToAddressResponseDTO(address))
                .isNotNull()
                .isEqualTo(addressResponseDTOToComparisonInOfTheAddressToAddressResponseDTO);
    }

    @Test
    void ofTheProductToPurchasedProduct_mapsFromProductToPurchasedProduct_wheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.ofTheProductToPurchasedProduct(product, purchase))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.ofTheProductToPurchasedProduct(product, purchase).getId())
                .isNull();

        Assertions.assertThat(Mapper.ofTheProductToPurchasedProduct(product, purchase).getName())
                .isNotNull()
                .isEqualTo(product.getName());
    }

    @Test
    void ofThePurchaseToPurchaseUserResponseDTO_mapsFromPurchaseToPurchaseUserResponseDTO_wheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.ofThePurchaseToPurchaseUserResponseDTO(purchase))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.ofThePurchaseToPurchaseUserResponseDTO(purchase))
                .isNotNull()
                .isEqualTo(purchaseUserResponseDTOToComparisonInOfThePurchaseToPurchaseUserResponseDTO);
    }

    @Test
    void ofThePurchaseToPurchaseResponseDTOForRestaurant_mapsFromPurchaseToPurchaseRestaurantResponseDTO_wheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.ofThePurchaseToPurchaseRestaurantResponseDTO(purchase))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.ofThePurchaseToPurchaseRestaurantResponseDTO(purchase))
                .isNotNull()
                .isEqualTo(purchaseRestaurantResponseDTOToComparisonInfThePurchaseToPurchaseRestaurantResponseDTO);
    }

    @Test
    void ofTheProductToProductResponseDTO_mapsFromProductToProductResponseDTO_wheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.ofTheProductToProductResponseDTO(product))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.ofTheProductToProductResponseDTO(product))
                .isEqualTo(productResponseDTOToComparisonInOfTheProductToProductResponseDTO);
    }
}