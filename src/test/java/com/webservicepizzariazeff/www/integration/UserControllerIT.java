package com.webservicepizzariazeff.www.integration;

import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.request.UserRequestDTO;
import com.webservicepizzariazeff.www.exception_handler.ExistingUserExceptionHandler;
import com.webservicepizzariazeff.www.integration.mapper.MapperToIntegration;
import com.webservicepizzariazeff.www.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.Objects;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.jpa.hibernate.ddl-auto=update", "spring.flyway.enabled=false"}
)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier(value = "testRestTemplateUserToUserControllerIT")
    private TestRestTemplate testRestTemplate;

    private HttpHeaders httpHeaders;

    private static final String[] acceptLanguage = {"Accept-Language", "pt-BR"};

    private static UserRequestDTO userRequestDTO;

    private static User userRegisterNewUser;

    private static ExistingUserExceptionHandler existingUserExceptionHandlerToComparisonInRegisterNewUser;

    static void setUserRequestDTO() {

        userRequestDTO = UserRequestDTO.UserDTOBuilder.builder()
                .name("name")
                .password("123456789")
                .username("name@gmail.com")
                .build();
    }

    static void setUserRegisterNewUser() {

        userRegisterNewUser = User.UserBuilder.builder()
                .name(userRequestDTO.getName())
                .username(userRequestDTO.getUsername())
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(userRequestDTO.getPassword()))
                .authorities("ROLE_USER")
                .build();
    }

    @BeforeAll
    static void setExistingUserExceptionHandlerToComparisonInRegisterNewUser() {

        existingUserExceptionHandlerToComparisonInRegisterNewUser = ExistingUserExceptionHandler.
                ExistingUserExceptionHandlerBuilder.builder()
                .message("Este email já está sendo utilizado, por favor escolha outro.")
                .build();
    }

    @BeforeEach
    void initializeObjects() {

        setUserRequestDTO();
        setUserRegisterNewUser();
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
    void registerNewUser_registersANewUserAndReturnsAStatusCode201_whenTheUserIsNotRegistered() {

        this.httpHeaders = getHeaders();

        ResponseEntity<Long> idOfTheUserThatWasCreated = this.testRestTemplate.exchange(
                "/users/register",
                HttpMethod.PUT,
                new HttpEntity<>(userRequestDTO, httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(idOfTheUserThatWasCreated.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(idOfTheUserThatWasCreated.getBody())
                .isNotNull()
                .isEqualTo(1L);
    }

    @Test
    void registerNewUser_returnsAHandlerObjectAndAStatusCode409_whenTheUserIsAlreadyRegisteredInTheSystem() {

        this.userRepository.save(userRegisterNewUser);

        this.httpHeaders = getHeaders();

        ResponseEntity<ExistingUserExceptionHandler> responseEntity = this.testRestTemplate.exchange(
                "/users/register",
                HttpMethod.PUT,
                new HttpEntity<>(userRequestDTO, httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.CONFLICT);

        Assertions.assertThat(responseEntity.getBody())
                .isEqualTo(existingUserExceptionHandlerToComparisonInRegisterNewUser);
    }

    @Test
    void registerNewUser_returnsAStatusCode400_whenThePassedAcceptLanguageIsInvalid() {

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

        this.httpHeaders.add(acceptLanguage[0], "a");
        this.httpHeaders.add("Cookie", "XSRF-TOKEN=" + csrfToken);
        this.httpHeaders.add("X-XSRF-TOKEN", csrfToken);

        ResponseEntity<Void> responseEntity = this.testRestTemplate.exchange(
                "/users/register",
                HttpMethod.PUT,
                new HttpEntity<>(userRequestDTO, httpHeaders),
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

        @Bean(name = "testRestTemplateUserToUserControllerIT")
        public TestRestTemplate testRestTemplateUser(@Value("${local.server.port}") int port) {

            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port);

            return new TestRestTemplate(restTemplateBuilder);
        }
    }
}
