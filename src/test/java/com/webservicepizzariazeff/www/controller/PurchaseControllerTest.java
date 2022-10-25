package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.response.AddressResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchaseResponseDTOForUser;
import com.webservicepizzariazeff.www.dto.response.PurchasedProductResponseDTO;
import com.webservicepizzariazeff.www.service.PurchaseService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
class PurchaseControllerTest {

    @InjectMocks
    private PurchaseController purchaseController;

    @Mock
    private PurchaseService purchaseService;

    private static User user;

    private static Map<Boolean, List<PurchaseResponseDTOForUser>> mapPurchasedProductResponseDTOForResponse;

    @BeforeAll
    static void setObjects() {

        user = User.UserBuilder.builder()
                .id(1L)
                .name("name")
                .username("username")
                .password("password")
                .authorities("ROLE_USER")
                .build();

        List<PurchasedProductResponseDTO> purchasedProductResponseDTOList = List.of(
                PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                        .name("calabresa")
                        .build(),
                PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                        .name("mussarela")
                        .build(),
                PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                        .name("bauru")
                        .build()
        );

        AddressResponseDTO addressResponseDTO = AddressResponseDTO.AddressResponseDTOBuilder.builder()
                .id(1L)
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .build();

        mapPurchasedProductResponseDTOForResponse = Map.of(
                false, List.of(
                        PurchaseResponseDTOForUser.PurchaseResponseDTOForUserBuilder.builder()
                                .id(3L)
                                .amount(new BigDecimal("30.00"))
                                .dateAndTime("12/34/5678T90:12")
                                .cardName("cardName")
                                .isActive(true)
                                .isFinished(false)
                                .isDelivered(false)
                                .isPaymentThroughTheWebsite(false)
                                .purchasedProductResponseDTOS(purchasedProductResponseDTOList)
                                .addressResponseDTO(addressResponseDTO)
                                .build(),
                        PurchaseResponseDTOForUser.PurchaseResponseDTOForUserBuilder.builder()
                                .id(2L)
                                .amount(new BigDecimal("20.00"))
                                .dateAndTime("12/34/5678T90:12")
                                .cardName("cardName")
                                .isActive(true)
                                .isFinished(false)
                                .isDelivered(false)
                                .isPaymentThroughTheWebsite(false)
                                .purchasedProductResponseDTOS(purchasedProductResponseDTOList)
                                .addressResponseDTO(addressResponseDTO)
                                .build()
                ),
                true, List.of(
                        PurchaseResponseDTOForUser.PurchaseResponseDTOForUserBuilder.builder()
                                .id(1L)
                                .amount(new BigDecimal("10.00"))
                                .dateAndTime("12/34/5678T90:12")
                                .cardName("cardName")
                                .isActive(true)
                                .isFinished(false)
                                .isDelivered(true)
                                .isPaymentThroughTheWebsite(false)
                                .purchasedProductResponseDTOS(purchasedProductResponseDTOList)
                                .addressResponseDTO(addressResponseDTO)
                                .build()
                )
        );
    }

    @BeforeEach
    void definitionOfBehaviorsForMocks() {

        BDDMockito.when(this.purchaseService.findByAllPurchasesOfTheAnUser(ArgumentMatchers.any(UserDetails.class)))
                .thenReturn(mapPurchasedProductResponseDTOForResponse);
    }

    @Test
    void findPurchasesByUser_returnsAMapOfTheAllActivePurchaseResponseDTOForUserOfTheAUser_wheneverCalled() {

        Assertions.assertThat(this.purchaseController.findPurchasesByUser(user))
                .isEqualTo(ResponseEntity.ok(mapPurchasedProductResponseDTOForResponse));
    }
}