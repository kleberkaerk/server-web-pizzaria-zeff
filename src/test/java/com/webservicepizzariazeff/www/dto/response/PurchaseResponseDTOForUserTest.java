package com.webservicepizzariazeff.www.dto.response;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class PurchaseResponseDTOForUserTest {

    private PurchaseResponseDTOForUser purchaseResponseDTOForUser;

    private static List<PurchasedProductResponseDTO> purchasedProductResponseDTOList;

    private static AddressResponseDTO addressResponseDTO;

    @BeforeAll
    static void setObjects(){

        purchasedProductResponseDTOList = List.of(
                PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                        .name("name1")
                        .build(),
                PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                        .name("name2")
                        .build(),
                PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                        .name("name3")
                        .build()
        );

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
    void setPurchaseResponseDTOForUser(){

        this.purchaseResponseDTOForUser = PurchaseResponseDTOForUser.PurchaseResponseDTOForUserBuilder.builder()
                .id(1L)
                .amount(new BigDecimal("10.00"))
                .dateAndTime("12/34/5678T90:12")
                .cardName("cardName")
                .isActive(true)
                .isFinished(true)
                .isDelivered(true)
                .isPaymentThroughTheWebsite(true)
                .purchasedProductResponseDTOS(purchasedProductResponseDTOList)
                .addressResponseDTO(addressResponseDTO)
                .build();
    }

    @Test
    void getId() {

        Assertions.assertThat(this.purchaseResponseDTOForUser.getId())
                .isEqualTo(1L);
    }

    @Test
    void getAmount() {

        Assertions.assertThat(this.purchaseResponseDTOForUser.getAmount())
                .isEqualTo(new BigDecimal("10.00"));
    }

    @Test
    void getDateAndTime() {

        Assertions.assertThat(this.purchaseResponseDTOForUser.getDateAndTime())
                .isEqualTo("12/34/5678T90:12");
    }

    @Test
    void getCardName() {

        Assertions.assertThat(this.purchaseResponseDTOForUser.getCardName())
                .isEqualTo("cardName");
    }

    @Test
    void isActive() {

        Assertions.assertThat(this.purchaseResponseDTOForUser.isActive())
                .isTrue();
    }

    @Test
    void isFinished() {

        Assertions.assertThat(this.purchaseResponseDTOForUser.isFinished())
                .isTrue();
    }

    @Test
    void isDelivered() {

        Assertions.assertThat(this.purchaseResponseDTOForUser.isDelivered())
                .isTrue();
    }

    @Test
    void isPaymentThroughTheWebsite() {

        Assertions.assertThat(this.purchaseResponseDTOForUser.isPaymentThroughTheWebsite())
                .isTrue();
    }

    @Test
    void getPurchasedProductResponseDTOS() {

        Assertions.assertThat(this.purchaseResponseDTOForUser.getPurchasedProductResponseDTOS())
                .isEqualTo(purchasedProductResponseDTOList);
    }

    @Test
    void getAddressResponseDTO() {

        Assertions.assertThat(this.purchaseResponseDTOForUser.getAddressResponseDTO())
                .isEqualTo(addressResponseDTO);
    }

    @Test
    void testToString() {

        Assertions.assertThat(this.purchaseResponseDTOForUser)
                .hasToString("PurchaseResponseDTOForUser{" +
                        "id=" + this.purchaseResponseDTOForUser.getId() +
                        ", amount=" + this.purchaseResponseDTOForUser.getAmount() +
                        ", dateAndTime='" + this.purchaseResponseDTOForUser.getDateAndTime() + '\'' +
                        ", cardName='" + this.purchaseResponseDTOForUser.getCardName() + '\'' +
                        ", isActive=" + this.purchaseResponseDTOForUser.isActive() +
                        ", isFinished=" + this.purchaseResponseDTOForUser.isFinished() +
                        ", isDelivered=" + this.purchaseResponseDTOForUser.isDelivered() +
                        ", isPaymentThroughTheWebsite=" + this.purchaseResponseDTOForUser.isPaymentThroughTheWebsite() +
                        ", purchasedProductResponseDTOS=" + this.purchaseResponseDTOForUser.getPurchasedProductResponseDTOS() +
                        ", addressResponseDTO=" + this.purchaseResponseDTOForUser.getAddressResponseDTO() +
                        '}');
    }
}