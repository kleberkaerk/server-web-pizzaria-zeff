package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.domain.PriceRating;
import com.webservicepizzariazeff.www.domain.Product;
import com.webservicepizzariazeff.www.domain.Type;
import com.webservicepizzariazeff.www.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ExtendWith(SpringExtension.class)
class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    private static List<Product> returnFromFindAll;

    private static List<Product> returnFromFindByType;

    private static List<Product> returnFromFindByPriceRating;

    private static List<Product> returnFromFindByTypeAndPriceRating;

    static void setReturnFromFindAll() {

        returnFromFindAll = new ArrayList<>(List.of(
                Product.ProductBuilder.builder()
                        .id(1L)
                        .name("name1")
                        .description("description1")
                        .price(new BigDecimal("10"))
                        .type(Type.SALTY_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image1.png")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(2L)
                        .name("name2")
                        .description("description2")
                        .price(new BigDecimal("20"))
                        .type(Type.SWEET_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image2.png")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(3L)
                        .name("name3")
                        .description("description3")
                        .price(new BigDecimal("30"))
                        .type(Type.SWEET_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image3.png")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(4L)
                        .name("name4")
                        .description("description4")
                        .price(new BigDecimal("40"))
                        .type(Type.SALTY_ESFIHA)
                        .priceRating(PriceRating.REGULAR_PRICE)
                        .image("/image4.png")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(5L)
                        .name("name5")
                        .description("description5")
                        .price(new BigDecimal("50"))
                        .type(Type.SALTY_ESFIHA)
                        .priceRating(PriceRating.REGULAR_PRICE)
                        .image("/image5.png")
                        .build()
        ));
    }

    static void setReturnFromFindByType() {

        returnFromFindByType = returnFromFindAll.stream()
                .filter(product -> product.getType().name().equals(Type.SWEET_PIZZA.name()))
                .toList();
    }

    static void setReturnFromFindByPriceRating() {

        returnFromFindByPriceRating = returnFromFindAll.stream()
                .filter(product -> product.getPriceRating().name().equals(PriceRating.REGULAR_PRICE.name()))
                .toList();
    }

    static void setReturnFromFindByTypeAndPriceRating() {

        returnFromFindByTypeAndPriceRating = returnFromFindAll.stream()
                .filter(product -> product.getType().name().equals(Type.SALTY_PIZZA.name()))
                .filter(product -> product.getPriceRating().name().equals(PriceRating.PROMOTION.name()))
                .toList();
    }

    @BeforeAll
    static void initializeObjects() {

        setReturnFromFindAll();
        setReturnFromFindByType();
        setReturnFromFindByPriceRating();
        setReturnFromFindByTypeAndPriceRating();
    }

    @BeforeEach
    void definitionBehaviorsForMocks() {

        BDDMockito.when(this.productService.findAll(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(returnFromFindAll));

        BDDMockito.when(this.productService.findByType(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.anyString()))
                .thenReturn(new PageImpl<>(returnFromFindByType));

        BDDMockito.when(this.productService.findByPriceRating(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.anyString()))
                .thenReturn(new PageImpl<>(returnFromFindByPriceRating));

        BDDMockito.when(this.productService.findByTypeAndPriceRating(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(new PageImpl<>(returnFromFindByTypeAndPriceRating));
    }

    @Test
    void findAll_returnsAPageOfTheAllProductsAndAStatusCodeOk_ever() {

        Assertions.assertThat(this.productController.findAll(Page.empty().getPageable()))
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(new PageImpl<>(returnFromFindAll)));

        Assertions.assertThat(Objects.requireNonNull(this.productController.findAll(Page.empty().getPageable()).getBody()).toList())
                .hasSize(5);

        Assertions.assertThat(Objects.requireNonNull(this.productController.findAll(Page.empty().getPageable()).getBody()).toList())
                .asList()
                .contains(Product.ProductBuilder.builder()
                        .id(1L)
                        .name("name1")
                        .description("description1")
                        .price(new BigDecimal("10"))
                        .type(Type.SALTY_PIZZA)
                        .priceRating(PriceRating.REGULAR_PRICE)
                        .image("/image1.png")
                        .build());
    }

    @Test
    void findByType_returnsAPageOfProductsOfAGivenTypeAndAStatusCodeOk_whenTheTypeParameterIsEqualToTheNameOfOneOfTheTypeEnumerations() {

        Assertions.assertThat(this.productController.findByType(Page.empty().getPageable(), "SWEET_PIZZA"))
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(new PageImpl<>(returnFromFindByType)));

        Assertions.assertThat(Objects.requireNonNull(this.productController.findByType(Page.empty().getPageable(), "SWEET_PIZZA").getBody()).toList())
                .hasSize(2);

        Assertions.assertThat(Objects.requireNonNull(this.productController.findByType(Page.empty().getPageable(), "SWEET_PIZZA").getBody()).toList())
                .asList()
                .contains(Product.ProductBuilder.builder()
                        .id(2L)
                        .name("name2")
                        .description("description2")
                        .price(new BigDecimal("20"))
                        .type(Type.SWEET_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image2.png")
                        .build());
    }

    @Test
    void findByType_returnsAPageWithNoProductsAndAStatusCodeOk_whenTheTypeParameterIsNotEqualToTheNameOfOneOfTheTypeEnumerations() {

        BDDMockito.when(this.productService.findByType(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.anyString()))
                .thenReturn(Page.empty());

        Assertions.assertThat(this.productController.findByType(Page.empty().getPageable(), "SWEET"))
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(new PageImpl<>(Collections.emptyList())));

        Assertions.assertThat(Objects.requireNonNull(this.productController.findByType(Page.empty().getPageable(), "SWEET").getBody()).toList())
                .isEmpty();
    }

    @Test
    void findByPriceRating_returnsAPageOfProductsOfAGivenPriceRatingAndAStatusCodeOk_whenThePriceRatingParameterIsEqualToTheNameOfOneOfThePriceRatingEnumerations() {

        Assertions.assertThat(this.productController.findByPriceRating(Page.empty().getPageable(), "REGULAR_PRICE"))
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(new PageImpl<>(returnFromFindByPriceRating)));

        Assertions.assertThat(Objects.requireNonNull(this.productController.findByPriceRating(Page.empty().getPageable(), "REGULAR_PRICE").getBody()).toList())
                .hasSize(2);

        Assertions.assertThat(Objects.requireNonNull(this.productController.findByPriceRating(Page.empty().getPageable(), "REGULAR_PRICE").getBody()).toList())
                .asList()
                .contains(Product.ProductBuilder.builder()
                        .id(4L)
                        .name("name4")
                        .description("description4")
                        .price(new BigDecimal("40"))
                        .type(Type.SALTY_ESFIHA)
                        .priceRating(PriceRating.REGULAR_PRICE)
                        .image("/image4.png")
                        .build());
    }

    @Test
    void findByPriceRating_returnsAPageWithNoProductsAndAStatusCodeOk_whenThePriceRatingParameterIsNotEqualToTheNameOfOneOfThePriceRatingEnumerations() {

        BDDMockito.when(this.productService.findByPriceRating(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.anyString()))
                .thenReturn(Page.empty());

        Assertions.assertThat(this.productController.findByPriceRating(Page.empty().getPageable(), "REGULAR"))
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(new PageImpl<>(Collections.emptyList())));

        Assertions.assertThat(Objects.requireNonNull(this.productController.findByPriceRating(Page.empty().getPageable(), "REGULAR").getBody()).toList())
                .isEmpty();
    }

    @Test
    void findByTypeAndPriceRating_returnsAPageOfProductsOfAGivenTypeAndPriceRatingAndAStatusCodeOk_whenTheTypeParameterAndPriceRatingParameterAreEqualToTheNamesOfOneTypeEnumerationAndPriceRatingEnumeration() {

        Assertions.assertThat(this.productController.findByTypeAndPriceRating(Page.empty().getPageable(), "SALTY_PIZZA", "PROMOTION"))
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(new PageImpl<>(returnFromFindByTypeAndPriceRating)));

        Assertions.assertThat(Objects.requireNonNull(this.productController.findByTypeAndPriceRating(Page.empty().getPageable(), "SALTY_PIZZA", "PROMOTION").getBody()).toList())
                .hasSize(1);

        Assertions.assertThat(Objects.requireNonNull(this.productController.findByTypeAndPriceRating(Page.empty().getPageable(), "SALTY_PIZZA", "PROMOTION").getBody()).toList())
                .asList()
                .contains(Product.ProductBuilder.builder()
                        .id(1L)
                        .name("name1")
                        .description("description1")
                        .price(new BigDecimal("10"))
                        .type(Type.SALTY_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image1.png")
                        .build());
    }

    @Test
    void findByTypeAndPriceRating_returnsAPageWithNoProductsAndAStatusCodeOk_whenTheTypeParameterOrPriceRatingParameterAreNotEqualToTheNamesOfOneTypeEnumerationOrPriceRatingEnumeration() {

        BDDMockito.when(this.productService.findByTypeAndPriceRating(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(Page.empty());

        Assertions.assertThat(this.productController.findByTypeAndPriceRating(Page.empty().getPageable(), "SALTY", "REGULAR"))
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(new PageImpl<>(Collections.emptyList())));

        Assertions.assertThat(Objects.requireNonNull(this.productController.findByTypeAndPriceRating(Page.empty().getPageable(), "SALTY", "REGULAR").getBody()).toList())
                .isEmpty();
    }
}