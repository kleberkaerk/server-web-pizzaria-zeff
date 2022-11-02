package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.domain.PriceRating;
import com.webservicepizzariazeff.www.domain.Type;
import com.webservicepizzariazeff.www.dto.response.ProductResponseDTO;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ExtendWith(SpringExtension.class)
class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    private static List<ProductResponseDTO> productResponseDTOS;

    private static Map<Type, List<ProductResponseDTO>> mapFindAllProductsInStock;

    private static List<ProductResponseDTO> productResponseDTOToComparisonInFindAllProductsInStock;

    private static List<ProductResponseDTO> productResponseDTOFindProductsByTypeAndInStock;

    private static Map<Type, List<ProductResponseDTO>> mapFindProductsInPromotionAndInStock;

    private static List<ProductResponseDTO> productResponseDTOToComparisonInFindProductsInPromotionAndInStock;

    static void setProductResponseDTOS() {

        productResponseDTOS = List.of(
                ProductResponseDTO.ProductResponseDTOBuilder.builder()
                        .id(1L)
                        .name("name1")
                        .description("description1")
                        .price(new BigDecimal("10.00"))
                        .type(Type.SALTY_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image1")
                        .isStocked(true)
                        .build(),
                ProductResponseDTO.ProductResponseDTOBuilder.builder()
                        .id(2L)
                        .name("name2")
                        .description("description2")
                        .price(new BigDecimal("20.00"))
                        .type(Type.SWEET_PIZZA)
                        .priceRating(PriceRating.REGULAR_PRICE)
                        .image("/image2")
                        .isStocked(true)
                        .build(),
                ProductResponseDTO.ProductResponseDTOBuilder.builder()
                        .id(3L)
                        .name("name3")
                        .description("description3")
                        .price(new BigDecimal("30.00"))
                        .type(Type.SALTY_ESFIHA)
                        .priceRating(PriceRating.REGULAR_PRICE)
                        .image("/image3")
                        .isStocked(true)
                        .build(),
                ProductResponseDTO.ProductResponseDTOBuilder.builder()
                        .id(4L)
                        .name("name4")
                        .description("description4")
                        .price(new BigDecimal("40.00"))
                        .type(Type.SWEET_ESFIHA)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image4")
                        .isStocked(true)
                        .build(),
                ProductResponseDTO.ProductResponseDTOBuilder.builder()
                        .id(5L)
                        .name("name5")
                        .description("description5")
                        .price(new BigDecimal("50.00"))
                        .type(Type.DRINK)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image5")
                        .isStocked(true)
                        .build(),
                ProductResponseDTO.ProductResponseDTOBuilder.builder()
                        .id(6L)
                        .name("name6")
                        .description("description6")
                        .price(new BigDecimal("60.00"))
                        .type(Type.DRINK)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image6")
                        .isStocked(true)
                        .build(),
                ProductResponseDTO.ProductResponseDTOBuilder.builder()
                        .id(7L)
                        .name("name7")
                        .description("description7")
                        .price(new BigDecimal("70.00"))
                        .type(Type.DRINK)
                        .priceRating(PriceRating.REGULAR_PRICE)
                        .image("/image7")
                        .isStocked(true)
                        .build(),
                ProductResponseDTO.ProductResponseDTOBuilder.builder()
                        .id(8L)
                        .name("name8")
                        .description("description8")
                        .price(new BigDecimal("80.00"))
                        .type(Type.DRINK)
                        .priceRating(PriceRating.REGULAR_PRICE)
                        .image("/image8")
                        .isStocked(true)
                        .build(),
                ProductResponseDTO.ProductResponseDTOBuilder.builder()
                        .id(9L)
                        .name("name9")
                        .description("description9")
                        .price(new BigDecimal("90.00"))
                        .type(Type.DRINK)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image9")
                        .isStocked(true)
                        .build(),
                ProductResponseDTO.ProductResponseDTOBuilder.builder()
                        .id(10L)
                        .name("name10")
                        .description("description10")
                        .price(new BigDecimal("100.00"))
                        .type(Type.SWEET_ESFIHA)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image10")
                        .isStocked(true)
                        .build(),
                ProductResponseDTO.ProductResponseDTOBuilder.builder()
                        .id(11L)
                        .name("name11")
                        .description("description11")
                        .price(new BigDecimal("110.00"))
                        .type(Type.SWEET_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image11")
                        .isStocked(true)
                        .build(),
                ProductResponseDTO.ProductResponseDTOBuilder.builder()
                        .id(12L)
                        .name("name12")
                        .description("description12")
                        .price(new BigDecimal("120.00"))
                        .type(Type.SWEET_ESFIHA)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image12")
                        .isStocked(true)
                        .build(),
                ProductResponseDTO.ProductResponseDTOBuilder.builder()
                        .id(13L)
                        .name("name13")
                        .description("description13")
                        .price(new BigDecimal("130.00"))
                        .type(Type.SALTY_ESFIHA)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image13")
                        .isStocked(true)
                        .build(),
                ProductResponseDTO.ProductResponseDTOBuilder.builder()
                        .id(14L)
                        .name("name14")
                        .description("description14")
                        .price(new BigDecimal("140.00"))
                        .type(Type.DRINK)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image14")
                        .isStocked(true)
                        .build()
        );
    }

    static void setMapForReturnOfTheFindAllProductsInStock() {

        mapFindAllProductsInStock = Map.of(
                Type.SALTY_PIZZA, productResponseDTOS.stream()
                        .filter(ProductResponseDTO::isStocked)
                        .filter(productResponseDTO -> productResponseDTO.getType() == Type.SALTY_PIZZA)
                        .toList(),
                Type.SWEET_PIZZA, productResponseDTOS.stream()
                        .filter(ProductResponseDTO::isStocked)
                        .filter(productResponseDTO -> productResponseDTO.getType() == Type.SWEET_PIZZA)
                        .toList(),
                Type.SALTY_ESFIHA, productResponseDTOS.stream()
                        .filter(ProductResponseDTO::isStocked)
                        .filter(productResponseDTO -> productResponseDTO.getType() == Type.SALTY_ESFIHA)
                        .toList(),
                Type.SWEET_ESFIHA, productResponseDTOS.stream()
                        .filter(ProductResponseDTO::isStocked)
                        .filter(productResponseDTO -> productResponseDTO.getType() == Type.SWEET_ESFIHA)
                        .toList(),
                Type.DRINK, productResponseDTOS.stream()
                        .filter(ProductResponseDTO::isStocked)
                        .filter(productResponseDTO -> productResponseDTO.getType() == Type.DRINK)
                        .toList()
        );
    }

    static void setProductResponseDTOToComparisonInFindAllProductsInStock() {

        productResponseDTOToComparisonInFindAllProductsInStock = mapFindAllProductsInStock.get(Type.DRINK);
    }

    static void setProductResponseDTOFindProductsByTypeAndInStock() {

        productResponseDTOFindProductsByTypeAndInStock = productResponseDTOS.stream()
                .filter(ProductResponseDTO::isStocked)
                .filter(productResponseDTO -> productResponseDTO.getType() == Type.DRINK)
                .toList();
    }

    static void setMapFindProductsInPromotionAndInStock() {

        mapFindProductsInPromotionAndInStock = Map.of(
                Type.SALTY_PIZZA, productResponseDTOS.stream()
                        .filter(ProductResponseDTO::isStocked)
                        .filter(productResponseDTO -> productResponseDTO.getPriceRating() == PriceRating.PROMOTION)
                        .filter(productResponseDTO -> productResponseDTO.getType() == Type.SALTY_PIZZA)
                        .toList(),
                Type.SWEET_PIZZA, productResponseDTOS.stream()
                        .filter(ProductResponseDTO::isStocked)
                        .filter(productResponseDTO -> productResponseDTO.getPriceRating() == PriceRating.PROMOTION)
                        .filter(productResponseDTO -> productResponseDTO.getType() == Type.SWEET_PIZZA)
                        .toList(),
                Type.SALTY_ESFIHA, productResponseDTOS.stream()
                        .filter(ProductResponseDTO::isStocked)
                        .filter(productResponseDTO -> productResponseDTO.getPriceRating() == PriceRating.PROMOTION)
                        .filter(productResponseDTO -> productResponseDTO.getType() == Type.SALTY_ESFIHA)
                        .toList(),
                Type.SWEET_ESFIHA, productResponseDTOS.stream()
                        .filter(ProductResponseDTO::isStocked)
                        .filter(productResponseDTO -> productResponseDTO.getPriceRating() == PriceRating.PROMOTION)
                        .filter(productResponseDTO -> productResponseDTO.getType() == Type.SWEET_ESFIHA)
                        .toList(),
                Type.DRINK, productResponseDTOS.stream()
                        .filter(ProductResponseDTO::isStocked)
                        .filter(productResponseDTO -> productResponseDTO.getPriceRating() == PriceRating.PROMOTION)
                        .filter(productResponseDTO -> productResponseDTO.getType() == Type.DRINK)
                        .toList()
        );
    }

    static void setProductResponseDTOToComparisonInFindProductsInPromotionAndInStock() {

        productResponseDTOToComparisonInFindProductsInPromotionAndInStock = mapFindProductsInPromotionAndInStock.get(Type.SWEET_ESFIHA);
    }

    @BeforeAll
    static void initializeObjects() {

        setProductResponseDTOS();
        setMapForReturnOfTheFindAllProductsInStock();
        setProductResponseDTOToComparisonInFindAllProductsInStock();
        setProductResponseDTOFindProductsByTypeAndInStock();
        setMapFindProductsInPromotionAndInStock();
        setProductResponseDTOToComparisonInFindProductsInPromotionAndInStock();
    }

    @BeforeEach
    void definitionBehaviorsForMocks() {

        BDDMockito.when(this.productService.findAllProductsInStock())
                .thenReturn(mapFindAllProductsInStock);

        BDDMockito.when(this.productService.findProductsByTypeAndInStock(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.anyString()))
                .thenReturn(new PageImpl<>(productResponseDTOFindProductsByTypeAndInStock));

        BDDMockito.when(this.productService.findProductsInPromotionAndInStock())
                .thenReturn(mapFindProductsInPromotionAndInStock);
    }

    @Test
    void findProductsInStock_returnsAMapOfTheAllProductsInStockOrderedByTypeAndAStatusCodeOk_wheneverCalled() {

        Assertions.assertThatCode(() -> this.productController.findProductsInStock())
                .doesNotThrowAnyException();

        Assertions.assertThat(this.productController.findProductsInStock())
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(mapFindAllProductsInStock));

        Assertions.assertThat(this.productController.findProductsInStock().getBody())
                .isNotNull()
                .containsEntry(Type.DRINK, productResponseDTOToComparisonInFindAllProductsInStock);
    }

    @Test
    void findByType_returnsAPageOfTheAllProductsOfAGivenTypeAndAStatusCodeOk_whenThePassedTypeIsValid() {

        Assertions.assertThatCode(() -> this.productController.findByType(Page.empty().getPageable(), "DRINK"))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.productController.findByType(Page.empty().getPageable(), "DRINK"))
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(new PageImpl<>(productResponseDTOFindProductsByTypeAndInStock)));

        Assertions.assertThat(Objects.requireNonNull(this.productController.findByType(Page.empty().getPageable(), "DRINK").getBody()).toList())
                .asList()
                .hasSize(6)
                .contains(productResponseDTOFindProductsByTypeAndInStock.get(2));
    }

    @Test
    void findByType_returnsAPageWithNoProductsAndAStatusCodeOk_whenTheTypeParameterIsNotEqualToTheNameOfOneOfTheTypeEnumerations() {

        BDDMockito.when(this.productService.findProductsByTypeAndInStock(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.anyString()))
                .thenReturn(Page.empty());

        Assertions.assertThatCode(() -> this.productController.findByType(Page.empty().getPageable(), "SWEET"))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.productController.findByType(Page.empty().getPageable(), "SWEET"))
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(new PageImpl<>(Collections.emptyList())));

        Assertions.assertThat(Objects.requireNonNull(this.productController.findByType(Page.empty().getPageable(), "SWEET").getBody()).toList())
                .isEmpty();
    }

    @Test
    void findProductsInPromotion_returnsAMapOfTheAllProductsInPromotionOrderedByTypeAndAStatusCodeOk_wheneverCalled() {

        Assertions.assertThatCode(() -> this.productController.findProductsInPromotion())
                .doesNotThrowAnyException();

        Assertions.assertThat(this.productController.findProductsInPromotion())
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(mapFindProductsInPromotionAndInStock));

        Assertions.assertThat(this.productController.findProductsInPromotion().getBody())
                .containsEntry(Type.SWEET_ESFIHA, productResponseDTOToComparisonInFindProductsInPromotionAndInStock);
    }
}