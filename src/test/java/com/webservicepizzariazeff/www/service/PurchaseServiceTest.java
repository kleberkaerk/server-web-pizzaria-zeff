package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.Address;
import com.webservicepizzariazeff.www.domain.Purchase;
import com.webservicepizzariazeff.www.domain.PurchasedProduct;
import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.response.PurchaseRestaurantResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchaseUserResponseDTO;
import com.webservicepizzariazeff.www.exception.PurchaseFinishedException;
import com.webservicepizzariazeff.www.repository.PurchaseRepository;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class PurchaseServiceTest {

    @InjectMocks
    private PurchaseService purchaseService;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private PurchasedProductService purchasedProductService;

    private static User user;

    private static Address address;

    private static Purchase purchase;

    private static List<PurchasedProduct> purchasedProducts;

    private static List<Purchase> purchases;

    private static List<Purchase> PurchasesFindByUserAndIsActive;

    private static List<PurchaseUserResponseDTO> purchasesToComparisonInFindByAllPurchasesOfTheAnUser;

    private static List<Purchase> purchasesFindByIsDelivered;

    private static List<PurchaseRestaurantResponseDTO> purchasesToComparisonInFndByAllUsersPurchases;

    private static Purchase purchaseDeliverPurchase;

    private static List<PurchaseRestaurantResponseDTO> purchaseRestaurantResponseDTOToComparisonInFindDeliveredPurchases;

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

    static void setPurchasedProducts() {

        purchasedProducts = List.of(
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
                .isFinished(false)
                .isDelivered(false)
                .isPaymentThroughTheWebsite(false)
                .user(user)
                .address(address)
                .purchasedProducts(purchasedProducts)
                .build();
    }

    static void setPurchases() {

        purchases = List.of(
                Purchase.PurchaseBuilder.builder()
                        .id(1L)
                        .amount(new BigDecimal("10.00"))
                        .dateAndTime("12/34/5678T90:12")
                        .cardName("cardName1")
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
                        .cardName("cardName2")
                        .isActive(false)
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
                        .cardName("cardName3")
                        .isActive(true)
                        .isFinished(false)
                        .isDelivered(true)
                        .isPaymentThroughTheWebsite(false)
                        .user(user)
                        .address(address)
                        .purchasedProducts(purchasedProducts)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(4L)
                        .amount(new BigDecimal("40.00"))
                        .dateAndTime("12/34/5678T90:12")
                        .cardName("cardName4")
                        .isActive(true)
                        .isFinished(false)
                        .isDelivered(true)
                        .isPaymentThroughTheWebsite(false)
                        .user(user)
                        .address(address)
                        .purchasedProducts(purchasedProducts)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(5L)
                        .amount(new BigDecimal("50.00"))
                        .dateAndTime("12/34/5678T90:12")
                        .cardName("cardName5")
                        .isActive(true)
                        .isFinished(false)
                        .isDelivered(true)
                        .isPaymentThroughTheWebsite(false)
                        .user(user)
                        .address(address)
                        .purchasedProducts(purchasedProducts)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(6L)
                        .amount(new BigDecimal("60.00"))
                        .dateAndTime("12/34/5678T90:12")
                        .cardName("cardName6")
                        .isActive(true)
                        .isFinished(true)
                        .isDelivered(true)
                        .isPaymentThroughTheWebsite(false)
                        .user(user)
                        .address(address)
                        .purchasedProducts(purchasedProducts)
                        .build()
        );
    }

    static void setPurchasesFindByUserAndIsActive() {

        PurchasesFindByUserAndIsActive = purchases.stream()
                .filter(Purchase::isActive)
                .toList();
    }

    static void setPurchasesToComparisonInFindByAllPurchasesOfTheAnUser() {

        purchasesToComparisonInFindByAllPurchasesOfTheAnUser = PurchasesFindByUserAndIsActive.stream()
                .map(Mapper::fromPurchaseToPurchaseUserResponseDTO)
                .sorted(Comparator.comparing(PurchaseUserResponseDTO::getId).reversed())
                .filter(PurchaseUserResponseDTO::isDelivered)
                .toList();
    }

    static void setPurchasesFindByIsDelivered() {

        purchasesFindByIsDelivered = purchases.stream()
                .filter(purchase -> !purchase.isDelivered())
                .toList();
    }

    static void setPurchasesToComparisonInFndByAllUsersPurchases() {
        purchasesToComparisonInFndByAllUsersPurchases = purchasesFindByIsDelivered.stream()
                .map(Mapper::fromPurchaseToPurchaseRestaurantResponseDTO)
                .sorted(Comparator.comparing(PurchaseRestaurantResponseDTO::getId))
                .filter(PurchaseRestaurantResponseDTO::isActive)
                .toList();
    }

    static void setPurchaseDeliverPurchase() {

        purchaseDeliverPurchase = Purchase.PurchaseBuilder.builder()
                .id(1L)
                .amount(new BigDecimal("10.00"))
                .dateAndTime("12/34/5678T90:12")
                .cardName("cardName")
                .isActive(true)
                .isFinished(true)
                .isDelivered(false)
                .isPaymentThroughTheWebsite(false)
                .user(user)
                .address(address)
                .purchasedProducts(purchasedProducts)
                .build();
    }

    static void setPurchaseRestaurantResponseDTOToComparisonInFindDeliveredPurchases() {

        purchaseRestaurantResponseDTOToComparisonInFindDeliveredPurchases = purchases.stream()
                .filter(Purchase::isDelivered)
                .sorted(Comparator.comparing(Purchase::getId).reversed())
                .map(Mapper::fromPurchaseToPurchaseRestaurantResponseDTO)
                .limit(50)
                .toList();
    }

    @BeforeAll
    static void initializeObjects() {

        setUser();
        setAddress();
        setPurchasedProducts();
        setPurchase();
        setPurchases();
        setPurchasesFindByUserAndIsActive();
        setPurchasesToComparisonInFindByAllPurchasesOfTheAnUser();
        setPurchasesFindByIsDelivered();
        setPurchasesToComparisonInFndByAllUsersPurchases();
        setPurchaseDeliverPurchase();
        setPurchaseRestaurantResponseDTOToComparisonInFindDeliveredPurchases();
    }

    @BeforeEach
    void definitionOfBehaviorsForMockPurchaseRepository() {

        BDDMockito.when(this.purchaseRepository.save(ArgumentMatchers.any(Purchase.class)))
                .thenReturn(purchase);

        BDDMockito.when(this.purchaseRepository.findByUserAndIsActive(
                        ArgumentMatchers.any(User.class),
                        ArgumentMatchers.anyBoolean()
                ))
                .thenReturn(PurchasesFindByUserAndIsActive);

        BDDMockito.when(this.purchaseRepository.findByIdAndUser(
                        ArgumentMatchers.any(Long.class),
                        ArgumentMatchers.any(User.class)
                ))
                .thenReturn(Optional.of(purchase));

        BDDMockito.doNothing()
                .when(this.purchaseRepository).updateIsActiveById(
                        ArgumentMatchers.anyBoolean(),
                        ArgumentMatchers.any(Long.class)
                );

        BDDMockito.when(this.purchaseRepository.findByIsDelivered(ArgumentMatchers.anyBoolean()))
                .thenReturn(purchasesFindByIsDelivered);


        BDDMockito.when(this.purchaseRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(purchase));

        BDDMockito.doNothing().when(this.purchaseRepository).updateIsFinishedById(
                ArgumentMatchers.anyBoolean(),
                ArgumentMatchers.any(Long.class)
        );

        BDDMockito.doNothing()
                .when(this.purchaseRepository).updateIsDeliveredById(
                        ArgumentMatchers.anyBoolean(),
                        ArgumentMatchers.any(Long.class)
                );

        BDDMockito.when(this.purchaseRepository.findDistinctByIsDelivered(ArgumentMatchers.anyBoolean()))
                .thenReturn(purchases.stream()
                        .filter(Purchase::isDelivered)
                        .toList());

        BDDMockito.doNothing()
                .when(this.purchaseRepository).updateIsDeliveredById(ArgumentMatchers.anyBoolean(), ArgumentMatchers.any(Long.class));
    }

    @BeforeEach
    void definitionOfBehaviorsForMockPurchasedProductService() {

        BDDMockito.doNothing()
                .when(this.purchasedProductService).delete(ArgumentMatchers.any(Long.class));
    }

    @Test
    void save_persistAndReturnsANewPurchaseInTheDatabase_wheneverCalled() {

        Assertions.assertThatCode(() -> this.purchaseService.save(purchase))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.purchaseService.save(purchase))
                .isNotNull()
                .isEqualTo(purchase);
    }

    @Test
    void findByAllPurchasesOfTheAnUser_returnsAMapOfTheAllActivePurchaseResponseDTOForUserOfTheAUser_wheneverCalled() {

        Assertions.assertThatCode(() -> this.purchaseService.findByAllPurchasesOfTheAnUser(user))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.purchaseService.findByAllPurchasesOfTheAnUser(user))
                .isNotNull()
                .containsEntry(true, purchasesToComparisonInFindByAllPurchasesOfTheAnUser);
    }

    @Test
    void cancelPurchaseOfTheUser_updateTheFieldIsActiveToFalseFromAPurchase_whenThePassedIdIsNotNullAndIsAnIdOfAPurchaseThatIsNotFinishedOrDelivered() {

        Assertions.assertThatCode(() -> this.purchaseService.cancelPurchaseOfTheUser(user, 1L, "pt-BR"))
                .doesNotThrowAnyException();
    }

    @Test
    void cancelPurchaseOfTheUser_throwsResponseStatusException_whenThePassedIdNotExists() {

        BDDMockito.when(this.purchaseRepository.findByIdAndUser(
                        ArgumentMatchers.any(Long.class),
                        ArgumentMatchers.any(User.class)
                ))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseService.cancelPurchaseOfTheUser(user, 0L, "pt-BR"));
    }

    @Test
    void cancelPurchaseOfTheUser_throwsPurchaseFinishedException_whenThePassedIdIsAnIdOfAPurchaseThatIsFinishedOrDelivered() {

        BDDMockito.when(this.purchaseRepository.findByIdAndUser(
                        ArgumentMatchers.any(Long.class),
                        ArgumentMatchers.any(User.class)
                ))
                .thenReturn(Optional.of(Purchase.PurchaseBuilder.builder()
                        .id(1L)
                        .isFinished(true)
                        .isDelivered(true)
                        .build()));

        Assertions.assertThatExceptionOfType(PurchaseFinishedException.class)
                .isThrownBy(() -> this.purchaseService.cancelPurchaseOfTheUser(user, 1L, "pt-BR"));
    }

    @Test
    void cancelPurchaseOfTheUser_throwsResponseStatusException_whenThePassedAcceptLanguageIsInvalid() {

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseService.cancelPurchaseOfTheUser(user, 1L, "a"));
    }

    @Test
    void findByAllUsersPurchases_returnsAllPurchaseRestaurantResponseDTONonDelivered_wheneverCalled() {

        Assertions.assertThatCode(() -> this.purchaseService.findByAllUsersPurchases())
                .doesNotThrowAnyException();

        Assertions.assertThat(this.purchaseService.findByAllUsersPurchases())
                .isNotNull()
                .containsEntry(true, purchasesToComparisonInFndByAllUsersPurchases);
    }

    @Test
    void preparePurchase_updatesTheIsFinishedOfAPurchaseToTrue_whenTheIdIsValid() {

        Assertions.assertThatCode(() -> this.purchaseService.preparePurchase(1L, "pt-BR"))
                .doesNotThrowAnyException();
    }

    @Test
    void preparePurchase_throwsResponseStatusException_whenThePassedIdNotExist() {

        BDDMockito.when(this.purchaseRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseService.preparePurchase(1L, "pt-BR"));
    }

    @Test
    void preparePurchase_throwsResponseStatusException_whenThePassedIdIsInvalid() {

        BDDMockito.when(this.purchaseRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(purchases.get(1)));

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseService.preparePurchase(1L, "pt-BR"));
    }

    @Test
    void preparePurchase_throwsResponseStatusException_whenThePassedAcceptLanguageIsInvalid() {

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseService.preparePurchase(1L, "a"));
    }

    @Test
    void deliverPurchase_updatesTheIsDeliveredOfAPurchaseToTrue_whenThePassedIdIsValid() {

        BDDMockito.when(this.purchaseRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(purchaseDeliverPurchase));

        Assertions.assertThatCode(() -> this.purchaseService.deliverPurchase(1L, "pt-BR"))
                .doesNotThrowAnyException();
    }

    @Test
    void deliverPurchase_throwsResponseStatusException_whenThePassedIdNotExist() {

        BDDMockito.when(this.purchaseRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseService.deliverPurchase(1L, "pt-BR"));
    }

    @Test
    void deliverPurchase_throwsResponseStatusException_whenThePassedIdIsInvalid() {

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseService.deliverPurchase(1L, "pt-BR"));
    }

    @Test
    void deliverPurchase_throwsResponseStatusException_whenThePassedAcceptLanguageIsInvalid() {

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseService.deliverPurchase(1L, "a"));
    }

    @Test
    void deleteAPurchase_deletesAPurchase_whenThePassedIdIsValid() {

        Purchase purchaseToBeDeleted = purchases.get(1);

        BDDMockito.when(this.purchaseRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(purchaseToBeDeleted));

        Assertions.assertThatCode(() -> this.purchaseService.deleteAPurchase(1L))
                .doesNotThrowAnyException();
    }

    @Test
    void deleteAPurchase_throwsResponseStatusException_whenThePassedIdDoesNotExist() {

        BDDMockito.when(this.purchaseRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseService.deleteAPurchase(2L));
    }

    @Test
    void deleteAPurchase_throwsResponseStatusException_whenThePassedIdIsInvalid() {

        Purchase invalidPurchase = Purchase.PurchaseBuilder.builder()
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

        BDDMockito.when(this.purchaseRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(invalidPurchase));

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseService.deleteAPurchase(1L));
    }

    @Test
    void findDeliveredPurchases_returnsAListOfTheLast50PurchaseRestaurantResponseDTODelivered_wheneverCalled() {

        Assertions.assertThatCode(() -> this.purchaseService.findDeliveredPurchases())
                .doesNotThrowAnyException();

        Assertions.assertThat(this.purchaseService.findDeliveredPurchases())
                .isNotNull()
                .asList()
                .isEqualTo(purchaseRestaurantResponseDTOToComparisonInFindDeliveredPurchases)
                .hasSize(purchaseRestaurantResponseDTOToComparisonInFindDeliveredPurchases.size())
                .contains(purchaseRestaurantResponseDTOToComparisonInFindDeliveredPurchases.get(0));
    }

    @Test
    void disableDeliveryById_updatesTheIsDeliveredOfAPurchaseToFalse_whenThePassedIdIsValid() {

        Purchase purchaseFindAllOfDisableDeliveryById = purchases.get(5);

        BDDMockito.when(this.purchaseRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(purchaseFindAllOfDisableDeliveryById));

        Assertions.assertThatCode(() -> this.purchaseService.disableDeliveryById(1L))
                .doesNotThrowAnyException();
    }

    @Test
    void disableDeliveryById_throwsResponseStatusException_whenThePassedIdDoesNotExist() {

        BDDMockito.when(this.purchaseRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseService.disableDeliveryById(2L));
    }
}