package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.PriceRating;
import com.webservicepizzariazeff.www.domain.Product;
import com.webservicepizzariazeff.www.domain.Type;
import com.webservicepizzariazeff.www.repository.ProductRepository;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private static List<Product> returnFromFindAll;

    private static List<Product> returnFromFindByType;

    private static List<Product> returnFromFindByPriceRating;

    private static List<Product> returnFromFindByTypeAndPriceRating;

    @BeforeAll
    static void setObjects() {

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

        returnFromFindByType = returnFromFindAll.stream()
                .filter(product -> product.getType().name().equals(Type.SWEET_PIZZA.name()))
                .toList();

        returnFromFindByPriceRating = returnFromFindAll.stream()
                .filter(product -> product.getPriceRating().name().equals(PriceRating.REGULAR_PRICE.name()))
                .toList();

        returnFromFindByTypeAndPriceRating = returnFromFindAll.stream()
                .filter(product -> product.getType().name().equals(Type.SALTY_PIZZA.name()))
                .filter(product -> product.getPriceRating().name().equals(PriceRating.PROMOTION.name()))
                .toList();
    }

    @BeforeEach
    void definitionBehaviorsForMocks() {

        BDDMockito.when(this.productRepository.findAll(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(returnFromFindAll));

        BDDMockito.when(this.productRepository.findByType(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.any(Type.class)))
                .thenReturn(new PageImpl<>(returnFromFindByType));

        BDDMockito.when(this.productRepository.findByPriceRating(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.any(PriceRating.class)))
                .thenReturn(new PageImpl<>(returnFromFindByPriceRating));

        BDDMockito.when(this.productRepository.findByTypeAndPriceRating(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.any(Type.class), ArgumentMatchers.any(PriceRating.class)))
                .thenReturn(new PageImpl<>(returnFromFindByTypeAndPriceRating));
    }

    @Test
    void findAll_returnsAListOfAllProductsInTheDatabase_ever() {

        Assertions.assertThat(this.productService.findAll(Page.empty().getPageable()))
                .isNotNull()
                .isEqualTo(new PageImpl<>(returnFromFindAll));

        Assertions.assertThat(this.productService.findAll(Page.empty().getPageable()).toList())
                .hasSize(5);

        Assertions.assertThat(Objects.requireNonNull(this.productService.findAll(Page.empty().getPageable())).toList())
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
    void findByType_returnsAPageOfProductsOfAGivenTypeFromTheDatabase_whenTheTypeParameterIsEqualToTheNameOfOneOfTheTypeEnumerations() {

        Assertions.assertThat(this.productService.findByType(Page.empty().getPageable(), "SWEET_PIZZA"))
                .isNotNull()
                .isEqualTo(new PageImpl<>(returnFromFindByType));

        Assertions.assertThat(Objects.requireNonNull(this.productService.findByType(Page.empty().getPageable(), "SWEET_PIZZA")).toList())
                .hasSize(2);

        Assertions.assertThat(Objects.requireNonNull(this.productService.findByType(Page.empty().getPageable(), "SWEET_PIZZA")).toList())
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
    void findByType_returnsAPageWithNoProducts_whenTheTypeParameterIsNotEqualToTheNameOfOneOfTheTypeEnumerations() {

        BDDMockito.when(this.productRepository.findByType(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.any(Type.class)))
                .thenReturn(Page.empty());

        Assertions.assertThat(this.productService.findByType(Page.empty().getPageable(), "SWEET"))
                .isNotNull()
                .isEqualTo(new PageImpl<>(Collections.emptyList()));

        Assertions.assertThat(Objects.requireNonNull(this.productService.findByType(Page.empty().getPageable(), "SWEET")).toList())
                .isEmpty();
    }

    @Test
    void findByPriceRating_returnsAPageOfProductsOfAGivenPriceRatingFromDatabase_whenThePriceRatingParameterIsEqualToTheNameOfOneOfThePriceRatingEnumerations() {

        Assertions.assertThat(this.productService.findByPriceRating(Page.empty().getPageable(), "REGULAR_PRICE"))
                .isNotNull()
                .isEqualTo(new PageImpl<>(returnFromFindByPriceRating));

        Assertions.assertThat(Objects.requireNonNull(this.productService.findByPriceRating(Page.empty().getPageable(), "REGULAR_PRICE")).toList())
                .hasSize(2);

        Assertions.assertThat(Objects.requireNonNull(this.productService.findByPriceRating(Page.empty().getPageable(), "REGULAR_PRICE")).toList())
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
    void findByPriceRating_returnsAPageWithNoProducts_whenThePriceRatingParameterIsNotEqualToTheNameOfOneOfThePriceRatingEnumerations() {

        BDDMockito.when(this.productRepository.findByPriceRating(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.any(PriceRating.class)))
                .thenReturn(Page.empty());

        Assertions.assertThat(this.productService.findByPriceRating(Page.empty().getPageable(), "REGULAR"))
                .isNotNull()
                .isEqualTo(new PageImpl<>(Collections.emptyList()));

        Assertions.assertThat(Objects.requireNonNull(this.productService.findByPriceRating(Page.empty().getPageable(), "REGULAR")).toList())
                .isEmpty();
    }

    @Test
    void findByTypeAndPriceRating_returnsAPageOfProductsOfAGivenTypeAndPriceRatingFromDatabase_whenTheTypeParameterAndPriceRatingParameterAreEqualToTheNamesOfOneTypeEnumerationAndPriceRatingEnumeration() {

        Assertions.assertThat(this.productService.findByTypeAndPriceRating(Page.empty().getPageable(), "SALTY_PIZZA", "PROMOTION"))
                .isNotNull()
                .isEqualTo(new PageImpl<>(returnFromFindByTypeAndPriceRating));

        Assertions.assertThat(Objects.requireNonNull(this.productService.findByTypeAndPriceRating(Page.empty().getPageable(), "SALTY_PIZZA", "PROMOTION")).toList())
                .hasSize(1);

        Assertions.assertThat(Objects.requireNonNull(this.productService.findByTypeAndPriceRating(Page.empty().getPageable(), "SALTY_PIZZA", "PROMOTION")).toList())
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
    void findByTypeAndPriceRating_returnsAPageWithNoProducts_whenTheTypeParameterOrPriceRatingParameterAreNotEqualToTheNamesOfOneTypeEnumerationOrPriceRatingEnumeration() {

        BDDMockito.when(this.productRepository.findByTypeAndPriceRating(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.any(Type.class), ArgumentMatchers.any(PriceRating.class)))
                .thenReturn(Page.empty());

        Assertions.assertThat(this.productService.findByTypeAndPriceRating(Page.empty().getPageable(), "SALTY", "REGULAR"))
                .isNotNull()
                .isEqualTo(new PageImpl<>(Collections.emptyList()));

        Assertions.assertThat(Objects.requireNonNull(this.productService.findByTypeAndPriceRating(Page.empty().getPageable(), "SALTY", "REGULAR")).toList())
                .isEmpty();
    }
}