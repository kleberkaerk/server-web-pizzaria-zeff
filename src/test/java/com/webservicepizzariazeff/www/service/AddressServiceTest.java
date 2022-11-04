package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.Address;
import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.request.AddressRequestDTO;
import com.webservicepizzariazeff.www.dto.response.AddressResponseDTO;
import com.webservicepizzariazeff.www.exception.ExistingAddressException;
import com.webservicepizzariazeff.www.repository.AddressRepository;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    private static User user;

    private static Address address;

    private static AddressRequestDTO addressRequestDTO;

    private static List<Address> addresses;

    private static AddressResponseDTO addressResponseDTOToComparisonInFindByUser;

    static void setUser(){

        user = User.UserBuilder.builder()
                .id(1L)
                .name("name")
                .username("username")
                .password("password")
                .authorities("ROLE_USER")
                .build();
    }

    static void setAddress(){

        address = Address.AddressBuilder.builder()
                .id(1L)
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .user(user)
                .build();
    }

    static void setAddressRequestDTO() {

        addressRequestDTO = AddressRequestDTO.AddressRequestDTOBuilder.builder()
                .number("1")
                .road("road")
                .district("district")
                .state("state")
                .city("city")
                .build();
    }

    static void setAddresses() {

        addresses = List.of(
                Address.AddressBuilder.builder()
                        .id(1L)
                        .number("1")
                        .road("road")
                        .district("district")
                        .city("city")
                        .state("state")
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

    static void setAddressResponseDTOToComparisonInFindByUser(){

        addressResponseDTOToComparisonInFindByUser = AddressResponseDTO.AddressResponseDTOBuilder.builder()
                .id(1L)
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .build();
    }

    @BeforeAll
    static void initializeObjects() {

        setUser();
        setAddress();
        setAddressRequestDTO();
        setAddresses();
        setAddressResponseDTOToComparisonInFindByUser();
    }

    @BeforeEach
    void definitionOfBehaviorsForMocks() {

        BDDMockito.when(this.addressRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(address));

        BDDMockito.when(this.addressRepository.findByNumberAndRoadAndDistrictAndCityAndStateAndUser(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(User.class)
                ))
                .thenReturn(Optional.empty());

        BDDMockito.when(this.addressRepository.save(ArgumentMatchers.any(Address.class)))
                .thenReturn(address);

        BDDMockito.when(this.addressRepository.findByUser(ArgumentMatchers.any(User.class)))
                .thenReturn(addresses);

        BDDMockito.doNothing()
                .when(this.addressRepository).deleteById(ArgumentMatchers.any());
    }

    @Test
    void findById_returnsAnAddress_whenTheIdParameterExists() {

        Assertions.assertThatCode(() -> this.addressService.findById(1L))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.addressService.findById(1L))
                .isNotNull()
                .isEqualTo(address);
    }

    @Test
    void findById_throwsResponseStatusException_whenTheIdParameterNotExists() {

        BDDMockito.when(this.addressRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.addressService.findById(1L));
    }

    @Test
    void registerAddress_registersANewAddressForAUserAndReturnsTheIdOfTheCreatedAddress_whenTheAddressDoesNotExistInTheDatabase() {

        Assertions.assertThatCode(() -> this.addressService.registerAddress(user, addressRequestDTO, "pt-BR"))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.addressService.registerAddress(user, addressRequestDTO, "pt-BR"))
                .isEqualTo(1L);
    }

    @Test
    void registerAddress_throwsExistingAddressException_whenTheAddressIsAlreadyRegisteredInTheDatabase() {

        BDDMockito.when(this.addressRepository.findByNumberAndRoadAndDistrictAndCityAndStateAndUser(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(User.class)
        )).thenReturn(Optional.of(address));

        Assertions.assertThatExceptionOfType(ExistingAddressException.class)
                .isThrownBy(() -> this.addressService.registerAddress(user, addressRequestDTO, "pt-BR"));
    }

    @Test
    void registerAddress_throwsResponseStatusException_whenThePassedAcceptLanguageIsInvalid(){

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(()-> this.addressService.registerAddress(user, addressRequestDTO, "a"));
    }

    @Test
    void findByUser_returnsAListOfAddressResponseDTOOfTheUser_wheneverCalled() {

        Assertions.assertThatCode(() -> this.addressService.findByUser(user))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.addressService.findByUser(user))
                .isNotNull()
                .asList()
                .contains(addressResponseDTOToComparisonInFindByUser);
    }

    @Test
    void deleteAAddress_deleteAnAddressUsingTheId_whenTheAddressExistsInTheDatabase() {

        Assertions.assertThatCode(() -> this.addressService.deleteAAddress(1L))
                .doesNotThrowAnyException();
    }

    @Test
    void deleteAAddress_throwsResponseStatusException_whenTheAddressNotExistsInTheDatabase() {

        BDDMockito.when(this.addressRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.addressService.deleteAAddress(1L));
    }
}