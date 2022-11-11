package com.webservicepizzariazeff.www.integration;

import com.webservicepizzariazeff.www.domain.PriceRating;
import com.webservicepizzariazeff.www.domain.Product;
import com.webservicepizzariazeff.www.domain.Type;
import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.request.ProductRequestDTO;
import com.webservicepizzariazeff.www.dto.response.ProductResponseDTO;
import com.webservicepizzariazeff.www.exception_handler.ExistingProductExceptionHandler;
import com.webservicepizzariazeff.www.integration.mapper.MapperToIntegration;
import com.webservicepizzariazeff.www.integration.wrapper.PageableResponse;
import com.webservicepizzariazeff.www.repository.ProductRepository;
import com.webservicepizzariazeff.www.repository.UserRepository;
import com.webservicepizzariazeff.www.util.Mapper;
import org.assertj.core.api.Assertions;
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
class ProductControllerIT {

    @Autowired
    @Qualifier(value = "testRestTemplateNoCredentialsToProductControllerIT")
    private TestRestTemplate testRestTemplateNoCredentials;

    @Autowired
    @Qualifier(value = "testRestTemplateAdminToProductControllerIT")
    private TestRestTemplate testRestTemplateAdmin;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private static List<Product> productsFindProductsInStock;

    private static List<ProductResponseDTO> productResponseDTOSToComparisonInFindProductsInStock;

    private static List<Product> productsFindByType;

    private static List<ProductResponseDTO> productResponseDTOSToComparisonInFindByType;

    private static List<Product> productsFindProductsInPromotion;

    private static List<ProductResponseDTO> productResponseDTOSToComparisonInFindProductsInPromotion;

    private static List<Product> productsSearchProducts;

    private static List<ProductResponseDTO> productResponseDTOSToComparisonInSearchProducts;

    private HttpHeaders httpHeaders;

    private static final String[] acceptLanguage = {"Accept-Language", "pt-BR"};

    private static User userAdmin;

    private static List<Product> productsFindAllProducts;

    private static List<ProductResponseDTO> productResponseDTOSToComparisonInFindAllProducts;

    private static Product productUpdateStock;

    private static Product productUpdatePrice;

    private static Product productUpdatePriceRating;

    private static Product productDelete;

    private static ProductRequestDTO productRequestDTORegisterProduct;

    private static Product productRegisterProduct;

    private static ExistingProductExceptionHandler existingProductExceptionHandlerToComparisonInRegisterProduct;

    static void setProductsFindProductsInStock() {

        productsFindProductsInStock = List.of(
                Product.ProductBuilder.builder()
                        .id(1L)
                        .name("name1")
                        .description("description1")
                        .price(new BigDecimal("10.00"))
                        .type(Type.DRINK)
                        .priceRating(PriceRating.REGULAR_PRICE)
                        .isStocked(true)
                        .image("/image1")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(2L)
                        .name("name2")
                        .description("description2")
                        .price(new BigDecimal("20.00"))
                        .type(Type.SWEET_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(true)
                        .image("/image2")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(3L)
                        .name("name3")
                        .description("description3")
                        .price(new BigDecimal("30.00"))
                        .type(Type.SALTY_ESFIHA)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(true)
                        .image("/image3")
                        .build()
        );
    }

    static void setProductResponseDTOSToComparisonInFindProductsInStock() {

        productResponseDTOSToComparisonInFindProductsInStock = productsFindProductsInStock.stream()
                .filter(product -> product.getType() == Type.SWEET_PIZZA)
                .sorted(Comparator.comparing(Product::getName))
                .map(Mapper::fromProductToProductResponseDTO)
                .toList();
    }

    static void setProductsFindByType() {

        productsFindByType = List.of(
                Product.ProductBuilder.builder()
                        .id(1L)
                        .name("name1")
                        .description("description1")
                        .price(new BigDecimal("10.00"))
                        .type(Type.SWEET_PIZZA)
                        .priceRating(PriceRating.REGULAR_PRICE)
                        .isStocked(true)
                        .image("/image1")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(2L)
                        .name("name2")
                        .description("description2")
                        .price(new BigDecimal("20.00"))
                        .type(Type.SWEET_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(true)
                        .image("/image2")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(3L)
                        .name("name3")
                        .description("description3")
                        .price(new BigDecimal("30.00"))
                        .type(Type.SWEET_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(true)
                        .image("/image3")
                        .build()
        );
    }

    static void setProductResponseDTOSToComparisonInFindByType() {

        productResponseDTOSToComparisonInFindByType = productsFindByType.stream()
                .filter(product -> product.getType() == Type.SWEET_PIZZA)
                .map(Mapper::fromProductToProductResponseDTO)
                .toList();
    }

    static void setProductsFindProductsInPromotion() {

        productsFindProductsInPromotion = List.of(
                Product.ProductBuilder.builder()
                        .id(1L)
                        .name("name1")
                        .description("description1")
                        .price(new BigDecimal("10.00"))
                        .type(Type.SWEET_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(true)
                        .image("/image1")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(2L)
                        .name("name2")
                        .description("description2")
                        .price(new BigDecimal("20.00"))
                        .type(Type.SWEET_ESFIHA)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(true)
                        .image("/image2")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(3L)
                        .name("name3")
                        .description("description3")
                        .price(new BigDecimal("30.00"))
                        .type(Type.DRINK)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(true)
                        .image("/image3")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(4L)
                        .name("name4")
                        .description("description4")
                        .price(new BigDecimal("40.00"))
                        .type(Type.SWEET_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(true)
                        .image("/image4")
                        .build()
        );
    }

    static void setProductResponseDTOSToComparisonInFindProductsInPromotion() {

        productResponseDTOSToComparisonInFindProductsInPromotion = productsFindProductsInPromotion.stream()
                .filter(product -> product.getType() == Type.SWEET_PIZZA)
                .map(Mapper::fromProductToProductResponseDTO)
                .sorted(Comparator.comparing(ProductResponseDTO::getName))
                .toList();
    }

    static void setProductsSearchProducts() {

        productsSearchProducts = List.of(
                Product.ProductBuilder.builder()
                        .id(1L)
                        .name("name1")
                        .description("description1")
                        .price(new BigDecimal("10.00"))
                        .type(Type.DRINK)
                        .priceRating(PriceRating.REGULAR_PRICE)
                        .isStocked(true)
                        .image("/image1")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(2L)
                        .name("name2")
                        .description("description2")
                        .price(new BigDecimal("20.00"))
                        .type(Type.SWEET_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(true)
                        .image("/image2")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(3L)
                        .name("name3")
                        .description("description3")
                        .price(new BigDecimal("30.00"))
                        .type(Type.SALTY_ESFIHA)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(true)
                        .image("/image3")
                        .build()
        );
    }

    static void setProductResponseDTOSToComparisonInSearchProducts() {

        productResponseDTOSToComparisonInSearchProducts = productsSearchProducts.stream()
                .map(Mapper::fromProductToProductResponseDTO)
                .toList();
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

    static void setProductsFindAllProducts() {

        productsFindAllProducts = List.of(
                Product.ProductBuilder.builder()
                        .id(1L)
                        .name("name1")
                        .description("description1")
                        .price(new BigDecimal("10.00"))
                        .type(Type.SWEET_PIZZA)
                        .priceRating(PriceRating.REGULAR_PRICE)
                        .isStocked(true)
                        .image("/image1")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(2L)
                        .name("name2")
                        .description("description2")
                        .price(new BigDecimal("20.00"))
                        .type(Type.SWEET_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(true)
                        .image("/image2")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(3L)
                        .name("name3")
                        .description("description3")
                        .price(new BigDecimal("30.00"))
                        .type(Type.SALTY_ESFIHA)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(true)
                        .image("/image3")
                        .build()
        );
    }

    static void setProductResponseDTOSToComparisonInFindAllProducts() {

        productResponseDTOSToComparisonInFindAllProducts = productsFindAllProducts.stream()
                .map(Mapper::fromProductToProductResponseDTO)
                .sorted(Comparator.comparing(ProductResponseDTO::getPriceRating))
                .filter(ProductResponseDTO::isStocked)
                .toList();
    }

    static void setProductUpdateStock() {

        productUpdateStock = Product.ProductBuilder.builder()
                .id(1L)
                .name("name1")
                .description("description1")
                .price(new BigDecimal("10.00"))
                .type(Type.SWEET_PIZZA)
                .priceRating(PriceRating.REGULAR_PRICE)
                .isStocked(true)
                .image("/image1")
                .build();
    }

    static void setProductUpdatePrice() {

        productUpdatePrice = Product.ProductBuilder.builder()
                .id(1L)
                .name("name1")
                .description("description1")
                .price(new BigDecimal("10.00"))
                .type(Type.SWEET_PIZZA)
                .priceRating(PriceRating.REGULAR_PRICE)
                .isStocked(true)
                .image("/image1")
                .build();
    }

    static void setProductUpdatePriceRating() {

        productUpdatePriceRating = Product.ProductBuilder.builder()
                .id(1L)
                .name("name1")
                .description("description1")
                .price(new BigDecimal("10.00"))
                .type(Type.SWEET_PIZZA)
                .priceRating(PriceRating.REGULAR_PRICE)
                .isStocked(true)
                .image("/image1")
                .build();
    }

    static void setProductDelete() {

        productDelete = Product.ProductBuilder.builder()
                .id(1L)
                .name("name1")
                .description("description1")
                .price(new BigDecimal("10.00"))
                .type(Type.SALTY_ESFIHA)
                .priceRating(PriceRating.PROMOTION)
                .isStocked(true)
                .image("/image1")
                .build();
    }

    static void setProductRequestDTORegisterProduct() {

        productRequestDTORegisterProduct = ProductRequestDTO.ProductRequestDTOBuilder.builder()
                .name("name1")
                .description("description1")
                .price(new BigDecimal("10.00"))
                .type(Type.DRINK)
                .priceRating(PriceRating.REGULAR_PRICE)
                .build();
    }

    static void setProductRegisterProduct() {

        productRegisterProduct = Product.ProductBuilder.builder()
                .id(1L)
                .name(productRequestDTORegisterProduct.getName())
                .description(productRequestDTORegisterProduct.getDescription())
                .price(productRequestDTORegisterProduct.getPrice())
                .type(productRequestDTORegisterProduct.getType())
                .priceRating(productRequestDTORegisterProduct.getPriceRating())
                .isStocked(true)
                .image("image1")
                .build();
    }

    static void setExistingProductExceptionHandlerToComparisonInRegisterProduct() {

        existingProductExceptionHandlerToComparisonInRegisterProduct = ExistingProductExceptionHandler.
                ExistingProductExceptionHandlerBuilder.builder()
                .message("Este produto já foi cadastrado antes.")
                .build();
    }

    @BeforeAll
    static void initializeObjects() {

        setProductsFindProductsInStock();
        setProductResponseDTOSToComparisonInFindProductsInStock();
        setProductsFindByType();
        setProductResponseDTOSToComparisonInFindByType();
        setProductsFindProductsInPromotion();
        setProductResponseDTOSToComparisonInFindProductsInPromotion();
        setProductsSearchProducts();
        setProductResponseDTOSToComparisonInSearchProducts();
        setUserAdmin();
        setProductsFindAllProducts();
        setProductResponseDTOSToComparisonInFindAllProducts();
        setProductUpdateStock();
        setProductUpdatePrice();
        setProductUpdatePriceRating();
        setProductDelete();
        setProductRequestDTORegisterProduct();
        setProductRegisterProduct();
        setExistingProductExceptionHandlerToComparisonInRegisterProduct();
    }

    @Test
    void findProductsInStock_returnsAllProductsInStockGroupedByTypeAndAStatusCode200_wheneverCalled() {

        productsFindProductsInStock.forEach(
                product -> this.productRepository.save(product)
        );

        ResponseEntity<Map<Type, List<ProductResponseDTO>>> allProductsGroupedByType = this.testRestTemplateNoCredentials.exchange(
                "/products/find-products",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        Assertions.assertThat(allProductsGroupedByType.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.OK);

        Assertions.assertThat(allProductsGroupedByType.getBody())
                .isNotNull()
                .containsEntry(Type.SWEET_PIZZA, productResponseDTOSToComparisonInFindProductsInStock);
    }

    @Test
    void findByType_returnsAPageOfTheAllProductsOfAGivenTypeAndAStatusCode200_wheneverCalled() {

        productsFindByType.forEach(
                product -> this.productRepository.save(product)
        );

        ResponseEntity<PageableResponse<ProductResponseDTO>> allProductsOfAGivenType = this.testRestTemplateNoCredentials.exchange(
                "/products/find-by-type?type=SWEET_PIZZA",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(allProductsOfAGivenType.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.OK);

        Assertions.assertThat(Objects.requireNonNull(allProductsOfAGivenType.getBody()).toList())
                .isNotNull()
                .asList()
                .hasSize(productResponseDTOSToComparisonInFindByType.size())
                .isEqualTo(productResponseDTOSToComparisonInFindByType)
                .contains(productResponseDTOSToComparisonInFindByType.get(0));
    }

    @Test
    void findProductsInPromotion_returnsAllProductsInPromotionGroupedByTypeAndAStatusCode200_wheneverCalled() {

        productsFindProductsInPromotion.forEach(
                product -> this.productRepository.save(product)
        );

        ResponseEntity<Map<Type, List<ProductResponseDTO>>> allProductsInPromotionGroupedByType = this.testRestTemplateNoCredentials.exchange(
                "/products/find-promotions",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        Assertions.assertThat(allProductsInPromotionGroupedByType.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.OK);

        Assertions.assertThat(allProductsInPromotionGroupedByType.getBody())
                .isNotNull()
                .containsEntry(Type.SWEET_PIZZA, productResponseDTOSToComparisonInFindProductsInPromotion);
    }

    @Test
    void searchProducts_returnsAPageOfTheProductOfAGivenNameAndAStatusCode200_wheneverCalled() {

        productsSearchProducts.forEach(
                product -> this.productRepository.save(product)
        );

        ResponseEntity<PageableResponse<ProductResponseDTO>> allProductsWithAGivenName = this.testRestTemplateNoCredentials.exchange(
                "/products/search?name=name",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        Assertions.assertThat(allProductsWithAGivenName.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.OK);

        Assertions.assertThat(Objects.requireNonNull(allProductsWithAGivenName.getBody()).toList())
                .isNotNull()
                .asList()
                .hasSize(productResponseDTOSToComparisonInSearchProducts.size())
                .isEqualTo(productResponseDTOSToComparisonInSearchProducts)
                .contains(productResponseDTOSToComparisonInSearchProducts.get(0));
    }

    private HttpHeaders getHeaders() {

        ResponseEntity<Void> responseEntityWithCsrfToken = this.testRestTemplateNoCredentials.exchange(
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
    void findAllProducts_returnsAllProductsGroupedByIsStockAndAStatusCode200_wheneverCalled() {

        this.userRepository.save(userAdmin);

        productsFindAllProducts.forEach(
                product -> this.productRepository.save(product)
        );

        this.httpHeaders = getHeaders();

        ResponseEntity<Map<Boolean, List<ProductResponseDTO>>> allProductsGroupedByIsStock = this.testRestTemplateAdmin.exchange(
                "/products/admin/find-all",
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                new ParameterizedTypeReference<>() {
                });

        Assertions.assertThat(allProductsGroupedByIsStock.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.OK);

        Assertions.assertThat(allProductsGroupedByIsStock.getBody())
                .isNotNull()
                .containsEntry(true, productResponseDTOSToComparisonInFindAllProducts);
    }

    @Test
    void updateStock_updateStockOfAProductAndReturnsAStatusCode204_whenThePassedIdExistsInDatabase() {

        this.userRepository.save(userAdmin);
        this.productRepository.save(productUpdateStock);

        this.httpHeaders = getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateAdmin.exchange(
                "/products/admin/stock/1/false",
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
    void updateStock_returnsAStatusCode400_whenThePassedIdDoesNotExistInTheDatabase() {

        this.userRepository.save(userAdmin);

        this.httpHeaders = getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateAdmin.exchange(
                "/products/admin/stock/2/true",
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
    void updatePrice_updatePriceOfAProductAndReturnsAStatusCode204_whenThePassedIdExistsInDatabase() {

        this.userRepository.save(userAdmin);
        this.productRepository.save(productUpdatePrice);

        this.httpHeaders = getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateAdmin.exchange(
                "/products/admin/price/1/20.00",
                HttpMethod.PUT,
                new HttpEntity<>(httpHeaders),
                new ParameterizedTypeReference<>() {
                });

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void updatePrice_returnsAStatusCode400_whenThePassedIdDoesNotExistInTheDatabase() {

        this.userRepository.save(userAdmin);

        this.httpHeaders = getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateAdmin.exchange(
                "/products/admin/price/1/20.00",
                HttpMethod.PUT,
                new HttpEntity<>(httpHeaders),
                new ParameterizedTypeReference<>() {
                });

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void updatePriceRating_updatePriceRatingOfAProductAndReturnsAStatusCode204_whenThePassedIdExistsInDatabase() {

        this.userRepository.save(userAdmin);
        this.productRepository.save(productUpdatePriceRating);

        this.httpHeaders = getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateAdmin.exchange(
                "/products/admin/rating/1/PROMOTION",
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
    void updatePriceRating_returnsAStatusCode400_whenThePassedIdDoesNotExistInTheDatabase() {

        this.userRepository.save(userAdmin);

        this.httpHeaders = getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateAdmin.exchange(
                "/products/admin/rating/2/PROMOTION",
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
    void delete_deleteAProductAndReturnsAStatusCode204_whenThePassedIdExistsInDatabase() {

        this.userRepository.save(userAdmin);
        this.productRepository.save(productDelete);

        this.httpHeaders = getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateAdmin.exchange(
                "/products/admin/delete/1",
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
    void delete_returnsAStatusCode400_whenThePassedIdDoesNotExistInTheDatabase() {

        this.userRepository.save(userAdmin);

        this.httpHeaders = getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplateAdmin.exchange(
                "/products/admin/delete/2",
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
    void registerProduct_registersANewProductAndReturnsAStatusCode201_whenTheProductIsNotRegistered() {

        this.userRepository.save(userAdmin);

        this.httpHeaders = getHeaders();

        ResponseEntity<Long> idOfTheProductThatWasCreated = this.testRestTemplateAdmin.exchange(
                "/products/admin/register",
                HttpMethod.PUT,
                new HttpEntity<>(productRequestDTORegisterProduct, this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(idOfTheProductThatWasCreated.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(idOfTheProductThatWasCreated.getBody())
                .isNotNull()
                .isEqualTo(1L);
    }

    @Test
    void registerProduct_returnsAHandlerObjectAndAStatusCode409_whenTheProductIsAlreadyRegisteredInTheSystem() {

        this.userRepository.save(userAdmin);
        this.productRepository.save(productRegisterProduct);

        this.httpHeaders = getHeaders();

        ResponseEntity<ExistingProductExceptionHandler> responseEntity = this.testRestTemplateAdmin.exchange(
                "/products/admin/register",
                HttpMethod.PUT,
                new HttpEntity<>(productRequestDTORegisterProduct, this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.CONFLICT);

        Assertions.assertThat(responseEntity.getBody())
                .isNotNull()
                .isEqualTo(existingProductExceptionHandlerToComparisonInRegisterProduct);
    }

    @Test
    void registerProduct_returnsAStatusCode400_whenThePassedAcceptLanguageIsInvalid() {

        ResponseEntity<Void> responseEntityWithCsrfToken = this.testRestTemplateNoCredentials.exchange(
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

        ResponseEntity<Void> idOfTheProductThatWasCreated = this.testRestTemplateAdmin.exchange(
                "/products/admin/register",
                HttpMethod.PUT,
                new HttpEntity<>(productRequestDTORegisterProduct, this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(idOfTheProductThatWasCreated.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @TestConfiguration
    @Lazy
    static class Config {

        @Bean(name = "testRestTemplateNoCredentialsToProductControllerIT")
        public TestRestTemplate testRestTemplateNoCredentials(@Value("${local.server.port}") int port) {

            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port);

            return new TestRestTemplate(restTemplateBuilder);
        }

        @Bean(name = "testRestTemplateAdminToProductControllerIT")
        public TestRestTemplate testRestTemplateAdmin(@Value("${local.server.port}") int port) {

            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("name-admin@gmail.com", "password-admin");

            return new TestRestTemplate(restTemplateBuilder);
        }
    }
}
