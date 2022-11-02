package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.PriceRating;
import com.webservicepizzariazeff.www.domain.Product;
import com.webservicepizzariazeff.www.domain.Type;
import com.webservicepizzariazeff.www.dto.response.ProductResponseDTO;
import com.webservicepizzariazeff.www.repository.ProductRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private static List<Product> productsFindAll;

    private static List<Product> productsFindByIsStocked;

    private static List<ProductResponseDTO> productResponseDTOToComparisonInFindByIsStocked;

    private static List<Product> productsFindByTypeAndIsStocked;

    private static List<ProductResponseDTO> productResponseDTOToComparisonInFindByTypeAndIsStocked;

    private static List<Product> productsFindByPriceRatingAndIsStocked;

    private static List<ProductResponseDTO> productResponseDTOToComparisonInFindByPriceRatingAndIsStocked;

    static void setProductsFindAll() {

        productsFindAll = new ArrayList<>(List.of(
                Product.ProductBuilder.builder()
                        .id(1L)
                        .name("name1")
                        .description("description1")
                        .price(new BigDecimal("10"))
                        .type(Type.SALTY_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(true)
                        .image("/image1.png")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(2L)
                        .name("name2")
                        .description("description2")
                        .price(new BigDecimal("20"))
                        .type(Type.SWEET_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(false)
                        .image("/image2.png")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(3L)
                        .name("name3")
                        .description("description3")
                        .price(new BigDecimal("30"))
                        .type(Type.SWEET_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(false)
                        .image("/image3.png")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(4L)
                        .name("name4")
                        .description("description4")
                        .price(new BigDecimal("40"))
                        .type(Type.SALTY_ESFIHA)
                        .priceRating(PriceRating.REGULAR_PRICE)
                        .isStocked(false)
                        .image("/image4.png")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(5L)
                        .name("name5")
                        .description("description5")
                        .price(new BigDecimal("50"))
                        .type(Type.SALTY_ESFIHA)
                        .priceRating(PriceRating.REGULAR_PRICE)
                        .isStocked(true)
                        .image("/image5.png")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(6L)
                        .name("name6")
                        .description("description6")
                        .price(new BigDecimal("60"))
                        .type(Type.SALTY_ESFIHA)
                        .priceRating(PriceRating.REGULAR_PRICE)
                        .isStocked(true)
                        .image("/image6.png")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(7L)
                        .name("name7")
                        .description("description7")
                        .price(new BigDecimal("70"))
                        .type(Type.SALTY_ESFIHA)
                        .priceRating(PriceRating.REGULAR_PRICE)
                        .isStocked(true)
                        .image("/image7.png")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(8L)
                        .name("name8")
                        .description("description8")
                        .price(new BigDecimal("80"))
                        .type(Type.SALTY_ESFIHA)
                        .priceRating(PriceRating.REGULAR_PRICE)
                        .isStocked(true)
                        .image("/image8.png")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(9L)
                        .name("name9")
                        .description("description9")
                        .price(new BigDecimal("90"))
                        .type(Type.DRINK)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(true)
                        .image("/image9.png")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(10L)
                        .name("name10")
                        .description("description10")
                        .price(new BigDecimal("100"))
                        .type(Type.SWEET_ESFIHA)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(true)
                        .image("/image10.png")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(11L)
                        .name("name11")
                        .description("description11")
                        .price(new BigDecimal("110"))
                        .type(Type.SWEET_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(true)
                        .image("/image11.png")
                        .build()
        ));
    }

    static void setProductsFindByIsStocked() {

        productsFindByIsStocked = productsFindAll.stream()
                .filter(Product::isStocked)
                .toList();
    }

    static void setProductResponseDTOToComparisonInFindByIsStocked(){

        productResponseDTOToComparisonInFindByIsStocked = productsFindByIsStocked.stream()
                .filter(product -> product.getType() == Type.SALTY_PIZZA)
                .map(Mapper::ofTheProductToProductResponseDTO)
                .toList();

    }

    static void setProductsFindByTypeAndIsStocked() {

        productsFindByTypeAndIsStocked = productsFindAll.stream()
                .filter(Product::isStocked)
                .filter(product -> product.getType() == Type.SALTY_ESFIHA)
                .toList();
    }

    static void setProductResponseDTOToComparisonInFindByTypeAndIsStocked() {

        productResponseDTOToComparisonInFindByTypeAndIsStocked = productsFindByTypeAndIsStocked.stream()
                .map(Mapper::ofTheProductToProductResponseDTO)
                .toList();
    }

    static void setProductsFindByPriceRatingAndIsStocked() {

        productsFindByPriceRatingAndIsStocked = productsFindAll.stream()
                .filter(Product::isStocked)
                .filter(product -> product.getPriceRating() == PriceRating.PROMOTION)
                .toList();
    }

    static void setProductResponseDTOToComparisonInFindByPriceRatingAndIsStocked(){

        productResponseDTOToComparisonInFindByPriceRatingAndIsStocked = productsFindByPriceRatingAndIsStocked.stream()
                .filter(product -> product.getType() == Type.SWEET_PIZZA)
                .map(Mapper::ofTheProductToProductResponseDTO)
                .toList();
    }

    @BeforeAll
    static void initializeObjects() {

        setProductsFindAll();
        setProductsFindByIsStocked();
        setProductResponseDTOToComparisonInFindByIsStocked();
        setProductsFindByTypeAndIsStocked();
        setProductResponseDTOToComparisonInFindByTypeAndIsStocked();
        setProductsFindByPriceRatingAndIsStocked();
        setProductResponseDTOToComparisonInFindByPriceRatingAndIsStocked();
    }

    @BeforeEach
    void definitionBehaviorsForMocks() {

        BDDMockito.when(this.productRepository.findAll())
                .thenReturn(productsFindAll);

        BDDMockito.when(this.productRepository.findByIsStocked(ArgumentMatchers.anyBoolean()))
                .thenReturn(productsFindByIsStocked);

        BDDMockito.when(this.productRepository.findByTypeAndIsStocked(
                        ArgumentMatchers.any(Pageable.class),
                        ArgumentMatchers.any(Type.class),
                        ArgumentMatchers.anyBoolean())
                )
                .thenReturn(new PageImpl<>(productsFindByTypeAndIsStocked));

        BDDMockito.when(this.productRepository.findByPriceRatingAndIsStocked(
                        ArgumentMatchers.any(PriceRating.class),
                        ArgumentMatchers.anyBoolean())
                )
                .thenReturn(productsFindByPriceRatingAndIsStocked);
    }

    @Test
    void findAllNonPageable_returnsAListOfAllProductsInTheDatabase_wheneverCalled() {

        Assertions.assertThatCode(() -> this.productService.findAllNonPageable())
                .doesNotThrowAnyException();

        Assertions.assertThat(this.productService.findAllNonPageable())
                .isNotNull()
                .asList()
                .isEqualTo(productsFindAll)
                .contains(productsFindAll.get(0));
    }

    @Test
    void findAllProductsInStock_returnsAMapOfTheAllProductsWithStockOrderedByType_wheneverCalled() {

        Assertions.assertThatCode(() -> this.productService.findAllProductsInStock())
                .doesNotThrowAnyException();

        Assertions.assertThat(this.productService.findAllProductsInStock())
                .isNotNull()
                .containsEntry(Type.SALTY_PIZZA, productResponseDTOToComparisonInFindByIsStocked);
    }

    @Test
    void findProductsByTypeAndInStock_returnsAPageOfTheAllProductsOfAGivenType_whenThePassedTypeIsValid() {

        Assertions.assertThatCode(() -> this.productService.findProductsByTypeAndInStock(Page.empty().getPageable(), "SALTY_ESFIHA"))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.productService.findProductsByTypeAndInStock(Page.empty().getPageable(), "SALTY_ESFIHA").toList())
                .isNotNull()
                .asList()
                .isEqualTo(productResponseDTOToComparisonInFindByTypeAndIsStocked)
                .contains(productResponseDTOToComparisonInFindByTypeAndIsStocked.get(0));
    }

    @Test
    void findProductsByTypeAndInStock_returnsAPageWithNoProducts_whenTheTypeParameterIsNotEqualToTheNameOfOneOfTheTypeEnumerations() {

        Assertions.assertThatCode(() -> this.productService.findProductsByTypeAndInStock(Page.empty().getPageable(), "SALTY"))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.productService.findProductsByTypeAndInStock(Page.empty().getPageable(), "SALTY"))
                .isEqualTo(Page.empty());
    }

    @Test
    void findProductsInPromotionAndInStock_returnsAMapOfTheAllProductsInPromotionAndInStockOrderedByType_wheneverCalled() {

        Assertions.assertThatCode(()-> this.productService.findProductsInPromotionAndInStock())
                .doesNotThrowAnyException();

        Assertions.assertThat(this.productService.findProductsInPromotionAndInStock())
                .isNotNull()
                .containsEntry(Type.SWEET_PIZZA, productResponseDTOToComparisonInFindByPriceRatingAndIsStocked);
    }
}