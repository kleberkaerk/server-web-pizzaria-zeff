package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.Address;
import com.webservicepizzariazeff.www.domain.Purchase;
import com.webservicepizzariazeff.www.domain.PurchasedProduct;
import com.webservicepizzariazeff.www.domain.User;
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

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(SpringExtension.class)
class PurchaseServiceTest {

    @InjectMocks
    private PurchaseService purchaseService;

    @Mock
    private PurchaseRepository purchaseRepository;

    private static List<PurchasedProduct> purchasedProducts;

    private static User user;

    private static Address address;

    private static Purchase purchase;

    @BeforeAll
    static void setObjects() {

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

        user = User.UserBuilder.builder().id(1L)
                .name("name")
                .username("username")
                .password("password")
                .authorities("ROLE_USER")
                .build();

        address = Address.AddressBuilder.builder()
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .build();

        purchase = Purchase.PurchaseBuilder.builder()
                .id(1L)
                .build();
    }

    @BeforeEach
    void definitionOfBehaviorsForMocks() {

        BDDMockito.when(this.purchaseRepository.save(ArgumentMatchers.any(Purchase.class)))
                .thenReturn(Purchase.PurchaseBuilder.builder()
                        .id(1L)
                        .amount(new BigDecimal("10.00"))
                        .dateAndTime("12/34/5678T90:12")
                        .cardName("cardName")
                        .isActive(true)
                        .isFinished(true)
                        .isDelivered(true)
                        .isPaymentThroughTheWebsite(true)
                        .purchasedProducts(purchasedProducts)
                        .user(user)
                        .address(address)
                        .build());
    }

    @Test
    void save_persistAndReturnsANewPurchaseInTheDatabase_wheneverCalled() {

        Assertions.assertThat(this.purchaseService.save(purchase).getId())
                .isNotNull()
                .isEqualTo(1L);

        Assertions.assertThat(this.purchaseService.save(purchase).getAmount())
                .isNotNull()
                .isEqualTo(new BigDecimal("10.00"));

        Assertions.assertThat(this.purchaseService.save(purchase).getDateAndTime())
                .isNotNull()
                .isEqualTo("12/34/5678T90:12");

        Assertions.assertThat(this.purchaseService.save(purchase).getCardName())
                .isNotNull()
                .isEqualTo("cardName");

        Assertions.assertThat(this.purchaseService.save(purchase).isActive())
                .isTrue();

        Assertions.assertThat(this.purchaseService.save(purchase).isFinished())
                .isTrue();

        Assertions.assertThat(this.purchaseService.save(purchase).isDelivered())
                .isTrue();

        Assertions.assertThat(this.purchaseService.save(purchase).isPaymentThroughTheWebsite())
                .isTrue();

        Assertions.assertThat(this.purchaseService.save(purchase).getPurchasedProducts())
                .isNotNull()
                .asList()
                .isEqualTo(purchasedProducts);

        Assertions.assertThat(this.purchaseService.save(purchase).getUser())
                .isNotNull()
                .isEqualTo(user);

        Assertions.assertThat(this.purchaseService.save(purchase).getAddress())
                .isNotNull()
                .isEqualTo(address);
    }
}