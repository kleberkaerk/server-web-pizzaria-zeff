package com.webservicepizzariazeff.www.integration;

import com.webservicepizzariazeff.www.domain.Address;
import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.request.AddressRequestDTO;
import com.webservicepizzariazeff.www.dto.response.AddressResponseDTO;
import com.webservicepizzariazeff.www.exception_handler.ExistingAddressExceptionHandler;
import com.webservicepizzariazeff.www.integration.mapper.MapperToIntegration;
import com.webservicepizzariazeff.www.repository.AddressRepository;
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

import java.util.List;
import java.util.Objects;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.jpa.hibernate.ddl-auto=update", "spring.flyway.enabled=false"}
)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AddressControllerIT {

    @Autowired
    @Qualifier(value = "testRestTemplateUserToAddressControllerIT")
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    private HttpHeaders httpHeaders;

    private static final String[] acceptLanguage = {"Accept-Language", "pt-BR"};

    private static User user;

    private static AddressRequestDTO addressRequestDTORegisterNewAddress;

    private static Address addressRegisterNewAddress;

    private static ExistingAddressExceptionHandler existingAddressExceptionHandlerToComparisonInRegisterNewAddress;

    private static List<Address> addressFindAddressesByUser;

    private static List<AddressResponseDTO> addressResponseDTOSToComparisonInFindAddressesByUser;

    private static Address addressDeleteAddress;

    static void setUser() {

        user = User.UserBuilder.builder()
                .id(1L)
                .name("name")
                .username("name@gmail.com")
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("password"))
                .authorities("ROLE_USER")
                .build();
    }

    static void setAddressRequestDTORegisterNewAddress() {

        addressRequestDTORegisterNewAddress = AddressRequestDTO.AddressRequestDTOBuilder.builder()
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .build();
    }

    static void setAddressRegisterNewAddress() {

        addressRegisterNewAddress = Address.AddressBuilder.builder()
                .number(addressRequestDTORegisterNewAddress.getNumber())
                .road(addressRequestDTORegisterNewAddress.getRoad())
                .district(addressRequestDTORegisterNewAddress.getDistrict())
                .city(addressRequestDTORegisterNewAddress.getCity())
                .state(addressRequestDTORegisterNewAddress.getState())
                .user(user)
                .build();
    }

    static void setExistingAddressExceptionHandlerToComparisonInRegisterNewAddress() {

        existingAddressExceptionHandlerToComparisonInRegisterNewAddress = ExistingAddressExceptionHandler.
                ExistingAddressExceptionHandlerBuilder.builder()
                .message("Este endereço já foi cadastrado antes.")
                .build();
    }

    static void setAddressFindAddressesByUser() {

        addressFindAddressesByUser = List.of(
          Address.AddressBuilder.builder()
                  .id(1L)
                  .number("1")
                  .road("road1")
                  .district("district1")
                  .city("city1")
                  .state("state1")
                  .user(user)
                  .build(),
          Address.AddressBuilder.builder()
                  .id(2L)
                  .number("2")
                  .road("road2")
                  .district("district2")
                  .city("city2")
                  .state("state2")
                  .user(user)
                  .build()
        );
    }

    static void setAddressResponseDTOSToComparisonInFindAddressesByUser() {

        addressResponseDTOSToComparisonInFindAddressesByUser = addressFindAddressesByUser.stream()
                .map(Mapper::fromAddressToAddressResponseDTO)
                .toList();
    }

    static void setAddressDeleteAddress() {

        addressDeleteAddress = Address.AddressBuilder.builder()
                .id(1L)
                .number("number1")
                .road("road1")
                .district("district1")
                .city("city1")
                .state("state1")
                .user(user)
                .build();
    }

    @BeforeAll
    static void initializeObjects() {

        setUser();
        setAddressRequestDTORegisterNewAddress();
        setAddressRegisterNewAddress();
        setExistingAddressExceptionHandlerToComparisonInRegisterNewAddress();
        setAddressFindAddressesByUser();
        setAddressResponseDTOSToComparisonInFindAddressesByUser();
        setAddressDeleteAddress();
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
    void registerNewAddress_registersANewAddressAndReturnsAStatusCode201_whenTheAddressIsNotRegistered() {

        this.userRepository.save(user);

        this.httpHeaders = getHeaders();

        ResponseEntity<Long> idOfTheAddressThatWasCreated
                = this.testRestTemplate.exchange(
                "/addresses/register",
                HttpMethod.PUT,
                new HttpEntity<>(addressRequestDTORegisterNewAddress, this.httpHeaders),
                new ParameterizedTypeReference<>() {
                });

        Assertions.assertThat(idOfTheAddressThatWasCreated.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(idOfTheAddressThatWasCreated.getBody())
                .isNotNull()
                .isEqualTo(1L);
    }

    @Test
    void registerNewAddress_returnsAHandlerObjectAndAStatusCode409_whenTheAddressIsAlreadyRegisteredInTheSystem() {

        this.userRepository.save(user);
        this.addressRepository.save(addressRegisterNewAddress);

        this.httpHeaders = getHeaders();

        ResponseEntity<ExistingAddressExceptionHandler> responseEntity = this.testRestTemplate.exchange(
                "/addresses/register",
                HttpMethod.PUT,
                new HttpEntity<>(addressRequestDTORegisterNewAddress, this.httpHeaders),
                new ParameterizedTypeReference<>() {
                });

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.CONFLICT);

        Assertions.assertThat(responseEntity.getBody())
                .isEqualTo(existingAddressExceptionHandlerToComparisonInRegisterNewAddress);
    }

    @Test
    void registerNewAddress_returnsAStatusCode400_whenThePassedAcceptLanguageIsInvalid() {

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

        this.userRepository.save(user);

        ResponseEntity<Void> responseEntity
                = this.testRestTemplate.exchange(
                "/addresses/register",
                HttpMethod.PUT,
                new HttpEntity<>(addressRequestDTORegisterNewAddress, this.httpHeaders),
                new ParameterizedTypeReference<>() {
                });

        Assertions.assertThat(responseEntity.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void findAddressesByUser_returnAListWithTheAddressesOfAGivenUserAndAStatusCode200_wheneverCalled() {

        this.userRepository.save(user);

        addressFindAddressesByUser.forEach(addressRepository::save);

        ResponseEntity<List<AddressResponseDTO>> userAddresses = this.testRestTemplate.exchange(
                "/addresses/find-by-user",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(userAddresses.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.OK);

        Assertions.assertThat(userAddresses.getBody())
                .isNotNull()
                .asList()
                .hasSize(addressResponseDTOSToComparisonInFindAddressesByUser.size())
                .isEqualTo(addressResponseDTOSToComparisonInFindAddressesByUser)
                .contains(addressResponseDTOSToComparisonInFindAddressesByUser.get(0));
    }

    @Test
    void deleteAddress_deleteAAddressByIdAndReturnsAStatusCode204_whenThePassedIdExistsInTheDatabase() {

        this.userRepository.save(user);
        this.addressRepository.save(addressDeleteAddress);

        this.httpHeaders = getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplate.exchange(
                "/addresses/1",
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
    void deleteAddress_returnsAHandlerObjectAndAStatusCode400_whenTheIdPassedDoesNotExistInTheDatabase() {

        this.userRepository.save(user);

        this.httpHeaders = getHeaders();

        ResponseEntity<Void> responseEntity = this.testRestTemplate.exchange(
                "/addresses/2",
                HttpMethod.DELETE,
                new HttpEntity<>(this.httpHeaders),
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

        @Bean(name = "testRestTemplateUserToAddressControllerIT")
        public TestRestTemplate testRestTemplateUser(@Value("${local.server.port}") int port) {

            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("name@gmail.com", "password");

            return new TestRestTemplate(restTemplateBuilder);
        }
    }
}
