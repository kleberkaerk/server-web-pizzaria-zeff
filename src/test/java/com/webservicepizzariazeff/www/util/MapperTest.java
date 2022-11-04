package com.webservicepizzariazeff.www.util;


import com.webservicepizzariazeff.www.domain.*;
import com.webservicepizzariazeff.www.dto.request.AddressRequestDTO;
import com.webservicepizzariazeff.www.dto.request.ProductRequestDTO;
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

    private static AddressResponseDTO addressResponseDTOToComparisonInFromAddressToAddressResponseDTO;

    private static Product product;

    private static List<PurchasedProduct> purchasedProducts;

    private static Purchase purchase;

    private static PurchaseUserResponseDTO purchaseUserResponseDTOToComparisonInFromPurchaseToPurchaseUserResponseDTO;

    private static PurchaseRestaurantResponseDTO purchaseRestaurantResponseDTOToComparisonInFromPurchaseToPurchaseRestaurantResponseDTO;

    private static ProductResponseDTO productResponseDTOToComparisonInFromProductToProductResponseDTO;

    private static ProductRequestDTO productRequestDTO;

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

    static void setAddressResponseDTOToComparisonInFromAddressToAddressResponseDTO() {

        addressResponseDTOToComparisonInFromAddressToAddressResponseDTO = AddressResponseDTO.AddressResponseDTOBuilder.builder()
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
                .description("description")
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

    static void setPurchaseUserResponseDTOToComparisonInFromPurchaseToPurchaseUserResponseDTO() {

        purchaseUserResponseDTOToComparisonInFromPurchaseToPurchaseUserResponseDTO = PurchaseUserResponseDTO.PurchaseUserResponseDTOBuilder.builder()
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
                .addressResponseDTO(Mapper.fromAddressToAddressResponseDTO(purchase.getAddress()))
                .build();
    }

    static void setPurchaseRestaurantResponseDTOToComparisonInFromPurchaseToPurchaseRestaurantResponseDTO() {

        purchaseRestaurantResponseDTOToComparisonInFromPurchaseToPurchaseRestaurantResponseDTO =
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
                        .addressResponseDTO(Mapper.fromAddressToAddressResponseDTO(purchase.getAddress()))
                        .build();
    }

    static void setProductResponseDTOToComparisonInFromProductToProductResponseDTO() {

        productResponseDTOToComparisonInFromProductToProductResponseDTO = ProductResponseDTO.ProductResponseDTOBuilder.builder()
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

    static void setProductRequestDTO() {

        productRequestDTO = ProductRequestDTO.ProductRequestDTOBuilder.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .type(product.getType())
                .priceRating(product.getPriceRating())
                .build();
    }

    @BeforeAll
    static void initializeObjects() {

        setAddressRequestDTO();
        setUser();
        setAddress();
        setAddressResponseDTOToComparisonInFromAddressToAddressResponseDTO();
        setProduct();
        setPurchasedProducts();
        setPurchase();
        setPurchaseUserResponseDTOToComparisonInFromPurchaseToPurchaseUserResponseDTO();
        setPurchaseRestaurantResponseDTOToComparisonInFromPurchaseToPurchaseRestaurantResponseDTO();
        setProductResponseDTOToComparisonInFromProductToProductResponseDTO();
        setProductRequestDTO();
    }

    @Test
    void fromAddressRequestDTOToAddress_mapsFromAAddressRequestDTOToAnAddress_WheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.fromAddressRequestDTOToAddress(addressRequestDTO, user))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.fromAddressRequestDTOToAddress(addressRequestDTO, user).getId())
                .isNull();

        Assertions.assertThat(Mapper.fromAddressRequestDTOToAddress(addressRequestDTO, user).getNumber())
                .isNotNull()
                .isEqualTo("1");

        Assertions.assertThat(Mapper.fromAddressRequestDTOToAddress(addressRequestDTO, user).getRoad())
                .isNotNull()
                .isEqualTo("road");

        Assertions.assertThat(Mapper.fromAddressRequestDTOToAddress(addressRequestDTO, user).getDistrict())
                .isNotNull()
                .isEqualTo("district");

        Assertions.assertThat(Mapper.fromAddressRequestDTOToAddress(addressRequestDTO, user).getCity())
                .isNotNull()
                .isEqualTo("city");

        Assertions.assertThat(Mapper.fromAddressRequestDTOToAddress(addressRequestDTO, user).getState())
                .isNotNull()
                .isEqualTo("state");

        Assertions.assertThat(Mapper.fromAddressRequestDTOToAddress(addressRequestDTO, user).getUser())
                .isNotNull()
                .isEqualTo(user);
    }

    @Test
    void fromUserDetailsToUser_mapsFromAUserDetailsToAnUser_WheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.fromUserDetailsToUser(user))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.fromUserDetailsToUser(user))
                .isNotNull()
                .isEqualTo(user);
    }

    @Test
    void fromAddressToAddressResponseDTO_mapsFromAddressToAnAddressResponseDTO_wheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.fromAddressToAddressResponseDTO(address))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.fromAddressToAddressResponseDTO(address))
                .isNotNull()
                .isEqualTo(addressResponseDTOToComparisonInFromAddressToAddressResponseDTO);
    }

    @Test
    void fromProductToPurchasedProduct_mapsFromProductToPurchasedProduct_wheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.fromProductToPurchasedProduct(product, purchase))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.fromProductToPurchasedProduct(product, purchase).getId())
                .isNull();

        Assertions.assertThat(Mapper.fromProductToPurchasedProduct(product, purchase).getName())
                .isNotNull()
                .isEqualTo(product.getName());
    }

    @Test
    void fromPurchaseToPurchaseUserResponseDTO_mapsFromPurchaseToPurchaseUserResponseDTO_wheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.fromPurchaseToPurchaseUserResponseDTO(purchase))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.fromPurchaseToPurchaseUserResponseDTO(purchase))
                .isNotNull()
                .isEqualTo(purchaseUserResponseDTOToComparisonInFromPurchaseToPurchaseUserResponseDTO);
    }

    @Test
    void fromPurchaseToPurchaseRestaurantResponseDTO_mapsFromPurchaseToPurchaseRestaurantResponseDTO_wheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.fromPurchaseToPurchaseRestaurantResponseDTO(purchase))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.fromPurchaseToPurchaseRestaurantResponseDTO(purchase))
                .isNotNull()
                .isEqualTo(purchaseRestaurantResponseDTOToComparisonInFromPurchaseToPurchaseRestaurantResponseDTO);
    }

    @Test
    void fromProductToProductResponseDTO_mapsFromProductToProductResponseDTO_wheneverCalled() {

        Assertions.assertThatCode(() -> Mapper.fromProductToProductResponseDTO(product))
                .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.fromProductToProductResponseDTO(product))
                .isNotNull()
                .isEqualTo(productResponseDTOToComparisonInFromProductToProductResponseDTO);
    }

    @Test
    void fromProductRequestDTOToProduct_mapsFromProductProductRequestDTOToProduct_wheneverCalled() {

        Assertions.assertThatCode(()-> Mapper.fromProductRequestDTOToProduct(productRequestDTO))
                        .doesNotThrowAnyException();

        Assertions.assertThat(Mapper.fromProductRequestDTOToProduct(productRequestDTO).getName())
                .isNotNull()
                .isEqualTo(product.getName());

        Assertions.assertThat(Mapper.fromProductRequestDTOToProduct(productRequestDTO).getDescription())
                .isNotNull()
                .isEqualTo(product.getDescription());

        Assertions.assertThat(Mapper.fromProductRequestDTOToProduct(productRequestDTO).getPrice())
                .isNotNull()
                .isEqualTo(product.getPrice());

        Assertions.assertThat(Mapper.fromProductRequestDTOToProduct(productRequestDTO).getType())
                .isNotNull()
                .isEqualTo(product.getType());

        Assertions.assertThat(Mapper.fromProductRequestDTOToProduct(productRequestDTO).getPriceRating())
                .isNotNull()
                .isEqualTo(product.getPriceRating());
    }

    @Test
    void fromAcceptLanguageToStringArray_mapsFromStringAcceptLanguageToStringArray_wheneverCalled(){

        Assertions.assertThat(Mapper.fromAcceptLanguageToStringArray("pt-BR"))
                .isEqualTo(new String[]{"pt", "BR"});
    }
}