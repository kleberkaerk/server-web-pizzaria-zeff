package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.response.AddressResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchaseRestaurantResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchaseUserResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchasedProductResponseDTO;
import com.webservicepizzariazeff.www.exception.PurchaseFinishedException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

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

    private static List<PurchasedProductResponseDTO> purchasedProductResponseDTOList;

    private static AddressResponseDTO addressResponseDTO;

    private static Map<Boolean, List<PurchaseUserResponseDTO>> mapPurchaseUserResponseDTOForResponse;

    private static Map<Boolean, List<PurchaseRestaurantResponseDTO>> mapPurchaseRestaurantResponseDTOForResponse;

    static void setUser() {

        user = User.UserBuilder.builder()
                .id(1L)
                .name("name")
                .username("username")
                .password("password")
                .authorities("ROLE_USER")
                .build();
    }

    static void setPurchasedProductResponseDTOList() {

        purchasedProductResponseDTOList = List.of(
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
    }

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

    static void setMapPurchaseUserResponseDTOForResponse() {

        mapPurchaseUserResponseDTOForResponse = Map.of(
                false, List.of(
                        PurchaseUserResponseDTO.PurchaseUserResponseDTOBuilder.builder()
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
                        PurchaseUserResponseDTO.PurchaseUserResponseDTOBuilder.builder()
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
                        PurchaseUserResponseDTO.PurchaseUserResponseDTOBuilder.builder()
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

    static void setMapPurchaseRestaurantResponseDTOForResponse() {

        mapPurchaseRestaurantResponseDTOForResponse = Map.of(
                false, List.of(
                        PurchaseRestaurantResponseDTO.PurchaseRestaurantResponseDTOBuilder.builder()
                                .id(3L)
                                .clientName("clientName3")
                                .isActive(false)
                                .isFinished(false)
                                .isDelivered(false)
                                .isPaymentThroughTheWebsite(false)
                                .purchasedProductResponseDTOS(purchasedProductResponseDTOList)
                                .addressResponseDTO(addressResponseDTO)
                                .build()
                ),
                true, List.of(
                        PurchaseRestaurantResponseDTO.PurchaseRestaurantResponseDTOBuilder.builder()
                                .id(1L)
                                .clientName("clientName1")
                                .isActive(true)
                                .isFinished(false)
                                .isDelivered(false)
                                .isPaymentThroughTheWebsite(false)
                                .purchasedProductResponseDTOS(purchasedProductResponseDTOList)
                                .addressResponseDTO(addressResponseDTO)
                                .build(),
                        PurchaseRestaurantResponseDTO.PurchaseRestaurantResponseDTOBuilder.builder()
                                .id(2L)
                                .clientName("clientName2")
                                .isActive(true)
                                .isFinished(true)
                                .isDelivered(false)
                                .isPaymentThroughTheWebsite(true)
                                .purchasedProductResponseDTOS(purchasedProductResponseDTOList)
                                .addressResponseDTO(addressResponseDTO)
                                .build(),
                        PurchaseRestaurantResponseDTO.PurchaseRestaurantResponseDTOBuilder.builder()
                                .id(4L)
                                .clientName("clientName4")
                                .isActive(true)
                                .isFinished(false)
                                .isDelivered(false)
                                .isPaymentThroughTheWebsite(false)
                                .purchasedProductResponseDTOS(purchasedProductResponseDTOList)
                                .addressResponseDTO(addressResponseDTO)
                                .build()
                )
        );
    }

    @BeforeAll
    static void initializeObjects() {

        setUser();
        setPurchasedProductResponseDTOList();
        setAddressResponseDTO();
        setMapPurchaseUserResponseDTOForResponse();
        setMapPurchaseRestaurantResponseDTOForResponse();
    }

    @BeforeEach
    void definitionOfBehaviorsForMocks() {

        BDDMockito.when(this.purchaseService.findByAllPurchasesOfTheAnUser(ArgumentMatchers.any(UserDetails.class)))
                .thenReturn(mapPurchaseUserResponseDTOForResponse);

        BDDMockito.doNothing()
                .when(this.purchaseService).cancelPurchaseOfTheUser(ArgumentMatchers.any(Long.class), ArgumentMatchers.anyString());

        BDDMockito.when(this.purchaseService.findByAllUsersPurchases())
                .thenReturn(mapPurchaseRestaurantResponseDTOForResponse);

        BDDMockito.doNothing()
                .when(this.purchaseService).preparePurchase(ArgumentMatchers.any(Long.class), ArgumentMatchers.anyString());

        BDDMockito.doNothing()
                .when(this.purchaseService).deliverPurchase(ArgumentMatchers.any(Long.class), ArgumentMatchers.anyString());
    }

    @Test
    void findPurchasesByUser_returnsAMapOfTheAllActivePurchaseUserResponseDTOOfTheAUserAndAStatusCodeOk_wheneverCalled() {

        Assertions.assertThatCode(() -> this.purchaseController.findPurchasesOfTheUser(user))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.purchaseController.findPurchasesOfTheUser(user))
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(mapPurchaseUserResponseDTOForResponse));
    }

    @Test
    void cancelPurchase_returnsAStatusCodeNoContent_whenTheIdPassedIsValid() {

        Assertions.assertThatCode(() -> this.purchaseController.cancelPurchase(1L, "pt-BR"))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.purchaseController.cancelPurchase(1L, "pt-BR"))
                .isNotNull()
                .isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Test
    void cancelPurchase_throwsResponseStatusException_whenTheIdPassedIsNotExists() {

        BDDMockito.doThrow(ResponseStatusException.class)
                .when(this.purchaseService).cancelPurchaseOfTheUser(ArgumentMatchers.any(Long.class), ArgumentMatchers.anyString());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseController.cancelPurchase(0L, "pt-Br"));
    }

    @Test
    void cancelPurchase_throwsPurchaseFinishedException_whenTheIdPassedIsInvalid() {

        BDDMockito.doThrow(PurchaseFinishedException.class)
                .when(this.purchaseService).cancelPurchaseOfTheUser(ArgumentMatchers.any(Long.class), ArgumentMatchers.anyString());

        Assertions.assertThatExceptionOfType(PurchaseFinishedException.class)
                .isThrownBy(() -> this.purchaseController.cancelPurchase(1L, "pt-Br"));
    }

    @Test
    void findUsersPurchases_returnsAMapOfTheAllPurchaseRestaurantResponseDTONoDeliveredAndAStatusCodeOk_wheneverCalled() {

        Assertions.assertThatCode(() -> this.purchaseController.findUsersPurchases())
                .doesNotThrowAnyException();

        Assertions.assertThat(this.purchaseController.findUsersPurchases())
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(mapPurchaseRestaurantResponseDTOForResponse));
    }

    @Test
    void purchasePreparation_returnsAStatusCodeNoContent_whenTheIdPassedIsValid() {

        Assertions.assertThatCode(() -> this.purchaseController.purchasePreparation(1L, "pt-BR"))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.purchaseController.purchasePreparation(1L, "pt-BR"))
                .isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Test
    void purchasePreparation_throwsResponseStatusException_whenTheIdPassedIsNotExistsOrIsInvalid() {

        BDDMockito.doThrow(ResponseStatusException.class)
                .when(this.purchaseService).preparePurchase(ArgumentMatchers.any(Long.class), ArgumentMatchers.anyString());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseController.purchasePreparation(1L, "pt-BR"));
    }

    @Test
    void purchaseDelivery_returnsAStatusCodeNoContent_whenTheIdPassedIsValid() {

        Assertions.assertThatCode(() -> this.purchaseController.purchaseDelivery(1L, "pt-BR"))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.purchaseController.purchaseDelivery(1L, "pt-BR"))
                .isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Test
    void purchaseDelivery_throwsResponseStatusException_whenTheIdPassedIsNotExistsOrIsInvalid() {

        BDDMockito.doThrow(ResponseStatusException.class)
                .when(this.purchaseService).deliverPurchase(ArgumentMatchers.any(Long.class), ArgumentMatchers.anyString());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseController.purchaseDelivery(1L, "pt-BR"));
    }
}