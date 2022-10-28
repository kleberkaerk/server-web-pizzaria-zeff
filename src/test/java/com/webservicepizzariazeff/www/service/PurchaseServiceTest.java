package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.Address;
import com.webservicepizzariazeff.www.domain.Purchase;
import com.webservicepizzariazeff.www.domain.PurchasedProduct;
import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.response.AddressResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchaseRestaurantResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchaseUserResponseDTO;
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

    private static Address address;

    private static Purchase purchaseToCompare;

    private static List<PurchasedProduct> purchasedProducts;

    private static List<Purchase> userPurchases;

    private static Map<Boolean, List<PurchaseUserResponseDTO>> mapPurchaseUserResponseDTOToCompare;

    private static List<Purchase> restaurantPurchases;

    private static Map<Boolean, List<PurchaseRestaurantResponseDTO>> mapPurchaseRestaurantResponseDTOToCompare;

    static void setUser() {

        user = User.UserBuilder.builder().id(1L)
                .name("name")
                .username("username")
                .password("password")
                .authorities("ROLE_USER")
                .build();
    }

    static void setAddress() {

        address = Address.AddressBuilder.builder()
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

    static void setPurchaseToCompare() {

        purchaseToCompare = Purchase.PurchaseBuilder.builder()
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

    static void setUserPurchases() {

        userPurchases = List.of(
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
    }

    static void setMapPurchaseUserResponseDTOToCompare() {

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

        mapPurchaseUserResponseDTOToCompare = new HashMap<>(
                Map.of(
                        false, List.of(
                                PurchaseUserResponseDTO.PurchaseUserResponseDTOBuilder.builder()
                                        .id(userPurchases.get(1).getId())
                                        .amount(userPurchases.get(1).getAmount())
                                        .dateAndTime(userPurchases.get(1).getDateAndTime())
                                        .cardName(userPurchases.get(1).getCardName())
                                        .isActive(userPurchases.get(1).isActive())
                                        .isFinished(userPurchases.get(1).isFinished())
                                        .isDelivered(userPurchases.get(1).isDelivered())
                                        .isPaymentThroughTheWebsite(userPurchases.get(1).isPaymentThroughTheWebsite())
                                        .purchasedProductResponseDTOS(purchasedProductResponseDTOS)
                                        .addressResponseDTO(addressResponseDTO)
                                        .build(),
                                PurchaseUserResponseDTO.PurchaseUserResponseDTOBuilder.builder()
                                        .id(userPurchases.get(0).getId())
                                        .amount(userPurchases.get(0).getAmount())
                                        .dateAndTime(userPurchases.get(0).getDateAndTime())
                                        .cardName(userPurchases.get(0).getCardName())
                                        .isActive(userPurchases.get(0).isActive())
                                        .isFinished(userPurchases.get(0).isFinished())
                                        .isDelivered(userPurchases.get(0).isDelivered())
                                        .isPaymentThroughTheWebsite(userPurchases.get(0).isPaymentThroughTheWebsite())
                                        .purchasedProductResponseDTOS(purchasedProductResponseDTOS)
                                        .addressResponseDTO(addressResponseDTO)
                                        .build()
                        ),
                        true, List.of(
                                PurchaseUserResponseDTO.PurchaseUserResponseDTOBuilder.builder()
                                        .id(userPurchases.get(2).getId())
                                        .amount(userPurchases.get(2).getAmount())
                                        .dateAndTime(userPurchases.get(2).getDateAndTime())
                                        .cardName(userPurchases.get(2).getCardName())
                                        .isActive(userPurchases.get(2).isActive())
                                        .isFinished(userPurchases.get(2).isFinished())
                                        .isDelivered(userPurchases.get(2).isDelivered())
                                        .isPaymentThroughTheWebsite(userPurchases.get(2).isPaymentThroughTheWebsite())
                                        .purchasedProductResponseDTOS(purchasedProductResponseDTOS)
                                        .addressResponseDTO(addressResponseDTO)
                                        .build()
                        )
                )
        );
    }

    static void setRestaurantPurchases() {

        restaurantPurchases = List.of(
                Purchase.PurchaseBuilder.builder()
                        .id(1L)
                        .amount(new BigDecimal("10.00"))
                        .dateAndTime("12/34/5678T90:12")
                        .cardName("cardName1")
                        .isActive(true)
                        .isFinished(false)
                        .isDelivered(false)
                        .isPaymentThroughTheWebsite(false)
                        .purchasedProducts(purchasedProducts)
                        .user(user)
                        .address(address)
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
                        .purchasedProducts(purchasedProducts)
                        .user(user)
                        .address(address)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(3L)
                        .amount(new BigDecimal("30.00"))
                        .dateAndTime("12/34/5678T90:12")
                        .cardName("cardName3")
                        .isActive(false)
                        .isFinished(false)
                        .isDelivered(false)
                        .isPaymentThroughTheWebsite(true)
                        .purchasedProducts(purchasedProducts)
                        .user(user)
                        .address(address)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(4L)
                        .amount(new BigDecimal("40.00"))
                        .dateAndTime("12/34/5678T90:12")
                        .cardName("cardName4")
                        .isActive(true)
                        .isFinished(true)
                        .isDelivered(false)
                        .isPaymentThroughTheWebsite(true)
                        .purchasedProducts(purchasedProducts)
                        .user(user)
                        .address(address)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(5L)
                        .amount(new BigDecimal("50.00"))
                        .dateAndTime("12/34/5678T90:12")
                        .cardName("cardName5")
                        .isActive(true)
                        .isFinished(true)
                        .isDelivered(false)
                        .isPaymentThroughTheWebsite(false)
                        .purchasedProducts(purchasedProducts)
                        .user(user)
                        .address(address)
                        .build()
        );
    }

    static void setMapPurchaseRestaurantResponseDTOToCompare() {

        List<PurchasedProductResponseDTO> purchasedProductResponseDTOS = List.of(
                PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                        .name(purchasedProducts.get(0).getName())
                        .build(),
                PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                        .name(purchasedProducts.get(1).getName())
                        .build(),
                PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder.builder()
                        .name(purchasedProducts.get(2).getName())
                        .build()
        );

        AddressResponseDTO addressResponseDTO = AddressResponseDTO.AddressResponseDTOBuilder.builder()
                .id(address.getId())
                .number(address.getNumber())
                .road(address.getRoad())
                .district(address.getDistrict())
                .city(address.getCity())
                .state(address.getState())
                .build();

        mapPurchaseRestaurantResponseDTOToCompare = new HashMap<>(Map.of(
                true, List.of(
                        PurchaseRestaurantResponseDTO.PurchaseRestaurantResponseDTOBuilder.builder()
                                .id(restaurantPurchases.get(0).getId())
                                .clientName(restaurantPurchases.get(0).getUser().getName())
                                .isActive(restaurantPurchases.get(0).isActive())
                                .isFinished(restaurantPurchases.get(0).isFinished())
                                .isDelivered(restaurantPurchases.get(0).isDelivered())
                                .isPaymentThroughTheWebsite(restaurantPurchases.get(0).isPaymentThroughTheWebsite())
                                .purchasedProductResponseDTOS(purchasedProductResponseDTOS)
                                .addressResponseDTO(addressResponseDTO)
                                .build(),
                        PurchaseRestaurantResponseDTO.PurchaseRestaurantResponseDTOBuilder.builder()
                                .id(restaurantPurchases.get(3).getId())
                                .clientName(restaurantPurchases.get(3).getUser().getName())
                                .isActive(restaurantPurchases.get(3).isActive())
                                .isFinished(restaurantPurchases.get(3).isFinished())
                                .isDelivered(restaurantPurchases.get(3).isDelivered())
                                .isPaymentThroughTheWebsite(restaurantPurchases.get(3).isPaymentThroughTheWebsite())
                                .purchasedProductResponseDTOS(purchasedProductResponseDTOS)
                                .addressResponseDTO(addressResponseDTO)
                                .build(),
                        PurchaseRestaurantResponseDTO.PurchaseRestaurantResponseDTOBuilder.builder()
                                .id(restaurantPurchases.get(4).getId())
                                .clientName(restaurantPurchases.get(4).getUser().getName())
                                .isActive(restaurantPurchases.get(4).isActive())
                                .isFinished(restaurantPurchases.get(4).isFinished())
                                .isDelivered(restaurantPurchases.get(4).isDelivered())
                                .isPaymentThroughTheWebsite(restaurantPurchases.get(4).isPaymentThroughTheWebsite())
                                .purchasedProductResponseDTOS(purchasedProductResponseDTOS)
                                .addressResponseDTO(addressResponseDTO)
                                .build()
                ),
                false, List.of(
                        PurchaseRestaurantResponseDTO.PurchaseRestaurantResponseDTOBuilder.builder()

                                .id(restaurantPurchases.get(1).getId())
                                .clientName(restaurantPurchases.get(1).getUser().getName())
                                .isActive(restaurantPurchases.get(1).isActive())
                                .isFinished(restaurantPurchases.get(1).isFinished())
                                .isDelivered(restaurantPurchases.get(1).isDelivered())
                                .isPaymentThroughTheWebsite(restaurantPurchases.get(1).isPaymentThroughTheWebsite())
                                .purchasedProductResponseDTOS(purchasedProductResponseDTOS)
                                .addressResponseDTO(addressResponseDTO)
                                .build(),
                        PurchaseRestaurantResponseDTO.PurchaseRestaurantResponseDTOBuilder.builder()

                                .id(restaurantPurchases.get(2).getId())
                                .clientName(restaurantPurchases.get(2).getUser().getName())
                                .isActive(restaurantPurchases.get(2).isActive())
                                .isFinished(restaurantPurchases.get(2).isFinished())
                                .isDelivered(restaurantPurchases.get(2).isDelivered())
                                .isPaymentThroughTheWebsite(restaurantPurchases.get(2).isPaymentThroughTheWebsite())
                                .purchasedProductResponseDTOS(purchasedProductResponseDTOS)
                                .addressResponseDTO(addressResponseDTO)
                                .build()
                )
        ));
    }

    @BeforeAll
    static void initializeObjects() {

        setUser();
        setAddress();
        setPurchasedProducts();
        setPurchaseToCompare();
        setUserPurchases();
        setMapPurchaseUserResponseDTOToCompare();
        setRestaurantPurchases();
        setMapPurchaseRestaurantResponseDTOToCompare();
    }

    @BeforeEach
    void definitionOfBehaviorsForMocks() {

        BDDMockito.when(this.purchaseRepository.save(ArgumentMatchers.any(Purchase.class)))
                .thenReturn(userPurchases.get(0));

        BDDMockito.when(this.purchaseRepository.findByUserAndIsActive(
                        ArgumentMatchers.any(User.class),
                        ArgumentMatchers.anyBoolean()
                ))
                .thenReturn(userPurchases);

        BDDMockito.when(this.purchaseRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(userPurchases.get(0)));

        BDDMockito.doNothing()
                .when(this.purchaseRepository).updateIsActiveById(
                        ArgumentMatchers.anyBoolean(),
                        ArgumentMatchers.any(Long.class)
                );

        BDDMockito.when(this.purchaseRepository.findByIsDelivered(ArgumentMatchers.anyBoolean()))
                .thenReturn(restaurantPurchases);

        BDDMockito.doNothing().when(this.purchaseRepository).updateIsFinishedById(
                ArgumentMatchers.anyBoolean(),
                ArgumentMatchers.any(Long.class)
        );

        BDDMockito.doNothing()
                .when(this.purchaseRepository).updateIsDeliveredById(
                        ArgumentMatchers.anyBoolean(),
                        ArgumentMatchers.any(Long.class)
                );
    }

    @Test
    void save_persistAndReturnsANewPurchaseInTheDatabase_wheneverCalled() {

        Assertions.assertThat(this.purchaseService.save(purchaseToCompare))
                .isNotNull()
                .hasToString(purchaseToCompare.toString());
    }

    @Test
    void findByAllPurchasesOfTheAnUser_returnsAMapOfTheAllActivePurchaseResponseDTOForUserOfTheAUser_wheneverCalled() {

        Assertions.assertThat(this.purchaseService.findByAllPurchasesOfTheAnUser(user))
                .isNotNull()
                .hasToString(mapPurchaseUserResponseDTOToCompare.toString());
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

    @Test
    void findByAllUsersPurchases_returnsAllPurchaseUserResponseDTOOfTheAUser_wheneverCalled() {

        Assertions.assertThat(this.purchaseService.findByAllUsersPurchases())
                .isNotNull()
                .hasToString(mapPurchaseRestaurantResponseDTOToCompare.toString());
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
                .thenReturn(Optional.of(userPurchases.get(2)));

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.purchaseService.preparePurchase(1L, "pt-BR"));
    }

    @Test
    void deliverPurchase_updatesTheIsDeliveredOfAPurchaseToTrue_whenTheIdIsValid() {

        BDDMockito.when(this.purchaseRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(Purchase.PurchaseBuilder.builder()
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
                        .build()));

        Assertions.assertThatCode(()-> this.purchaseService.deliverPurchase(1L, "pt-BR"))
                .doesNotThrowAnyException();
    }

    @Test
    void deliverPurchase_throwsResponseStatusException_whenThePassedIdNotExist(){

        BDDMockito.when(this.purchaseRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(()-> this.purchaseService.deliverPurchase(1L, "pt-BR"));
    }

    @Test
    void deliverPurchase_throwsResponseStatusException_whenThePassedIdIsInvalid(){

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(()-> this.purchaseService.deliverPurchase(1L, "pt-BR"));
    }
}