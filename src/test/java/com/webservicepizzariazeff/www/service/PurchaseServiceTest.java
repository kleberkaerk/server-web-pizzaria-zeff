package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.Address;
import com.webservicepizzariazeff.www.domain.Purchase;
import com.webservicepizzariazeff.www.domain.PurchasedProduct;
import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.response.AddressResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchaseResponseDTOForUser;
import com.webservicepizzariazeff.www.dto.response.PurchasedProductResponseDTO;
import com.webservicepizzariazeff.www.exception.PurchaseFinishedException;
import com.webservicepizzariazeff.www.repository.PurchaseRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class PurchaseServiceTest {

    @InjectMocks
    private PurchaseService purchaseService;

    @Mock
    private PurchaseRepository purchaseRepository;

    private static User user;

    private static Purchase purchase;

    private static List<Purchase> purchases;

    private static Map<Boolean, List<PurchaseResponseDTOForUser>> mapPurchasedProductResponseDTOForResponse;

    @BeforeAll
    static void setObjects() {

        List<PurchasedProduct> purchasedProducts = List.of(
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

        user = User.UserBuilder.builder().id(1L)
                .name("name")
                .username("username")
                .password("password")
                .authorities("ROLE_USER")
                .build();

        Address address = Address.AddressBuilder.builder()
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .build();

        purchase = Purchase.PurchaseBuilder.builder()
                .id(1L)
                .amount(new BigDecimal("10.00"))
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

        purchases = List.of(
                Purchase.PurchaseBuilder.builder()
                        .id(1L)
                        .amount(new BigDecimal("10.00"))
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
                        .id(2L)
                        .amount(new BigDecimal("20.00"))
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
                        .amount(new BigDecimal("30.00"))
                        .dateAndTime("12/34/5678T90:12")
                        .cardName("cardName")
                        .isActive(true)
                        .isFinished(false)
                        .isDelivered(true)
                        .isPaymentThroughTheWebsite(false)
                        .user(user)
                        .address(address)
                        .purchasedProducts(purchasedProducts)
                        .build()
        );

        List<PurchasedProductResponseDTO> purchasedProductResponseDTOS = purchasedProducts.stream()
                .map(purchasedProduct -> PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                        .name(purchasedProduct.getName())
                        .build()
                ).toList();

        AddressResponseDTO addressResponseDTO = AddressResponseDTO.AddressResponseDTOBuilder.builder()
                .id(address.getId())
                .number(address.getNumber())
                .road(address.getRoad())
                .district(address.getDistrict())
                .city(address.getCity())
                .state(address.getState())
                .build();

        mapPurchasedProductResponseDTOForResponse = new HashMap<>(
                Map.of(
                        false, List.of(
                                PurchaseResponseDTOForUser.PurchaseResponseDTOForUserBuilder.builder()
                                        .id(purchases.get(1).getId())
                                        .amount(purchases.get(1).getAmount())
                                        .dateAndTime(purchases.get(1).getDateAndTime())
                                        .cardName(purchases.get(1).getCardName())
                                        .isActive(purchases.get(1).isActive())
                                        .isFinished(purchases.get(1).isFinished())
                                        .isDelivered(purchases.get(1).isDelivered())
                                        .isPaymentThroughTheWebsite(purchases.get(1).isPaymentThroughTheWebsite())
                                        .purchasedProductResponseDTOS(purchasedProductResponseDTOS)
                                        .addressResponseDTO(addressResponseDTO)
                                        .build(),
                                PurchaseResponseDTOForUser.PurchaseResponseDTOForUserBuilder.builder()
                                        .id(purchases.get(0).getId())
                                        .amount(purchases.get(0).getAmount())
                                        .dateAndTime(purchases.get(0).getDateAndTime())
                                        .cardName(purchases.get(0).getCardName())
                                        .isActive(purchases.get(0).isActive())
                                        .isFinished(purchases.get(0).isFinished())
                                        .isDelivered(purchases.get(0).isDelivered())
                                        .isPaymentThroughTheWebsite(purchases.get(0).isPaymentThroughTheWebsite())
                                        .purchasedProductResponseDTOS(purchasedProductResponseDTOS)
                                        .addressResponseDTO(addressResponseDTO)
                                        .build()
                        ),
                        true, List.of(
                                PurchaseResponseDTOForUser.PurchaseResponseDTOForUserBuilder.builder()
                                        .id(purchases.get(2).getId())
                                        .amount(purchases.get(2).getAmount())
                                        .dateAndTime(purchases.get(2).getDateAndTime())
                                        .cardName(purchases.get(2).getCardName())
                                        .isActive(purchases.get(2).isActive())
                                        .isFinished(purchases.get(2).isFinished())
                                        .isDelivered(purchases.get(2).isDelivered())
                                        .isPaymentThroughTheWebsite(purchases.get(2).isPaymentThroughTheWebsite())
                                        .purchasedProductResponseDTOS(purchasedProductResponseDTOS)
                                        .addressResponseDTO(addressResponseDTO)
                                        .build()
                        )
                )
        );
    }

    @BeforeEach
    void definitionOfBehaviorsForMocks() {

        BDDMockito.when(this.purchaseRepository.save(ArgumentMatchers.any(Purchase.class)))
                .thenReturn(purchases.get(0));

        BDDMockito.when(this.purchaseRepository.findByUserAndIsActive(ArgumentMatchers.any(User.class), ArgumentMatchers.anyBoolean()))
                .thenReturn(purchases);

        BDDMockito.when(this.purchaseRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(purchases.get(0)));

        BDDMockito.doNothing()
                .when(this.purchaseRepository).updateIsActiveById(ArgumentMatchers.anyBoolean(), ArgumentMatchers.any(Long.class));
    }

    @Test
    void save_persistAndReturnsANewPurchaseInTheDatabase_wheneverCalled() {

        Assertions.assertThat(this.purchaseService.save(purchase))
                .isNotNull()
                .hasToString(purchase.toString());
    }

    @Test
    void findByAllPurchasesOfTheAnUser_returnsAMapOfTheAllActivePurchaseResponseDTOForUserOfTheAUser_wheneverCalled() {

        Assertions.assertThat(this.purchaseService.findByAllPurchasesOfTheAnUser(user))
                .isNotNull()
                .hasToString(mapPurchasedProductResponseDTOForResponse.toString());
    }

    @Test
    void cancelPurchaseOfTheUser_updateTheFieldIsActiveToFalseFromAPurchase_whenThePassedIdIsNotNullAndIsAnIdOfAPurchaseThatIsNotFinishedOrDelivered() {

        Assertions.assertThatCode(() -> this.purchaseService.cancelPurchaseOfTheUser(1L, "pt-BR"))
                .doesNotThrowAnyException();
    }

    @Test
    void cancelPurchaseOfTheUser_throwsResponseStatusException_whenThePassedIdNotExists() {

        BDDMockito.when(this.purchaseRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseService.cancelPurchaseOfTheUser(0L, "pt-BR"));
    }

    @Test
    void cancelPurchaseOfTheUser_throwsPurchaseFinishedException_whenThePassedIdIsAnIdOfAPurchaseThatIsFinishedOrDelivered() {

        BDDMockito.when(this.purchaseRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(Purchase.PurchaseBuilder.builder()
                        .id(1L)
                        .isFinished(true)
                        .isDelivered(true)
                        .build()));

        Assertions.assertThatExceptionOfType(PurchaseFinishedException.class)
                .isThrownBy(() -> this.purchaseService.cancelPurchaseOfTheUser(1L, "pt-BR"));
    }
}