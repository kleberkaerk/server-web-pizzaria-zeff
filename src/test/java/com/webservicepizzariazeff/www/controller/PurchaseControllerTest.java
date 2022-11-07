package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.domain.Address;
import com.webservicepizzariazeff.www.domain.Purchase;
import com.webservicepizzariazeff.www.domain.PurchasedProduct;
import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.response.PurchaseRestaurantResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchaseUserResponseDTO;
import com.webservicepizzariazeff.www.exception.PurchaseFinishedException;
import com.webservicepizzariazeff.www.service.PurchaseService;
import com.webservicepizzariazeff.www.util.Mapper;
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
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
class PurchaseControllerTest {

    @InjectMocks
    private PurchaseController purchaseController;

    @Mock
    private PurchaseService purchaseService;

    private static User user;

    private static Address address;

    private static List<PurchasedProduct> purchasedProducts;

    private static List<Purchase> purchases;

    private static Map<Boolean, List<PurchaseUserResponseDTO>> mapFindPurchasesOfTheUser;

    private static List<PurchaseUserResponseDTO> purchaseUserResponseDTOSToComparisonInFindPurchasesOfTheUser;

    private static Map<Boolean, List<PurchaseRestaurantResponseDTO>> mapFindByAllUsersPurchases;

    private static List<PurchaseRestaurantResponseDTO> purchaseRestaurantResponseDTOSToComparisonInFindUsersPurchases;

    private static List<PurchaseRestaurantResponseDTO> purchaseRestaurantResponseDTOSFindDeliveredPurchases;

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
                .road("road1")
                .district("district1")
                .city("city1")
                .state("state1")
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

    static void setPurchases() {

        purchases = List.of(
                Purchase.PurchaseBuilder.builder()
                        .id(1L)
                        .amount(new BigDecimal("10"))
                        .dateAndTime("12/34/5678T90:12")
                        .cardName("cardName")
                        .isActive(true)
                        .isFinished(false)
                        .isDelivered(true)
                        .isPaymentThroughTheWebsite(false)
                        .user(user)
                        .address(address)
                        .purchasedProducts(purchasedProducts)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(2L)
                        .amount(new BigDecimal("20"))
                        .dateAndTime("12/34/5678T90:12")
                        .cardName("cardName")
                        .isActive(true)
                        .isFinished(false)
                        .isDelivered(false)
                        .isPaymentThroughTheWebsite(false)
                        .user(user)
                        .address(address)
                        .purchasedProducts(purchasedProducts)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(3L)
                        .amount(new BigDecimal("30"))
                        .dateAndTime("12/34/5678T90:12")
                        .cardName("cardName")
                        .isActive(false)
                        .isFinished(false)
                        .isDelivered(false)
                        .isPaymentThroughTheWebsite(false)
                        .user(user)
                        .address(address)
                        .purchasedProducts(purchasedProducts)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(4L)
                        .cardName("cardName4")
                        .isActive(true)
                        .isFinished(true)
                        .isDelivered(true)
                        .isPaymentThroughTheWebsite(false)
                        .amount(new BigDecimal("40"))
                        .dateAndTime("12/34/5678T90:12")
                        .user(user)
                        .address(address)
                        .purchasedProducts(purchasedProducts)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(5L)
                        .cardName("cardName5")
                        .isActive(true)
                        .isFinished(true)
                        .isDelivered(true)
                        .isPaymentThroughTheWebsite(false)
                        .amount(new BigDecimal("50"))
                        .dateAndTime("12/34/5678T90:12")
                        .user(user)
                        .address(address)
                        .purchasedProducts(purchasedProducts)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(6L)
                        .cardName("cardName6")
                        .isActive(true)
                        .isFinished(true)
                        .isDelivered(true)
                        .isPaymentThroughTheWebsite(false)
                        .amount(new BigDecimal("60"))
                        .dateAndTime("12/34/5678T90:12")
                        .user(user)
                        .address(address)
                        .purchasedProducts(purchasedProducts)
                        .build()
        );
    }

    static void setMapFindPurchasesOfTheUser() {

        mapFindPurchasesOfTheUser = purchases.stream()
                .filter(Purchase::isActive)
                .map(Mapper::fromPurchaseToPurchaseUserResponseDTO)
                .collect(Collectors.groupingBy(PurchaseUserResponseDTO::isDelivered));
    }

    static void setPurchaseUserResponseDTOSToComparisonInFindPurchasesOfTheUser() {

        purchaseUserResponseDTOSToComparisonInFindPurchasesOfTheUser = mapFindPurchasesOfTheUser.get(true);
    }

    static void setMapFindByAllUsersPurchases() {

        mapFindByAllUsersPurchases = purchases.stream()
                .filter(purchase -> !purchase.isDelivered())
                .map(Mapper::fromPurchaseToPurchaseRestaurantResponseDTO)
                .collect(Collectors.groupingBy(PurchaseRestaurantResponseDTO::isActive));
    }

    static void setPurchaseRestaurantResponseDTOSToComparisonInFindUsersPurchases() {

        purchaseRestaurantResponseDTOSToComparisonInFindUsersPurchases = mapFindByAllUsersPurchases.get(false);
    }

    static void setPurchaseRestaurantResponseDTOSFindDeliveredPurchases() {

        purchaseRestaurantResponseDTOSFindDeliveredPurchases = purchases.stream()
                .filter(Purchase::isDelivered)
                .map(Mapper::fromPurchaseToPurchaseRestaurantResponseDTO)
                .toList();
    }

    @BeforeAll
    static void initializeObjects() {

        setUser();
        setAddress();
        setPurchasedProducts();
        setPurchases();
        setMapFindPurchasesOfTheUser();
        setPurchaseUserResponseDTOSToComparisonInFindPurchasesOfTheUser();
        setMapFindByAllUsersPurchases();
        setPurchaseRestaurantResponseDTOSToComparisonInFindUsersPurchases();
        setPurchaseRestaurantResponseDTOSFindDeliveredPurchases();
    }

    @BeforeEach
    void definitionOfBehaviorsForMocks() {

        BDDMockito.when(this.purchaseService.findByAllPurchasesOfTheAnUser(ArgumentMatchers.any(UserDetails.class)))
                .thenReturn(mapFindPurchasesOfTheUser);

        BDDMockito.doNothing()
                .when(this.purchaseService).cancelPurchaseOfTheUser(
                        ArgumentMatchers.any(UserDetails.class),
                        ArgumentMatchers.any(Long.class),
                        ArgumentMatchers.anyString()
                );

        BDDMockito.when(this.purchaseService.findByAllUsersPurchases())
                .thenReturn(mapFindByAllUsersPurchases);

        BDDMockito.doNothing()
                .when(this.purchaseService).preparePurchase(ArgumentMatchers.any(Long.class), ArgumentMatchers.anyString());

        BDDMockito.doNothing()
                .when(this.purchaseService).deliverPurchase(ArgumentMatchers.any(Long.class), ArgumentMatchers.anyString());

        BDDMockito.doNothing()
                .when(this.purchaseService).deleteAPurchase(ArgumentMatchers.any(Long.class));

        BDDMockito.when(this.purchaseService.findDeliveredPurchases())
                .thenReturn(purchaseRestaurantResponseDTOSFindDeliveredPurchases);

        BDDMockito.doNothing()
                .when(this.purchaseService).disableDeliveryById(ArgumentMatchers.any(Long.class));
    }

    @Test
    void findPurchasesOfTheUser_returnsAMapOrderedByIsFinishedOfTheAllActivePurchaseUserResponseDTOOfAUserAndAStatusCodeOk_wheneverCalled() {

        Assertions.assertThatCode(() -> this.purchaseController.findPurchasesOfTheUser(user))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.purchaseController.findPurchasesOfTheUser(user))
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(mapFindPurchasesOfTheUser));

        Assertions.assertThat(this.purchaseController.findPurchasesOfTheUser(user).getBody())
                .isNotNull()
                .containsEntry(true, purchaseUserResponseDTOSToComparisonInFindPurchasesOfTheUser);
    }

    @Test
    void cancelPurchase_returnsAStatusCodeNoContent_whenTheIdPassedIsValid() {

        Assertions.assertThatCode(() -> this.purchaseController.cancelPurchase(user, 1L, "pt-BR"))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.purchaseController.cancelPurchase(user, 1L, "pt-BR"))
                .isNotNull()
                .isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Test
    void cancelPurchase_throwsResponseStatusException_whenTheIdPassedIsNotExists() {

        BDDMockito.doThrow(ResponseStatusException.class)
                .when(this.purchaseService).cancelPurchaseOfTheUser(ArgumentMatchers.any(UserDetails.class), ArgumentMatchers.any(Long.class), ArgumentMatchers.anyString());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseController.cancelPurchase(user, 0L, "pt-Br"));
    }

    @Test
    void cancelPurchase_throwsPurchaseFinishedException_whenTheIdPassedIsInvalid() {

        BDDMockito.doThrow(PurchaseFinishedException.class)
                .when(this.purchaseService).cancelPurchaseOfTheUser(ArgumentMatchers.any(UserDetails.class), ArgumentMatchers.any(Long.class), ArgumentMatchers.anyString());

        Assertions.assertThatExceptionOfType(PurchaseFinishedException.class)
                .isThrownBy(() -> this.purchaseController.cancelPurchase(user, 1L, "pt-Br"));
    }

    @Test
    void findUsersPurchases_returnsAMapOfTheAllPurchaseRestaurantResponseDTONoDeliveredAndAStatusCodeOk_wheneverCalled() {

        Assertions.assertThatCode(() -> this.purchaseController.findUsersPurchases())
                .doesNotThrowAnyException();

        Assertions.assertThat(this.purchaseController.findUsersPurchases())
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(mapFindByAllUsersPurchases));

        Assertions.assertThat(this.purchaseController.findUsersPurchases().getBody())
                .isNotNull()
                .containsEntry(false, purchaseRestaurantResponseDTOSToComparisonInFindUsersPurchases);
    }

    @Test
    void purchasePreparation_returnsAStatusCodeNoContent_whenThePassedIdIsValid() {

        Assertions.assertThatCode(() -> this.purchaseController.purchasePreparation(1L, "pt-BR"))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.purchaseController.purchasePreparation(1L, "pt-BR"))
                .isNotNull()
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
                .isNotNull()
                .isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Test
    void purchaseDelivery_throwsResponseStatusException_whenTheIdPassedIsNotExistsOrIsInvalid() {

        BDDMockito.doThrow(ResponseStatusException.class)
                .when(this.purchaseService).deliverPurchase(ArgumentMatchers.any(Long.class), ArgumentMatchers.anyString());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseController.purchaseDelivery(1L, "pt-BR"));
    }

    @Test
    void deletePurchase_returnsAStatusCodeNoContent_whenTheIdPassedIsValid() {

        Assertions.assertThatCode(() -> this.purchaseController.deletePurchase(1L))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.purchaseController.deletePurchase(1L))
                .isNotNull()
                .isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Test
    void deletePurchase_throwsResponseStatusException_whenThePassedIdDoesNotExist() {

        BDDMockito.doThrow(ResponseStatusException.class)
                .when(this.purchaseService).deleteAPurchase(ArgumentMatchers.any(Long.class));

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseController.deletePurchase(1L));
    }

    @Test
    void deliveredPurchases_returnsAListOfTheLast50PurchaseRestaurantResponseDTODeliveredAndAStatusCodeOk_wheneverCalled() {

        Assertions.assertThatCode(() -> this.purchaseController.deliveredPurchases())
                .doesNotThrowAnyException();

        Assertions.assertThat(this.purchaseController.deliveredPurchases())
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(purchaseRestaurantResponseDTOSFindDeliveredPurchases));

        Assertions.assertThat(this.purchaseController.deliveredPurchases().getBody())
                .isNotNull()
                .asList()
                .hasSize(purchaseRestaurantResponseDTOSFindDeliveredPurchases.size())
                .isEqualTo(purchaseRestaurantResponseDTOSFindDeliveredPurchases)
                .contains(purchaseRestaurantResponseDTOSFindDeliveredPurchases.get(0));
    }

    @Test
    void disableDelivery_returnsAStatusCodeNoContent_whenThePassedIdIsValid() {

        Assertions.assertThatCode(() -> this.purchaseController.disableDelivery(1L))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.purchaseController.disableDelivery(1L))
                .isNotNull()
                .isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Test
    void disableDelivery_throwsResponseStatusException_whenThePassedIdDoesNotExist() {

        BDDMockito.doThrow(ResponseStatusException.class)
                .when(this.purchaseService).disableDeliveryById(ArgumentMatchers.any(Long.class));

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseController.disableDelivery(2L));
    }
}