package com.webservicepizzariazeff.www.dto.response;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class PurchaseRestaurantResponseDTOTest {

    private PurchaseRestaurantResponseDTO purchaseRestaurantResponseDTO;

    private static List<PurchasedProductResponseDTO> purchasedProductResponseDTOS;

    private static AddressResponseDTO addressResponseDTO;

    @BeforeAll
    static void setPurchasedProductResponseDTOS() {

        purchasedProductResponseDTOS = List.of(
                PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                        .name("name1")
                        .build(),
                PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                        .name("name2")
                        .build(),
                PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                        .name("name3")
                        .build(),
                PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                        .name("name4")
                        .build()
        );
    }

    @BeforeAll
    static void setAddressResponseDTO() {

        addressResponseDTO = AddressResponseDTO.AddressResponseDTOBuilder.builder()
                .id(1L)
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .build();
    }

    @BeforeEach
    void setPurchaseRestaurantResponseDTO() {

        this.purchaseRestaurantResponseDTO = PurchaseRestaurantResponseDTO.PurchaseRestaurantResponseDTOBuilder.builder()
                .id(1L)
                .clientName("clientName")
                .isActive(true)
                .isFinished(true)
                .isDelivered(true)
                .isPaymentThroughTheWebsite(true)
                .purchasedProductResponseDTOS(purchasedProductResponseDTOS)
                .addressResponseDTO(addressResponseDTO)
                .build();
    }

    @Test
    void getId() {

        Assertions.assertThat(this.purchaseRestaurantResponseDTO.getId())
                .isNotNull()
                .isEqualTo(1L);
    }

    @Test
    void getClientName() {

        Assertions.assertThat(this.purchaseRestaurantResponseDTO.getClientName())
                .isNotNull()
                .isEqualTo("clientName");
    }

    @Test
    void isActive() {

        Assertions.assertThat(this.purchaseRestaurantResponseDTO.isActive())
                .isTrue();
    }

    @Test
    void isFinished() {

        Assertions.assertThat(this.purchaseRestaurantResponseDTO.isFinished())
                .isTrue();
    }

    @Test
    void isDelivered() {

        Assertions.assertThat(this.purchaseRestaurantResponseDTO.isDelivered())
                .isTrue();
    }

    @Test
    void isPaymentThroughTheWebsite() {

        Assertions.assertThat(this.purchaseRestaurantResponseDTO.isPaymentThroughTheWebsite())
                .isTrue();
    }

    @Test
    void getPurchasedProductResponseDTOS() {

        Assertions.assertThat(this.purchaseRestaurantResponseDTO.getPurchasedProductResponseDTOS())
                .isNotNull()
                .isEqualTo(purchasedProductResponseDTOS);
    }

    @Test
    void getAddressResponseDTO() {

        Assertions.assertThat(this.purchaseRestaurantResponseDTO.getAddressResponseDTO())
                .isNotNull()
                .isEqualTo(addressResponseDTO);
    }

    @Test
    void testToString() {

        Assertions.assertThat(this.purchaseRestaurantResponseDTO)
                .hasToString("PurchaseResponseDTOForRestaurant{" +
                        "id=" + this.purchaseRestaurantResponseDTO.getId() +
                        ", clientName='" + this.purchaseRestaurantResponseDTO.getClientName() + '\'' +
                        ", isActive=" + this.purchaseRestaurantResponseDTO.isActive() +
                        ", isFinished=" + this.purchaseRestaurantResponseDTO.isFinished() +
                        ", isDelivered=" + this.purchaseRestaurantResponseDTO.isDelivered() +
                        ", isPaymentThroughTheWebsite=" + this.purchaseRestaurantResponseDTO.isPaymentThroughTheWebsite() +
                        ", purchasedProductResponseDTOS=" + this.purchaseRestaurantResponseDTO.getPurchasedProductResponseDTOS() +
                        ", addressResponseDTO=" + this.purchaseRestaurantResponseDTO.getAddressResponseDTO() +
                        '}');
    }
}