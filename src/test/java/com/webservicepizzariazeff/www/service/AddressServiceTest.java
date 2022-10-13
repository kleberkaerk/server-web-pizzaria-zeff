package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.Address;
import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.request.AddressDTO;
import com.webservicepizzariazeff.www.exception.ExistingAddressException;
import com.webservicepizzariazeff.www.repository.AddressRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    private Address addressForReturn;

    @BeforeEach
    void definitionOfBehaviorsForMocks() {

        this.addressForReturn = Address.AddressBuilder.builder()
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
                .build();

        BDDMockito.when(this.addressRepository.findByNumberAndRoadAndDistrictAndCityAndStateAndUser(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(User.class)
        )).thenReturn(Optional.empty());

        BDDMockito.when(this.addressRepository.save(ArgumentMatchers.any(Address.class)))
                .thenReturn(addressForReturn);
    }

    @Test
    void registerAddress_registersANewAddressForAUserAndReturnsTheIdOfTheCreatedAddress_whenTheAddressDoesNotExistInTheDatabase() {

        User userWhoOwnsTheAddress = User.UserBuilder.builder()
                .id(1L)
                .name("name")
                .username("username")
                .password("password")
                .authorities("ROLE_USER")
                .build();

        AddressDTO addressToBeSaved = AddressDTO.AddressDTOBuilder.builder()
                .road("road")
                .district("district")
                .state("state")
                .city("city")
                .build();

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
        )).thenReturn(Optional.of(this.addressForReturn));

        User userWhoOwnsTheAddress = User.UserBuilder.builder()
                .id(1L)
                .name("name")
                .username("username")
                .password("password")
                .authorities("ROLE_USER")
                .build();

        AddressDTO addressToBeSaved = AddressDTO.AddressDTOBuilder.builder()
                .number("1")
                .road("road")
                .district("district")
                .state("state")
                .city("city")
                .build();

        Assertions.assertThatExceptionOfType(ExistingAddressException.class)
                .isThrownBy(() -> this.addressService.registerAddress(userWhoOwnsTheAddress, addressToBeSaved, "pt-BR"));

    }
}