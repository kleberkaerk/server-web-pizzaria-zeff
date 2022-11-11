package com.webservicepizzariazeff.www.integration;

import com.webservicepizzariazeff.www.domain.*;
import com.webservicepizzariazeff.www.dto.request.CardRequestDTO;
import com.webservicepizzariazeff.www.dto.request.FormOfPaymentDTO;
import com.webservicepizzariazeff.www.dto.request.SaleRequestDTO;
import com.webservicepizzariazeff.www.exception_handler.InvalidCardExceptionHandler;
import com.webservicepizzariazeff.www.integration.mapper.MapperToIntegration;
import com.webservicepizzariazeff.www.repository.AddressRepository;
import com.webservicepizzariazeff.www.repository.ProductRepository;
import com.webservicepizzariazeff.www.repository.UserRepository;
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
import java.util.List;
import java.util.Objects;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.jpa.hibernate.ddl-auto=update", "spring.flyway.enabled=false"}
)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SaleControllerIT {

    @Autowired
    @Qualifier("testRestTemplateUserToSaleControllerIT")
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProductRepository productRepository;

    private HttpHeaders httpHeaders;

    private static final String[] acceptLanguage = {"Accept-Language", "pt-BR"};

    private static User user;

    private static Address address;

    private static List<Product> products;

    private static SaleRequestDTO saleRequestDTOSale;

    private static InvalidCardExceptionHandler invalidCardExceptionHandlerToComparisonInSale;

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
                .road("road1")
                .district("district1")
                .city("city1")
                .state("state1")
                .user(user)
                .build();
    }

    static void setProducts() {

        products = List.of(
                Product.ProductBuilder.builder()
                        .id(1L)
                        .name("name1")
                        .description("description1")
                        .price(new BigDecimal("10.00"))
                        .type(Type.DRINK)
                        .priceRating(PriceRating.REGULAR_PRICE)
                        .isStocked(true)
                        .image("image1")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(2L)
                        .name("name2")
                        .description("description2")
                        .price(new BigDecimal("20.00"))
                        .type(Type.SWEET_ESFIHA)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(true)
                        .image("image2")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(3L)
                        .name("name3")
                        .description("description3")
                        .price(new BigDecimal("30.00"))
                        .type(Type.SWEET_PIZZA)
                        .priceRating(PriceRating.PROMOTION)
                        .isStocked(true)
                        .image("image3")
                        .build()
        );
    }

    static void setSaleRequestDTOSale() {

        CardRequestDTO cardRequestDTO = CardRequestDTO.CardRequestDTOBuilder.builder()
                .nameOfCardHolder("nameOfCardHolder")
                .cardNumber("1234567890123456")
                .dueDate("12/34")
                .securityCode("1234")
                .formOfPaymentDTO(FormOfPaymentDTO.CREDIT)
                .build();

        saleRequestDTOSale = SaleRequestDTO.SaleRequestDTOBuilder.builder()
                .productsId(List.of(1L, 2L, 3L))
                .addressId(1L)
                .cardRequestDTO(cardRequestDTO)
                .isPaymentThroughTheWebsite(true)
                .build();
    }

    static void setInvalidCardExceptionHandlerToComparisonInSale() {

        invalidCardExceptionHandlerToComparisonInSale = InvalidCardExceptionHandler.
                InvalidCardExceptionHandlerBuilder.builder()
                .message("Cartão inválido.")
                .build();
    }

    @BeforeAll
    static void initializeObjects() {

        setUser();
        setAddress();
        setProducts();
        setSaleRequestDTOSale();
        setInvalidCardExceptionHandlerToComparisonInSale();
    }

    private HttpHeaders getHeaders() {

        ResponseEntity<Void> responseEntityWithCsrfToken = this.testRestTemplate.exchange(
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
    void sale_simulatesASaleAndReturnsAStatusCode201_whenTheSaleDTOIsValid() {

        this.userRepository.save(user);
        this.addressRepository.save(address);
        products.forEach(productRepository::save);

        this.httpHeaders = getHeaders();

        ResponseEntity<Long> idOfThePurchaseThatWasCreated = this.testRestTemplate.exchange(
                "/sales/sale",
                HttpMethod.POST,
                new HttpEntity<>(saleRequestDTOSale, this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(idOfThePurchaseThatWasCreated.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(idOfThePurchaseThatWasCreated.getBody())
                .isNotNull()
                .isEqualTo(1L);
    }

    @Test
    void sale_returnsAStatus404_whenTheUserNotHaveARegisteredAddress() {

        this.userRepository.save(user);
        products.forEach(productRepository::save);

        this.httpHeaders = getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplate.exchange(
                "/sales/sale",
                HttpMethod.POST,
                new HttpEntity<>(saleRequestDTOSale, this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void sale_returnsAHandlerObjectAndAStatusCode404_whenSaleRequestDTOHasANullCardRequestDTOAndPaymentThroughTheWebsiteEqualToTrue() {

        SaleRequestDTO saleRequestDTOSaleThatWitchWillReturnAStatusCode404 = SaleRequestDTO.SaleRequestDTOBuilder.builder()
                .productsId(List.of(1L, 2L, 3L))
                .addressId(1L)
                .cardRequestDTO(null)
                .isPaymentThroughTheWebsite(true)
                .build();

        this.userRepository.save(user);
        this.addressRepository.save(address);
        products.forEach(productRepository::save);

        this.httpHeaders = getHeaders();

        ResponseEntity<InvalidCardExceptionHandler> responseEntity = this.testRestTemplate.exchange(
                "/sales/sale",
                HttpMethod.POST,
                new HttpEntity<>(saleRequestDTOSaleThatWitchWillReturnAStatusCode404, this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.NOT_FOUND);

        Assertions.assertThat(responseEntity.getBody())
                .isNotNull()
                .isEqualTo(invalidCardExceptionHandlerToComparisonInSale);
    }

    @Test
    void sale_returnsAHandlerObjectAndAStatusCode404_whenSaleRequestDTOHasACardRequestDTOWithInvalidFields() {

        CardRequestDTO cardRequestDTO = CardRequestDTO.CardRequestDTOBuilder.builder()
                .nameOfCardHolder("12345")
                .cardNumber("abcdef")
                .dueDate("12/345")
                .securityCode("12345")
                .formOfPaymentDTO(FormOfPaymentDTO.CREDIT)
                .build();

        SaleRequestDTO saleRequestDTOSaleWithInvalidFields = SaleRequestDTO.SaleRequestDTOBuilder.builder()
                .productsId(List.of(1L, 2L, 3L))
                .addressId(1L)
                .cardRequestDTO(cardRequestDTO)
                .isPaymentThroughTheWebsite(true)
                .build();

        this.userRepository.save(user);
        this.addressRepository.save(address);
        products.forEach(productRepository::save);

        this.httpHeaders = getHeaders();

        ResponseEntity<InvalidCardExceptionHandler> responseEntity = this.testRestTemplate.exchange(
                "/sales/sale",
                HttpMethod.POST,
                new HttpEntity<>(saleRequestDTOSaleWithInvalidFields, this.httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.NOT_FOUND);

        Assertions.assertThat(responseEntity.getBody())
                .isNotNull()
                .isEqualTo(invalidCardExceptionHandlerToComparisonInSale);
    }

    @Test
    void sale_returnsAStatusCode400_whenThePassedAcceptLanguageIsInvalid() {

        ResponseEntity<Void> responseEntityWithCsrfToken = this.testRestTemplate.exchange(
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

        this.httpHeaders.add(acceptLanguage[0], "b");
        this.httpHeaders.add("Cookie", "XSRF-TOKEN=" + csrfToken);
        this.httpHeaders.add("X-XSRF-TOKEN", csrfToken);

        this.userRepository.save(user);
        this.addressRepository.save(address);
        products.forEach(productRepository::save);

        ResponseEntity<Void> responseEntity = this.testRestTemplate.exchange(
                "/sales/sale",
                HttpMethod.POST,
                new HttpEntity<>(saleRequestDTOSale, this.httpHeaders),
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

        @Bean(name = "testRestTemplateUserToSaleControllerIT")
        public TestRestTemplate testRestTemplateUser(@Value("${local.server.port}") int port) {

            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("name@gmail.com", "password");

            return new TestRestTemplate(restTemplateBuilder);
        }
    }
}