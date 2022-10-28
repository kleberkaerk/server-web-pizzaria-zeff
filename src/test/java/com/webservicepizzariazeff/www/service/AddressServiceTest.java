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

    private static List<Address> listOfTheAddressForReturn;

    private static User userWhoOwnsTheAddress;

    private static AddressRequestDTO addressToBeSaved;

    static void setListOfTheAddressForReturn() {

        listOfTheAddressForReturn = List.of(
                Address.AddressBuilder.builder()
                        .id(1L)
                        .number("1")
                        .road("road")
                        .district("district")
                        .city("city")
                        .state("state")
                        .user(User.UserBuilder.builder()
                                .id(1L)
                                .name("name")
                                .username("username")
                                .password("password")
                                .authorities("ROLE_USER")
                                .build())
                        .build(),
                Address.AddressBuilder.builder()
                        .id(2L)
                        .number("2")
                        .road("road2")
                        .district("district2")
                        .city("city2")
                        .state("state2")
                        .user(User.UserBuilder.builder()
                                .id(2L)
                                .name("name2")
                                .username("username2")
                                .password("password2")
                                .authorities("ROLE_USER")
                                .build())
                        .build()
        );
    }

    static void setUserWhoOwnsTheAddress() {

        userWhoOwnsTheAddress = User.UserBuilder.builder()
                .id(1L)
                .name("name")
                .username("username")
                .password("password")
                .authorities("ROLE_USER")
                .build();
    }

    static void setAddressToBeSaved() {

        addressToBeSaved = AddressRequestDTO.AddressRequestDTOBuilder.builder()
                .number("1")
                .road("road")
                .district("district")
                .state("state")
                .city("city")
                .build();
    }

    @BeforeAll
    static void initializeObjects() {

        setListOfTheAddressForReturn();
        setUserWhoOwnsTheAddress();
        setAddressToBeSaved();
    }

    @BeforeEach
    void definitionOfBehaviorsForMocks() {

        BDDMockito.when(this.addressRepository.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(Optional.of(listOfTheAddressForReturn.get(0)));

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
                .thenReturn(listOfTheAddressForReturn.get(0));

        BDDMockito.when(this.addressRepository.findByUser(ArgumentMatchers.any(User.class)))
                .thenReturn(listOfTheAddressForReturn);

        BDDMockito.doNothing()
                .when(this.addressRepository).deleteById(ArgumentMatchers.any());
    }

    @Test
    void findById_returnsAnAddress_whenTheIdParameterExists() {

        Assertions.assertThatCode(() -> this.addressService.findById(1L))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.addressService.findById(1L))
                .isNotNull()
                .isEqualTo(Address.AddressBuilder.builder()
                        .id(1L)
                        .number("1")
                        .road("road")
                        .district("district")
                        .city("city")
                        .state("state")
                        .user(userWhoOwnsTheAddress)
                        .build());
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

        Assertions.assertThatCode(() -> this.addressService.registerAddress(userWhoOwnsTheAddress, addressToBeSaved, "pt-BR"))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.addressService.registerAddress(userWhoOwnsTheAddress, addressToBeSaved, "pt-BR"))
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
        )).thenReturn(Optional.of(listOfTheAddressForReturn.get(0)));

        Assertions.assertThatExceptionOfType(ExistingAddressException.class)
                .isThrownBy(() -> this.addressService.registerAddress(userWhoOwnsTheAddress, addressToBeSaved, "pt-BR"));
    }

    @Test
    void findByUser_returnsAListOfAddressResponseDTOOfTheUser_wheneverCalled() {

        Assertions.assertThatCode(() -> this.addressService.findByUser(userWhoOwnsTheAddress))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.addressService.findByUser(userWhoOwnsTheAddress))
                .isNotNull()
                .asList()
                .contains(AddressResponseDTO.AddressResponseDTOBuilder.builder()
                        .id(2L)
                        .number("2")
                        .road("road2")
                        .district("district2")
                        .city("city2")
                        .state("state2")
                        .build());
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