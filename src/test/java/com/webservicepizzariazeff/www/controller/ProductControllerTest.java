package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.domain.PriceRating;
import com.webservicepizzariazeff.www.domain.Type;
import com.webservicepizzariazeff.www.dto.request.ProductRequestDTO;
import com.webservicepizzariazeff.www.dto.response.ProductResponseDTO;
import com.webservicepizzariazeff.www.exception.ExistingProductException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    private static List<ProductResponseDTO> productResponseDTOS;

    private static Map<Type, List<ProductResponseDTO>> mapFindAllProductsInStock;

    private static List<ProductResponseDTO> productResponseDTOSToComparisonInFindProductsInStock;

    private static List<ProductResponseDTO> productResponseDTOSFindProductsByTypeAndInStock;

    private static Map<Type, List<ProductResponseDTO>> mapFindProductsInPromotionAndInStock;

    private static List<ProductResponseDTO> productResponseDTOSToComparisonInFindProductsInPromotion;

    private static List<ProductResponseDTO> productResponseDTOSSearchProductsByNameAndInStock;

    private static Map<Boolean, List<ProductResponseDTO>> mapFindAllGrouped;

    private static List<ProductResponseDTO> productResponseDTOSToComparisonInFindAllProducts;

    private static ProductRequestDTO productRequestDTO;

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
                        .build(),
                ProductResponseDTO.ProductResponseDTOBuilder.builder()
                        .id(15L)
                        .name("name15")
                        .description("description15")
                        .price(new BigDecimal("150.00"))
                        .type(Type.DRINK)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image15")
                        .isStocked(false)
                        .build(),
                ProductResponseDTO.ProductResponseDTOBuilder.builder()
                        .id(16L)
                        .name("name16")
                        .description("description16")
                        .price(new BigDecimal("160.00"))
                        .type(Type.DRINK)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image16")
                        .isStocked(false)
                        .build()
        );
    }

    static void setMapForReturnOfTheFindAllProductsInStock() {

        mapFindAllProductsInStock = productResponseDTOS.stream()
                .filter(ProductResponseDTO::isStocked)
                .collect(Collectors.groupingBy(ProductResponseDTO::getType));
    }

    static void setProductResponseDTOSToComparisonInFindProductsInStock() {

        productResponseDTOSToComparisonInFindProductsInStock = mapFindAllProductsInStock.get(Type.DRINK);
    }

    static void setProductResponseDTOSFindProductsByTypeAndInStock() {

        productResponseDTOSFindProductsByTypeAndInStock = productResponseDTOS.stream()
                .filter(ProductResponseDTO::isStocked)
                .filter(productResponseDTO -> productResponseDTO.getType() == Type.DRINK)
                .toList();
    }

    static void setMapFindProductsInPromotionAndInStock() {

        mapFindProductsInPromotionAndInStock = productResponseDTOS.stream()
                .filter(ProductResponseDTO::isStocked)
                .filter(productResponseDTO -> productResponseDTO.getPriceRating() == PriceRating.PROMOTION)
                .collect(Collectors.groupingBy(ProductResponseDTO::getType));
    }

    static void setProductResponseDTOSToComparisonInFindProductsInPromotion() {

        productResponseDTOSToComparisonInFindProductsInPromotion = mapFindProductsInPromotionAndInStock.get(Type.SWEET_ESFIHA);
    }

    static void setProductResponseDTOSSearchProductsByNameAndInStock() {

        productResponseDTOSSearchProductsByNameAndInStock = productResponseDTOS.stream()
                .filter(ProductResponseDTO::isStocked)
                .toList();
    }

    static void setMapFindAllGrouped() {

        mapFindAllGrouped = productResponseDTOS.stream()
                .sorted(Comparator.comparing(ProductResponseDTO::getPriceRating))
                .collect(Collectors.groupingBy(ProductResponseDTO::isStocked));
    }

    static void setProductResponseDTOSToComparisonInFindAllProducts() {

        productResponseDTOSToComparisonInFindAllProducts = mapFindAllGrouped.get(true);
    }

    static void setProductRequestDTO() {

        productRequestDTO = ProductRequestDTO.ProductRequestDTOBuilder.builder()
                .name("name")
                .description("description")
                .price(new BigDecimal("10.00"))
                .type(Type.SALTY_PIZZA)
                .priceRating(PriceRating.PROMOTION)
                .build();
    }

    @BeforeAll
    static void initializeObjects() {

        setProductResponseDTOS();
        setMapForReturnOfTheFindAllProductsInStock();
        setProductResponseDTOSToComparisonInFindProductsInStock();
        setProductResponseDTOSFindProductsByTypeAndInStock();
        setMapFindProductsInPromotionAndInStock();
        setProductResponseDTOSToComparisonInFindProductsInPromotion();
        setProductResponseDTOSSearchProductsByNameAndInStock();
        setMapFindAllGrouped();
        setProductResponseDTOSToComparisonInFindAllProducts();
        setProductRequestDTO();
    }

    @BeforeEach
    void definitionBehaviorsForMocks() {

        BDDMockito.when(this.productService.findAllProductsInStock())
                .thenReturn(mapFindAllProductsInStock);

        BDDMockito.when(this.productService.findProductsByTypeAndInStock(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.any(Type.class)))
                .thenReturn(new PageImpl<>(productResponseDTOSFindProductsByTypeAndInStock));

        BDDMockito.when(this.productService.findProductsInPromotionAndInStock())
                .thenReturn(mapFindProductsInPromotionAndInStock);

        BDDMockito.when(this.productService.findAllGrouped())
                .thenReturn(mapFindAllGrouped);

        BDDMockito.doNothing()
                .when(this.productService).updateProductStock(ArgumentMatchers.any(Long.class), ArgumentMatchers.anyBoolean());

        BDDMockito.doNothing()
                .when(this.productService).updatePriceOfProduct(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(BigDecimal.class));

        BDDMockito.doNothing()
                .when(this.productService).updatePriceRatingOfProduct(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(PriceRating.class));

        BDDMockito.doNothing()
                .when(this.productService).deleteProduct(ArgumentMatchers.any(Long.class));

        BDDMockito.when(this.productService.registerNewProduct(ArgumentMatchers.any(ProductRequestDTO.class), ArgumentMatchers.anyString()))
                .thenReturn(1L);

        BDDMockito.when(this.productService.searchProductsByNameAndInStock(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.anyString()))
                .thenReturn(new PageImpl<>(productResponseDTOSSearchProductsByNameAndInStock));
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
                .containsEntry(Type.DRINK, productResponseDTOSToComparisonInFindProductsInStock);
    }

    @Test
    void findByType_returnsAPageOfTheAllProductsOfAGivenTypeAndAStatusCodeOk_wheneverCalled() {

        Assertions.assertThatCode(() -> this.productController.findByType(Page.empty().getPageable(), Type.DRINK))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.productController.findByType(Page.empty().getPageable(), Type.DRINK))
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(new PageImpl<>(productResponseDTOSFindProductsByTypeAndInStock)));

        Assertions.assertThat(Objects.requireNonNull(this.productController.findByType(Page.empty().getPageable(), Type.DRINK).getBody()).toList())
                .asList()
                .hasSize(productResponseDTOSFindProductsByTypeAndInStock.size())
                .contains(productResponseDTOSFindProductsByTypeAndInStock.get(2));
    }

    @Test
    void findProductsInPromotion_returnsAMapOfTheAllProductsInPromotionOrderedByTypeAndAStatusCodeOk_wheneverCalled() {

        Assertions.assertThatCode(() -> this.productController.findProductsInPromotion())
                .doesNotThrowAnyException();

        Assertions.assertThat(this.productController.findProductsInPromotion())
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(mapFindProductsInPromotionAndInStock));

        Assertions.assertThat(this.productController.findProductsInPromotion().getBody())
                .containsEntry(Type.SWEET_ESFIHA, productResponseDTOSToComparisonInFindProductsInPromotion);
    }

    @Test
    void searchProducts_returnsAPageOfTheAllProductsInStockAndThatHaveACertainNameAndAStatusCodeOk_wheneverCalled() {

        Assertions.assertThatCode(()-> this.productController.searchProducts(Page.empty().getPageable(), "name"))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.productController.searchProducts(Page.empty().getPageable(), "name"))
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(new PageImpl<>(productResponseDTOSSearchProductsByNameAndInStock)));

        Assertions.assertThat(Objects.requireNonNull(this.productController.searchProducts(Page.empty().getPageable(), "name").getBody()).toList())
                .isNotNull()
                .asList()
                .hasSize(productResponseDTOSSearchProductsByNameAndInStock.size())
                .contains(productResponseDTOSSearchProductsByNameAndInStock.get(1));
    }

    @Test
    void findAllProducts_returnsAMapOfTheAllProductsOrderedByIsStocked_wheneverCalled() {

        Assertions.assertThatCode(() -> this.productController.findAllProducts())
                .doesNotThrowAnyException();

        Assertions.assertThat(this.productController.findAllProducts())
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(mapFindAllGrouped));

        Assertions.assertThat(this.productController.findAllProducts().getBody())
                .containsEntry(true, productResponseDTOSToComparisonInFindAllProducts);
    }

    @Test
    void updateStock_returnsAStatusCodeNoContent_whenThePassedIdIsValid() {

        Assertions.assertThatCode(() -> this.productController.updateStock(1L, false))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.productController.updateStock(1L, false))
                .isNotNull()
                .isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Test
    void updateStock_throwsResponseStatusException_whenThePassedIdIsInvalid() {

        BDDMockito.doThrow(ResponseStatusException.class)
                .when(this.productService).updateProductStock(ArgumentMatchers.any(Long.class), ArgumentMatchers.anyBoolean());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.productController.updateStock(2L, false));
    }

    @Test
    void updatePrice_returnsAStatusCodeNoContent_whenThePassedIdIsValid() {

        BigDecimal price = new BigDecimal("10.00");

        Assertions.assertThatCode(() -> this.productController.updatePrice(1L, price))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.productController.updatePrice(1L, price))
                .isNotNull()
                .isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Test
    void updatePrice_throwsResponseStatusException_whenThePassedIdIsInvalid() {

        BDDMockito.doThrow(ResponseStatusException.class)
                .when(this.productService).updatePriceOfProduct(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(BigDecimal.class));

        BigDecimal price = new BigDecimal("10");

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.productController.updatePrice(2L, price));
    }

    @Test
    void updatePriceRating_returnsAStatusCodeNoContent_whenThePassedIdIsValid() {

        Assertions.assertThatCode(() -> this.productController.updatePriceRating(1L, PriceRating.REGULAR_PRICE))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.productController.updatePriceRating(1L, PriceRating.REGULAR_PRICE))
                .isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Test
    void updatePriceRating_throwsResponseStatusException_whenThePassedIdIsInvalid() {

        BDDMockito.doThrow(ResponseStatusException.class)
                .when(this.productService).updatePriceRatingOfProduct(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(PriceRating.class));

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.productController.updatePriceRating(1L, PriceRating.REGULAR_PRICE));
    }

    @Test
    void delete_returnsAStatusCodeNoContent_whenThePassedIdIsValid() {

        Assertions.assertThatCode(() -> this.productController.delete(1L))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.productController.delete(1L))
                .isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Test
    void delete_throwsResponseStatusException_whenThePassedIdIsInvalid() {

        BDDMockito.doThrow(ResponseStatusException.class)
                .when(this.productService).deleteProduct(ArgumentMatchers.any(Long.class));

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.productController.delete(1L));
    }

    @Test
    void registerProduct_returnsAIdOfTheProductThatWasSavedAndAStatusCodeCreated_whenTheProductIsNotRegistered() {

        Assertions.assertThatCode(() -> this.productController.registerProduct(productRequestDTO, "pt-BR"))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.productController.registerProduct(productRequestDTO, "pt-BR"))
                .isEqualTo(new ResponseEntity<>(1L, HttpStatus.CREATED));
    }

    @Test
    void registerProduct_throwsExistingProductException_whenTheProductIsAlreadyRegistered() {

        BDDMockito.when(this.productService.registerNewProduct(ArgumentMatchers.any(ProductRequestDTO.class), ArgumentMatchers.anyString()))
                .thenThrow(ExistingProductException.class);

        Assertions.assertThatExceptionOfType(ExistingProductException.class)
                .isThrownBy(() -> this.productController.registerProduct(productRequestDTO, "pt-BR"));
    }
}