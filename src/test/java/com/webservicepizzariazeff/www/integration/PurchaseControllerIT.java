package com.webservicepizzariazeff.www.integration;

import com.webservicepizzariazeff.www.domain.Address;
import com.webservicepizzariazeff.www.domain.Purchase;
import com.webservicepizzariazeff.www.domain.PurchasedProduct;
import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.response.PurchaseRestaurantResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchaseUserResponseDTO;
import com.webservicepizzariazeff.www.exception_handler.PurchaseFinishedExceptionHandler;
import com.webservicepizzariazeff.www.integration.mapper.MapperToIntegration;
import com.webservicepizzariazeff.www.repository.AddressRepository;
import com.webservicepizzariazeff.www.repository.PurchaseRepository;
import com.webservicepizzariazeff.www.repository.PurchasedProductRepository;
import com.webservicepizzariazeff.www.repository.UserRepository;
import com.webservicepizzariazeff.www.util.Mapper;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.jpa.hibernate.ddl-auto=update", "spring.flyway.enabled=false"}
)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PurchaseControllerIT {

    @Autowired
    @Qualifier("testRestTemplateUserToPurchaseControllerIT")
    private TestRestTemplate testRestTemplateUser;

    @Autowired
    @Qualifier("testRestTemplateAdminToPurchaseControllerIT")
    private TestRestTemplate testRestTemplateUserAdmin;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PurchasedProductRepository purchasedProductRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    private HttpHeaders httpHeaders;

    private static final String[] acceptLanguage = {"Accept-Language", "pt-BR"};

    private static User user;

    private static Address address;

    private static List<Purchase> purchasesFindPurchasesOfTheUser;

    private static List<PurchasedProduct> purchasedProductsFindPurchasesOfTheUser;

    private static List<PurchaseUserResponseDTO> purchaseUserResponseDTOSToComparisonInFindPurchasesOfTheUser;

    private static Purchase purchaseCancelPurchase;

    private static PurchaseFinishedExceptionHandler purchaseFinishedExceptionHandlerToComparisonInCancelPurchase;

    private static User userAdmin;

    private static List<Purchase> purchasesFindUsersPurchases;

    private static List<PurchasedProduct> purchasedProductsFindUsersPurchases;

    private static List<PurchaseRestaurantResponseDTO> purchaseRestaurantResponseDTOSToComparisonInFindUsersPurchases;

    private static Purchase purchasePurchasePreparation;

    private static Purchase purchasePurchaseDelivery;

    private static Purchase purchaseDeletePurchase;

    private static List<Purchase> purchasesDeliveredPurchases;

    private static List<PurchasedProduct> purchasedProductsDeliveredPurchases;

    private static List<PurchaseRestaurantResponseDTO> purchaseRestaurantResponseDTOSToComparisonInDeliveredPurchases;

    private static Purchase purchaseDisableDelivery;

    static void setUser() {

        user = User.UserBuilder.builder()
                .id(1L)
                .name("name")
                .username("name@gmail.com")
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("password"))
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

    static void setPurchasesFindPurchasesOfTheUser() {

        purchasesFindPurchasesOfTheUser = List.of(
                Purchase.PurchaseBuilder.builder()
                        .id(1L)
                        .amount(new BigDecimal("30.00"))
                        .cardName("cardName1")
                        .isPaymentThroughTheWebsite(true)
                        .user(user)
                        .address(address)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(2L)
                        .amount(new BigDecimal("40.00"))
                        .cardName("cardName2")
                        .isPaymentThroughTheWebsite(true)
                        .user(user)
                        .address(address)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(3L)
                        .amount(new BigDecimal("50.00"))
                        .cardName("cardName3")
                        .isPaymentThroughTheWebsite(true)
                        .user(user)
                        .address(address)
                        .build()
        );
    }

    static void setPurchasedProductsFindPurchasesOfTheUser() {

        purchasedProductsFindPurchasesOfTheUser = List.of(
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(1L)
                        .name("name1")
                        .purchase(purchasesFindPurchasesOfTheUser.get(0))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(2L)
                        .name("name2")
                        .purchase(purchasesFindPurchasesOfTheUser.get(0))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(3L)
                        .name("name3")
                        .purchase(purchasesFindPurchasesOfTheUser.get(0))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(4L)
                        .name("name4")
                        .purchase(purchasesFindPurchasesOfTheUser.get(1))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(5L)
                        .name("name5")
                        .purchase(purchasesFindPurchasesOfTheUser.get(1))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(6L)
                        .name("name6")
                        .purchase(purchasesFindPurchasesOfTheUser.get(1))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(7L)
                        .name("name7")
                        .purchase(purchasesFindPurchasesOfTheUser.get(2))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(8L)
                        .name("name8")
                        .purchase(purchasesFindPurchasesOfTheUser.get(2))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(9L)
                        .name("name9")
                        .purchase(purchasesFindPurchasesOfTheUser.get(2))
                        .build()
        );
    }

    static void setPurchaseUserResponseDTOSToComparisonInFindPurchasesOfTheUser() {

        List<Purchase> purchases = List.of(
                Purchase.PurchaseBuilder.builder()
                        .id(purchasesFindPurchasesOfTheUser.get(0).getId())
                        .amount(purchasesFindPurchasesOfTheUser.get(0).getAmount())
                        .cardName(purchasesFindPurchasesOfTheUser.get(0).getCardName())
                        .isPaymentThroughTheWebsite(purchasesFindPurchasesOfTheUser.get(0).isPaymentThroughTheWebsite())
                        .user(purchasesFindPurchasesOfTheUser.get(0).getUser())
                        .address(purchasesFindPurchasesOfTheUser.get(0).getAddress())
                        .dateAndTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm")))
                        .purchasedProducts(List.of(
                                purchasedProductsFindPurchasesOfTheUser.get(0),
                                purchasedProductsFindPurchasesOfTheUser.get(1),
                                purchasedProductsFindPurchasesOfTheUser.get(2)
                        ))
                        .isActive(true)
                        .isFinished(false)
                        .isDelivered(false)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(purchasesFindPurchasesOfTheUser.get(1).getId())
                        .amount(purchasesFindPurchasesOfTheUser.get(1).getAmount())
                        .cardName(purchasesFindPurchasesOfTheUser.get(1).getCardName())
                        .isPaymentThroughTheWebsite(purchasesFindPurchasesOfTheUser.get(1).isPaymentThroughTheWebsite())
                        .user(purchasesFindPurchasesOfTheUser.get(1).getUser())
                        .address(purchasesFindPurchasesOfTheUser.get(1).getAddress())
                        .dateAndTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm")))
                        .purchasedProducts(List.of(
                                purchasedProductsFindPurchasesOfTheUser.get(3),
                                purchasedProductsFindPurchasesOfTheUser.get(4),
                                purchasedProductsFindPurchasesOfTheUser.get(5)
                        ))
                        .isActive(true)
                        .isFinished(false)
                        .isDelivered(false)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(purchasesFindPurchasesOfTheUser.get(2).getId())
                        .amount(purchasesFindPurchasesOfTheUser.get(2).getAmount())
                        .cardName(purchasesFindPurchasesOfTheUser.get(2).getCardName())
                        .isPaymentThroughTheWebsite(purchasesFindPurchasesOfTheUser.get(2).isPaymentThroughTheWebsite())
                        .user(purchasesFindPurchasesOfTheUser.get(2).getUser())
                        .address(purchasesFindPurchasesOfTheUser.get(2).getAddress())
                        .dateAndTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm")))
                        .purchasedProducts(List.of(
                                purchasedProductsFindPurchasesOfTheUser.get(6),
                                purchasedProductsFindPurchasesOfTheUser.get(7),
                                purchasedProductsFindPurchasesOfTheUser.get(8)
                        ))
                        .isActive(true)
                        .isFinished(false)
                        .isDelivered(false)
                        .build()
        );

        purchaseUserResponseDTOSToComparisonInFindPurchasesOfTheUser = purchases.stream()
                .map(Mapper::fromPurchaseToPurchaseUserResponseDTO)
                .sorted(Comparator.comparing(PurchaseUserResponseDTO::getId).reversed())
                .filter(purchaseUserResponseDTO -> !purchaseUserResponseDTO.isDelivered())
                .toList();
    }

    static void setPurchaseCancelPurchase() {

        purchaseCancelPurchase = Purchase.PurchaseBuilder.builder()
                .id(1L)
                .amount(new BigDecimal("10.00"))
                .cardName("cardName1")
                .isPaymentThroughTheWebsite(true)
                .user(user)
                .address(address)
                .build();
    }

    static void setPurchaseFinishedExceptionHandlerToComparisonInCancelPurchase() {

        purchaseFinishedExceptionHandlerToComparisonInCancelPurchase = PurchaseFinishedExceptionHandler.
                PurchaseFinishedExceptionHandlerBuilder.builder()
                .message("Seu pedido já está pronto, portanto ele não poderá ser cancelado. Em poucos minutos ele será entregue na sua casa.")
                .build();
    }

    static void setUserAdmin() {

        userAdmin = User.UserBuilder.builder()
                .id(1L)
                .name("name-admin")
                .username("name-admin@gmail.com")
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("password-admin"))
                .authorities("ROLE_USER,ROLE_ADMIN")
                .build();
    }

    static void setPurchasesFindUsersPurchases() {

        purchasesFindUsersPurchases = List.of(
                Purchase.PurchaseBuilder.builder()
                        .id(1L)
                        .amount(new BigDecimal("10.00"))
                        .cardName("cardName1")
                        .isPaymentThroughTheWebsite(true)
                        .user(userAdmin)
                        .address(address)
                        .isActive(true)
                        .isFinished(false)
                        .isDelivered(false)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(2L)
                        .amount(new BigDecimal("20.00"))
                        .cardName("cardName2")
                        .isPaymentThroughTheWebsite(true)
                        .user(userAdmin)
                        .address(address)
                        .isActive(true)
                        .isFinished(false)
                        .isDelivered(false)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(3L)
                        .amount(new BigDecimal("30.00"))
                        .cardName("cardName3")
                        .isPaymentThroughTheWebsite(true)
                        .user(userAdmin)
                        .address(address)
                        .isActive(true)
                        .isFinished(false)
                        .isDelivered(false)
                        .build()
        );
    }

    static void setPurchasedProductsFindUsersPurchases() {

        purchasedProductsFindUsersPurchases = List.of(
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(1L)
                        .name("name1")
                        .purchase(purchasesFindUsersPurchases.get(0))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(2L)
                        .name("name2")
                        .purchase(purchasesFindUsersPurchases.get(0))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(3L)
                        .name("name3")
                        .purchase(purchasesFindUsersPurchases.get(0))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(4L)
                        .name("name4")
                        .purchase(purchasesFindUsersPurchases.get(1))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(5L)
                        .name("name5")
                        .purchase(purchasesFindUsersPurchases.get(1))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(6L)
                        .name("name6")
                        .purchase(purchasesFindUsersPurchases.get(1))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(7L)
                        .name("name7")
                        .purchase(purchasesFindUsersPurchases.get(2))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(8L)
                        .name("name8")
                        .purchase(purchasesFindUsersPurchases.get(2))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(9L)
                        .name("name9")
                        .purchase(purchasesFindUsersPurchases.get(2))
                        .build()
        );
    }

    static void setPurchaseRestaurantResponseDTOSToComparisonInFindUsersPurchases() {

        List<Purchase> purchases = List.of(
                Purchase.PurchaseBuilder.builder()
                        .id(purchasesFindUsersPurchases.get(0).getId())
                        .amount(purchasesFindUsersPurchases.get(0).getAmount())
                        .cardName(purchasesFindUsersPurchases.get(0).getCardName())
                        .isPaymentThroughTheWebsite(purchasesFindUsersPurchases.get(0).isPaymentThroughTheWebsite())
                        .user(purchasesFindUsersPurchases.get(0).getUser())
                        .address(purchasesFindUsersPurchases.get(0).getAddress())
                        .purchasedProducts(List.of(
                                purchasedProductsFindUsersPurchases.get(0),
                                purchasedProductsFindUsersPurchases.get(1),
                                purchasedProductsFindUsersPurchases.get(2)
                        ))
                        .isActive(purchasesFindUsersPurchases.get(0).isActive())
                        .isFinished(purchasesFindUsersPurchases.get(0).isFinished())
                        .isDelivered(purchasesFindUsersPurchases.get(0).isDelivered())
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(purchasesFindUsersPurchases.get(1).getId())
                        .amount(purchasesFindUsersPurchases.get(1).getAmount())
                        .cardName(purchasesFindUsersPurchases.get(1).getCardName())
                        .isPaymentThroughTheWebsite(purchasesFindUsersPurchases.get(1).isPaymentThroughTheWebsite())
                        .user(purchasesFindUsersPurchases.get(1).getUser())
                        .address(purchasesFindUsersPurchases.get(1).getAddress())
                        .purchasedProducts(List.of(
                                purchasedProductsFindUsersPurchases.get(3),
                                purchasedProductsFindUsersPurchases.get(4),
                                purchasedProductsFindUsersPurchases.get(5)
                        ))
                        .isActive(purchasesFindUsersPurchases.get(1).isActive())
                        .isFinished(purchasesFindUsersPurchases.get(1).isFinished())
                        .isDelivered(purchasesFindUsersPurchases.get(1).isDelivered())
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(purchasesFindUsersPurchases.get(2).getId())
                        .amount(purchasesFindUsersPurchases.get(2).getAmount())
                        .cardName(purchasesFindUsersPurchases.get(2).getCardName())
                        .isPaymentThroughTheWebsite(purchasesFindUsersPurchases.get(2).isPaymentThroughTheWebsite())
                        .user(purchasesFindUsersPurchases.get(2).getUser())
                        .address(purchasesFindUsersPurchases.get(2).getAddress())
                        .purchasedProducts(List.of(
                                purchasedProductsFindUsersPurchases.get(6),
                                purchasedProductsFindUsersPurchases.get(7),
                                purchasedProductsFindUsersPurchases.get(8)
                        ))
                        .isActive(purchasesFindUsersPurchases.get(2).isActive())
                        .isFinished(purchasesFindUsersPurchases.get(2).isFinished())
                        .isDelivered(purchasesFindUsersPurchases.get(2).isDelivered())
                        .build()
        );

        purchaseRestaurantResponseDTOSToComparisonInFindUsersPurchases = purchases.stream()
                .map(Mapper::fromPurchaseToPurchaseRestaurantResponseDTO)
                .sorted(Comparator.comparing(PurchaseRestaurantResponseDTO::getId))
                .filter(PurchaseRestaurantResponseDTO::isActive)
                .toList();
    }

    static void setPurchasePurchasePreparation() {

        purchasePurchasePreparation = Purchase.PurchaseBuilder.builder()
                .id(1L)
                .amount(new BigDecimal("10.00"))
                .cardName("cardName1")
                .isPaymentThroughTheWebsite(true)
                .user(userAdmin)
                .address(address)
                .isActive(true)
                .isFinished(false)
                .isDelivered(false)
                .build();
    }

    static void setPurchasePurchaseDelivery() {

        purchasePurchaseDelivery = Purchase.PurchaseBuilder.builder()
                .id(1L)
                .amount(new BigDecimal("10.00"))
                .cardName("cardName1")
                .isPaymentThroughTheWebsite(false)
                .user(userAdmin)
                .address(address)
                .isActive(true)
                .isFinished(false)
                .isDelivered(false)
                .build();
    }

    static void setPurchaseDeletePurchase() {

        purchaseDeletePurchase = Purchase.PurchaseBuilder.builder()
                .id(1L)
                .amount(new BigDecimal("10.00"))
                .cardName("cardName1")
                .isPaymentThroughTheWebsite(false)
                .user(userAdmin)
                .address(address)
                .isActive(true)
                .isFinished(false)
                .isDelivered(false)
                .build();
    }

    static void setPurchasesDeliveredPurchases() {

        purchasesDeliveredPurchases = List.of(
                Purchase.PurchaseBuilder.builder()
                        .id(1L)
                        .amount(new BigDecimal("10.00"))
                        .cardName("cardName1")
                        .isPaymentThroughTheWebsite(false)
                        .user(userAdmin)
                        .address(address)
                        .isActive(true)
                        .isFinished(false)
                        .isDelivered(false)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(2L)
                        .amount(new BigDecimal("20.00"))
                        .cardName("cardName2")
                        .isPaymentThroughTheWebsite(true)
                        .user(userAdmin)
                        .address(address)
                        .isActive(true)
                        .isFinished(false)
                        .isDelivered(false)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(3L)
                        .amount(new BigDecimal("30.00"))
                        .cardName("cardName3")
                        .isPaymentThroughTheWebsite(true)
                        .user(userAdmin)
                        .address(address)
                        .isActive(true)
                        .isFinished(false)
                        .isDelivered(false)
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(4L)
                        .amount(new BigDecimal("40.00"))
                        .cardName("cardName4")
                        .isPaymentThroughTheWebsite(false)
                        .user(userAdmin)
                        .address(address)
                        .isActive(true)
                        .isFinished(false)
                        .isDelivered(false)
                        .build()
        );
    }

    static void setPurchasedProductsDeliveredPurchases() {

        purchasedProductsDeliveredPurchases = List.of(
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(1L)
                        .name("name1")
                        .purchase(purchasesDeliveredPurchases.get(0))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(2L)
                        .name("name2")
                        .purchase(purchasesDeliveredPurchases.get(0))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(3L)
                        .name("name3")
                        .purchase(purchasesDeliveredPurchases.get(1))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(4L)
                        .name("name4")
                        .purchase(purchasesDeliveredPurchases.get(1))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(5L)
                        .name("name5")
                        .purchase(purchasesDeliveredPurchases.get(2))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(6L)
                        .name("name6")
                        .purchase(purchasesDeliveredPurchases.get(2))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(7L)
                        .name("name7")
                        .purchase(purchasesDeliveredPurchases.get(3))
                        .build(),
                PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(8L)
                        .name("name8")
                        .purchase(purchasesDeliveredPurchases.get(3))
                        .build()
        );
    }

    static void setPurchaseRestaurantResponseDTOSToComparisonInDeliveredPurchases() {

        List<Purchase> purchases = List.of(
                Purchase.PurchaseBuilder.builder()
                        .id(purchasesDeliveredPurchases.get(0).getId())
                        .amount(purchasesDeliveredPurchases.get(0).getAmount())
                        .cardName(purchasesDeliveredPurchases.get(0).getCardName())
                        .isPaymentThroughTheWebsite(purchasesDeliveredPurchases.get(0).isPaymentThroughTheWebsite())
                        .user(purchasesDeliveredPurchases.get(0).getUser())
                        .address(purchasesDeliveredPurchases.get(0).getAddress())
                        .isActive(purchasesDeliveredPurchases.get(0).isActive())
                        .isFinished(true)
                        .isDelivered(true)
                        .purchasedProducts(List.of(
                                purchasedProductsDeliveredPurchases.get(0),
                                purchasedProductsDeliveredPurchases.get(1)
                        ))
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(purchasesDeliveredPurchases.get(1).getId())
                        .amount(purchasesDeliveredPurchases.get(1).getAmount())
                        .cardName(purchasesDeliveredPurchases.get(1).getCardName())
                        .isPaymentThroughTheWebsite(purchasesDeliveredPurchases.get(1).isPaymentThroughTheWebsite())
                        .user(purchasesDeliveredPurchases.get(1).getUser())
                        .address(purchasesDeliveredPurchases.get(1).getAddress())
                        .isActive(purchasesDeliveredPurchases.get(1).isActive())
                        .isFinished(true)
                        .isDelivered(true)
                        .purchasedProducts(List.of(
                                purchasedProductsDeliveredPurchases.get(2),
                                purchasedProductsDeliveredPurchases.get(3)
                        ))
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(purchasesDeliveredPurchases.get(2).getId())
                        .amount(purchasesDeliveredPurchases.get(2).getAmount())
                        .cardName(purchasesDeliveredPurchases.get(2).getCardName())
                        .isPaymentThroughTheWebsite(purchasesDeliveredPurchases.get(2).isPaymentThroughTheWebsite())
                        .user(purchasesDeliveredPurchases.get(2).getUser())
                        .address(purchasesDeliveredPurchases.get(2).getAddress())
                        .isActive(purchasesDeliveredPurchases.get(2).isActive())
                        .isFinished(true)
                        .isDelivered(true)
                        .purchasedProducts(List.of(
                                purchasedProductsDeliveredPurchases.get(4),
                                purchasedProductsDeliveredPurchases.get(5)
                        ))
                        .build(),
                Purchase.PurchaseBuilder.builder()
                        .id(purchasesDeliveredPurchases.get(3).getId())
                        .amount(purchasesDeliveredPurchases.get(3).getAmount())
                        .cardName(purchasesDeliveredPurchases.get(3).getCardName())
                        .isPaymentThroughTheWebsite(purchasesDeliveredPurchases.get(3).isPaymentThroughTheWebsite())
                        .user(purchasesDeliveredPurchases.get(3).getUser())
                        .address(purchasesDeliveredPurchases.get(3).getAddress())
                        .isActive(purchasesDeliveredPurchases.get(3).isActive())
                        .isFinished(true)
                        .isDelivered(true)
                        .purchasedProducts(List.of(
                                purchasedProductsDeliveredPurchases.get(6),
                                purchasedProductsDeliveredPurchases.get(7)
                        ))
                        .build()
        );

        purchaseRestaurantResponseDTOSToComparisonInDeliveredPurchases = purchases.stream()
                .sorted(Comparator.comparing(Purchase::getId).reversed())
                .map(Mapper::fromPurchaseToPurchaseRestaurantResponseDTO)
                .toList();
    }

    static void setPurchaseDisableDelivery() {

        purchaseDisableDelivery = Purchase.PurchaseBuilder.builder()
                .id(1L)
                .amount(new BigDecimal("10.00"))
                .cardName("cardName1")
                .isPaymentThroughTheWebsite(true)
                .user(userAdmin)
                .address(address)
                .isActive(true)
                .isFinished(false)
                .isDelivered(false)
                .build();
    }

    @BeforeAll
    static void initializeObjects() {

        setUser();
        setAddress();
        setPurchasesFindPurchasesOfTheUser();
        setPurchasedProductsFindPurchasesOfTheUser();
        setPurchaseUserResponseDTOSToComparisonInFindPurchasesOfTheUser();
        setPurchaseCancelPurchase();
        setPurchaseFinishedExceptionHandlerToComparisonInCancelPurchase();
        setUserAdmin();
        setPurchasesFindUsersPurchases();
        setPurchasedProductsFindUsersPurchases();
        setPurchaseRestaurantResponseDTOSToComparisonInFindUsersPurchases();
        setPurchasePurchasePreparation();
        setPurchasePurchaseDelivery();
        setPurchaseDeletePurchase();
        setPurchasesDeliveredPurchases();
        setPurchasedProductsDeliveredPurchases();
        setPurchaseRestaurantResponseDTOSToComparisonInDeliveredPurchases();
        setPurchaseDisableDelivery();
    }

    @Test
    void findPurchasesOfTheUser_returnsAllPurchasesOfAUserGroupedByInDeliveredAndAStatusCode200_wheneverCalled() {

        this.userRepository.save(user);
        this.addressRepository.save(address);
        purchasesFindPurchasesOfTheUser.forEach(this.purchaseRepository::save);
        purchasedProductsFindPurchasesOfTheUser.forEach(this.purchasedProductRepository::save);

        ResponseEntity<Map<Boolean, List<PurchaseUserResponseDTO>>> allPurchasesOfTheAnUser = this.testRestTemplateUser.exchange(
                "/purchases/user",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(allPurchasesOfTheAnUser.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.OK);

        Assertions.assertThat(allPurchasesOfTheAnUser.getBody())
                .isNotNull()
                .containsEntry(false, purchaseUserResponseDTOSToComparisonInFindPurchasesOfTheUser);
    }

    private HttpHeaders getHeaders() {

        ResponseEntity<Void> responseEntityWithCsrfToken = this.testRestTemplateUser.exchange(
                "/products/find-promotions",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        String csrfToken = MapperToIntegration.fromHeaderToCsrfToken(
                Objects.requireNonNull(
                        responseEntityWithCsrfToken.getHeaders().getFirst("Set-Cookie")
                )
        );

        HttpHeaders headers = new HttpHeaders();

        headers.add(acceptLanguage[0], acceptLanguage[1]);
        headers.add("Cookie", "XSRF-TOKEN=" + csrfToken);
        headers.add("X-XSRF-TOKEN", csrfToken);

        return headers;
    }

    @Test
    void cancelPurchase_updatesTheIsActiveOfAPurchaseToFalseAndReturnsAStatusCode204_whenThePassedIdIsValid() {

        this.userRepository.save(user);
        this.addressRepository.save(address);
        this.purchaseRepository.save(purchaseCancelPurchase);

        this.httpHeaders = this.getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateUser.exchange(
                "/purchases/1",
                HttpMethod.PUT,
                new HttpEntity<>(this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void cancelPurchase_returnsAStatusCode400_whenThePassedIdNotExist() {

        this.userRepository.save(user);

        this.httpHeaders = this.getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateUser.exchange(
                "/purchases/2",
                HttpMethod.PUT,
                new HttpEntity<>(this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void cancelPurchase_returnsAHandlerObjectAndAStatusCode409_whenThePurchaseWasFinishedOrDelivered() {

        this.userRepository.save(user);
        this.addressRepository.save(address);
        this.purchaseRepository.save(purchaseCancelPurchase);
        this.purchaseRepository.updateIsFinishedById(true, purchaseCancelPurchase.getId());

        this.httpHeaders = this.getHeaders();

        ResponseEntity<PurchaseFinishedExceptionHandler> responseEntity = this.testRestTemplateUser.exchange(
                "/purchases/1",
                HttpMethod.PUT,
                new HttpEntity<>(this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.CONFLICT);

        Assertions.assertThat(responseEntity.getBody())
                .isNotNull()
                .isEqualTo(purchaseFinishedExceptionHandlerToComparisonInCancelPurchase);
    }

    @Test
    void cancelPurchase_returnsAStatusCode400_whenThePassedAcceptLanguageIsInvalid() {

        ResponseEntity<Void> responseEntityWithCsrfToken = this.testRestTemplateUser.exchange(
                "/products/find-promotions",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        String csrfToken = MapperToIntegration.fromHeaderToCsrfToken(
                Objects.requireNonNull(
                        responseEntityWithCsrfToken.getHeaders().getFirst("Set-Cookie")
                )
        );

        this.httpHeaders = new HttpHeaders();

        this.httpHeaders.add(acceptLanguage[0], "a");
        this.httpHeaders.add("Cookie", "XSRF-TOKEN=" + csrfToken);
        this.httpHeaders.add("X-XSRF-TOKEN", csrfToken);

        this.userRepository.save(user);
        this.addressRepository.save(address);
        this.purchaseRepository.save(purchaseCancelPurchase);

        ResponseEntity<Void> responseEntity = this.testRestTemplateUser.exchange(
                "/purchases/1",
                HttpMethod.PUT,
                new HttpEntity<>(this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void findUsersPurchases_returnsAllPurchasesNonDeliveredGroupedByIsActiveAndAStatusCode200_wheneverCalled() {

        this.userRepository.save(userAdmin);
        this.addressRepository.save(address);
        purchasesFindUsersPurchases.forEach(this.purchaseRepository::save);
        purchasedProductsFindUsersPurchases.forEach(this.purchasedProductRepository::save);

        ResponseEntity<Map<Boolean, List<PurchaseRestaurantResponseDTO>>> allPurchasesGroupedByIsActive = this.testRestTemplateUserAdmin.exchange(
                "/purchases/admin/find-all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(allPurchasesGroupedByIsActive.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.OK);

        Assertions.assertThat(allPurchasesGroupedByIsActive.getBody())
                .isNotNull()
                .containsEntry(true, purchaseRestaurantResponseDTOSToComparisonInFindUsersPurchases);
    }

    @Test
    void purchasePreparation_updatesIsFinishedOfAPurchaseToTrueAndReturnsAStatusCode204_whenThePassedIdIsValid() {

        this.userRepository.save(userAdmin);
        this.addressRepository.save(address);
        this.purchaseRepository.save(purchasePurchasePreparation);

        this.httpHeaders = this.getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateUserAdmin.exchange(
                "/purchases/admin/finish/1",
                HttpMethod.PUT,
                new HttpEntity<>(this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void purchasePreparation_returnsAStatusCode400_whenTheIdPassedDoesNotExistsInDatabase() {

        this.userRepository.save(userAdmin);

        this.httpHeaders = this.getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateUserAdmin.exchange(
                "/purchases/admin/finish/1",
                HttpMethod.PUT,
                new HttpEntity<>(this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void purchasePreparation_returnsAStatusCode400_whenThePassedIdIsInvalid() {

        this.userRepository.save(userAdmin);
        this.addressRepository.save(address);
        this.purchaseRepository.save(purchasePurchasePreparation);
        this.purchaseRepository.updateIsActiveById(false, purchasePurchasePreparation.getId());

        this.httpHeaders = this.getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateUserAdmin.exchange(
                "/purchases/admin/finish/1",
                HttpMethod.PUT,
                new HttpEntity<>(this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void purchasePreparation_returnsAStatusCode400_whenThePassedAcceptLanguageIsInvalid() {

        ResponseEntity<Void> responseEntityWithCsrfToken = this.testRestTemplateUser.exchange(
                "/products/find-promotions",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        String csrfToken = MapperToIntegration.fromHeaderToCsrfToken(
                Objects.requireNonNull(
                        responseEntityWithCsrfToken.getHeaders().getFirst("Set-Cookie")
                )
        );

        this.httpHeaders = new HttpHeaders();

        this.httpHeaders.add(acceptLanguage[0], "a");
        this.httpHeaders.add("Cookie", "XSRF-TOKEN=" + csrfToken);
        this.httpHeaders.add("X-XSRF-TOKEN", csrfToken);

        this.userRepository.save(userAdmin);
        this.addressRepository.save(address);
        this.purchaseRepository.save(purchasePurchasePreparation);

        ResponseEntity<Void> responseEntity = this.testRestTemplateUserAdmin.exchange(
                "/purchases/admin/finish/1",
                HttpMethod.PUT,
                new HttpEntity<>(this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void purchaseDelivery_updateIsDeliveredOfAPurchaseToTrueAndReturnsAStatusCode204_whenThePassedIdIsValid() {

        this.userRepository.save(userAdmin);
        this.addressRepository.save(address);
        Long purchaseId = this.purchaseRepository.save(purchasePurchaseDelivery).getId();
        this.purchaseRepository.updateIsFinishedById(true, purchaseId);

        this.httpHeaders = this.getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateUserAdmin.exchange(
                "/purchases/admin/deliver/1",
                HttpMethod.PUT,
                new HttpEntity<>(this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void purchaseDelivery_returnsAStatusCode400_whenTheIdPassedDoesNotExistsInDatabase() {

        this.userRepository.save(userAdmin);
        this.addressRepository.save(address);

        this.httpHeaders = this.getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateUserAdmin.exchange(
                "/purchases/admin/deliver/2",
                HttpMethod.PUT,
                new HttpEntity<>(this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void purchaseDelivery_returnsAStatusCode400_whenThePassedIdIsInvalid() {

        this.userRepository.save(userAdmin);
        this.addressRepository.save(address);
        this.purchaseRepository.save(purchasePurchaseDelivery);

        this.httpHeaders = this.getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateUserAdmin.exchange(
                "/purchases/admin/deliver/1",
                HttpMethod.PUT,
                new HttpEntity<>(this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void purchaseDelivery_returnsAStatusCode400_whenThePassedAcceptLanguageIsInvalid() {

        ResponseEntity<Void> responseEntityWithCsrfToken = this.testRestTemplateUser.exchange(
                "/products/find-promotions",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        String csrfToken = MapperToIntegration.fromHeaderToCsrfToken(
                Objects.requireNonNull(
                        responseEntityWithCsrfToken.getHeaders().getFirst("Set-Cookie")
                )
        );

        this.httpHeaders = new HttpHeaders();

        this.httpHeaders.add(acceptLanguage[0], "a");
        this.httpHeaders.add("Cookie", "XSRF-TOKEN=" + csrfToken);
        this.httpHeaders.add("X-XSRF-TOKEN", csrfToken);

        this.userRepository.save(userAdmin);
        this.addressRepository.save(address);
        Long purchaseId = this.purchaseRepository.save(purchasePurchaseDelivery).getId();
        this.purchaseRepository.updateIsFinishedById(true, purchaseId);

        ResponseEntity<Void> responseEntity = this.testRestTemplateUserAdmin.exchange(
                "/purchases/admin/deliver/1",
                HttpMethod.PUT,
                new HttpEntity<>(this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void deletePurchase_deleteAPurchaseAndReturnsAStatusCode204_whenThePassedIdIsValid() {

        this.userRepository.save(userAdmin);
        this.addressRepository.save(address);
        Long purchaseId = this.purchaseRepository.save(purchaseDeletePurchase).getId();
        this.purchaseRepository.updateIsActiveById(false, purchaseId);

        this.httpHeaders = this.getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateUserAdmin.exchange(
                "/purchases/admin/delete/1",
                HttpMethod.DELETE,
                new HttpEntity<>(this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deletePurchase_returnsAStatusCode400_whenTheIdPassedDoesNotExistsInDatabase() {

        this.userRepository.save(userAdmin);
        this.addressRepository.save(address);

        this.httpHeaders = this.getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateUserAdmin.exchange(
                "/purchases/admin/delete/2",
                HttpMethod.DELETE,
                new HttpEntity<>(this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void deletePurchase_returnsAStatusCode400_whenThePassedIdIsInvalid() {

        this.userRepository.save(userAdmin);
        this.addressRepository.save(address);
        this.purchaseRepository.save(purchaseDeletePurchase);

        this.httpHeaders = this.getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateUserAdmin.exchange(
                "/purchases/admin/delete/1",
                HttpMethod.DELETE,
                new HttpEntity<>(this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void deliveredPurchases_returnsAllLast50PurchasesDeliveredAndAStatusCode200_wheneverCalled() {

        this.userRepository.save(userAdmin);
        this.addressRepository.save(address);
        purchasesDeliveredPurchases.forEach(this.purchaseRepository::save);
        purchasedProductsDeliveredPurchases.forEach(this.purchasedProductRepository::save);
        purchasesDeliveredPurchases.forEach(purchase -> this.purchaseRepository.updateIsFinishedById(true, purchase.getId()));
        purchasesDeliveredPurchases.forEach(purchase -> this.purchaseRepository.updateIsDeliveredById(true, purchase.getId()));

        ResponseEntity<List<PurchaseRestaurantResponseDTO>> allLast50PurchasesDelivered = this.testRestTemplateUserAdmin.exchange(
                "/purchases/admin/delivered-purchases",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(allLast50PurchasesDelivered.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.OK);

        Assertions.assertThat(allLast50PurchasesDelivered.getBody())
                .isNotNull()
                .has(new Condition<>(
                        purchaseRestaurantResponseDTOS -> purchaseRestaurantResponseDTOS.size() <= 50,
                        "The PurchaseRestaurantResponseDTO list must not be greater than 50"))
                .asList()
                .hasSize(purchaseRestaurantResponseDTOSToComparisonInDeliveredPurchases.size())
                .isEqualTo(purchaseRestaurantResponseDTOSToComparisonInDeliveredPurchases)
                .contains(purchaseRestaurantResponseDTOSToComparisonInDeliveredPurchases.get(0));
    }

    @Test
    void disableDelivery_updatesIsDeliveredOfAPurchaseToFalseAndReturnsAStatusCode204_whenThePassedIdIsValid() {

        this.userRepository.save(userAdmin);
        this.addressRepository.save(address);
        Long purchaseId = this.purchaseRepository.save(purchaseDisableDelivery).getId();
        this.purchaseRepository.updateIsFinishedById(true, purchaseId);
        this.purchaseRepository.updateIsDeliveredById(true, purchaseId);

        this.httpHeaders = this.getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateUserAdmin.exchange(
                "/purchases/admin/disable-delivery/1",
                HttpMethod.PUT,
                new HttpEntity<>(this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void disableDelivery_returnsAStatusCode400_whenTheIdPassedDoesNotExistsInDatabase() {

        this.userRepository.save(userAdmin);

        this.httpHeaders = this.getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateUserAdmin.exchange(
                "/purchases/admin/disable-delivery/2",
                HttpMethod.PUT,
                new HttpEntity<>(this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @TestConfiguration
    @Lazy
    static class Config {

        @Bean(name = "testRestTemplateUserToPurchaseControllerIT")
        public TestRestTemplate testRestTemplateUser(@Value("${local.server.port}") int port) {

            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("name@gmail.com", "password");

            return new TestRestTemplate(restTemplateBuilder);
        }

        @Bean(name = "testRestTemplateAdminToPurchaseControllerIT")
        public TestRestTemplate testRestTemplateAdmin(@Value("${local.server.port}") int port) {

            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("name-admin@gmail.com", "password-admin");

            return new TestRestTemplate(restTemplateBuilder);
        }
    }
}
