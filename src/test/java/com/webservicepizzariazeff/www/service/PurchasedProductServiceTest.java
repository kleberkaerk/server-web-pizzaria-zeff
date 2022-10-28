package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.Purchase;
import com.webservicepizzariazeff.www.domain.PurchasedProduct;
import com.webservicepizzariazeff.www.repository.PurchasedProductRepository;
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

@ExtendWith(SpringExtension.class)
class PurchasedProductServiceTest {

    @InjectMocks
    private PurchasedProductService purchasedProductService;

    @Mock
    private PurchasedProductRepository purchasedProductRepository;

    private static PurchasedProduct purchasedProduct;

    @BeforeAll
    static void setPurchasedProduct() {

        purchasedProduct = PurchasedProduct.PurchasedProductBuilder.builder()
                .id(1L)
                .name("name")
                .purchase(Purchase.PurchaseBuilder.builder().id(1L).build())
                .build();
    }

    @BeforeEach
    void definitionBehaviorsForMocks() {

        BDDMockito.when(this.purchasedProductRepository.save(ArgumentMatchers.any(PurchasedProduct.class)))
                .thenReturn(PurchasedProduct.PurchasedProductBuilder.builder()
                        .id(1L)
                        .name("name")
                        .purchase(Purchase.PurchaseBuilder.builder().id(1L).build())
                        .build());
    }

    @Test
    void save_persistsANewPurchasedProductInTheDatabase_wheneverCalled() {

        Assertions.assertThatCode(() -> this.purchasedProductService.save(purchasedProduct))
                .doesNotThrowAnyException();
    }
}