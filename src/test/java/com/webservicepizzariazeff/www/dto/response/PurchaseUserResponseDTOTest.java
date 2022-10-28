package com.webservicepizzariazeff.www.dto.response;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class PurchaseUserResponseDTOTest {

    private PurchaseUserResponseDTO purchaseUserResponseDTO;

    private static List<PurchasedProductResponseDTO> purchasedProductResponseDTOList;

    private static AddressResponseDTO addressResponseDTO;

    static void setPurchasedProductResponseDTOList(){

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
    }

    static void setAddressResponseDTO(){

        addressResponseDTO = AddressResponseDTO.AddressResponseDTOBuilder.builder()
                .id(1L)
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .build();
    }

    @BeforeAll
    static void initializeObjects(){

        setPurchasedProductResponseDTOList();
        setAddressResponseDTO();
    }

    @BeforeEach
    void setPurchaseResponseDTOForUser(){

        this.purchaseUserResponseDTO = PurchaseUserResponseDTO.PurchaseUserResponseDTOBuilder.builder()
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

        Assertions.assertThat(this.purchaseUserResponseDTO.getId())
                .isEqualTo(1L);
    }

    @Test
    void getAmount() {

        Assertions.assertThat(this.purchaseUserResponseDTO.getAmount())
                .isEqualTo(new BigDecimal("10.00"));
    }

    @Test
    void getDateAndTime() {

        Assertions.assertThat(this.purchaseUserResponseDTO.getDateAndTime())
                .isEqualTo("12/34/5678T90:12");
    }

    @Test
    void getCardName() {

        Assertions.assertThat(this.purchaseUserResponseDTO.getCardName())
                .isEqualTo("cardName");
    }

    @Test
    void isActive() {

        Assertions.assertThat(this.purchaseUserResponseDTO.isActive())
                .isTrue();
    }

    @Test
    void isFinished() {

        Assertions.assertThat(this.purchaseUserResponseDTO.isFinished())
                .isTrue();
    }

    @Test
    void isDelivered() {

        Assertions.assertThat(this.purchaseUserResponseDTO.isDelivered())
                .isTrue();
    }

    @Test
    void isPaymentThroughTheWebsite() {

        Assertions.assertThat(this.purchaseUserResponseDTO.isPaymentThroughTheWebsite())
                .isTrue();
    }

    @Test
    void getPurchasedProductResponseDTOS() {

        Assertions.assertThat(this.purchaseUserResponseDTO.getPurchasedProductResponseDTOS())
                .isEqualTo(purchasedProductResponseDTOList);
    }

    @Test
    void getAddressResponseDTO() {

        Assertions.assertThat(this.purchaseUserResponseDTO.getAddressResponseDTO())
                .isEqualTo(addressResponseDTO);
    }

    @Test
    void testToString() {

        Assertions.assertThat(this.purchaseUserResponseDTO)
                .hasToString("PurchaseResponseDTOForUser{" +
                        "id=" + this.purchaseUserResponseDTO.getId() +
                        ", amount=" + this.purchaseUserResponseDTO.getAmount() +
                        ", dateAndTime='" + this.purchaseUserResponseDTO.getDateAndTime() + '\'' +
                        ", cardName='" + this.purchaseUserResponseDTO.getCardName() + '\'' +
                        ", isActive=" + this.purchaseUserResponseDTO.isActive() +
                        ", isFinished=" + this.purchaseUserResponseDTO.isFinished() +
                        ", isDelivered=" + this.purchaseUserResponseDTO.isDelivered() +
                        ", isPaymentThroughTheWebsite=" + this.purchaseUserResponseDTO.isPaymentThroughTheWebsite() +
                        ", purchasedProductResponseDTOS=" + this.purchaseUserResponseDTO.getPurchasedProductResponseDTOS() +
                        ", addressResponseDTO=" + this.purchaseUserResponseDTO.getAddressResponseDTO() +
                        '}');
    }
}