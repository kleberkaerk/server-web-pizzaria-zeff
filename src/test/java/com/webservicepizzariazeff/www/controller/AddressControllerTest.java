package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.request.AddressDTO;
import com.webservicepizzariazeff.www.exception.ExistingAddressException;
import com.webservicepizzariazeff.www.service.AddressService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AddressControllerTest {

    @InjectMocks
    AddressController addressController;

    @Mock
    AddressService addressService;

    @BeforeEach
    void definitionOfBehaviorsForMocks() {

        BDDMockito.when(this.addressService.registerAddress(
                        ArgumentMatchers.any(UserDetails.class),
                        ArgumentMatchers.any(AddressDTO.class),
                        ArgumentMatchers.anyString()))
                .thenReturn(1L);
    }

    @Test
    void registerNewAddress_createANewAddressAndReturnsTheIdOfThisAddress_WhenTheAddressIsNotRegisteredInTheDatabase() {

        User userWhoWillRegisterTheAddress = User.UserBuilder.builder().build();

        AddressDTO addressToBeSaved = AddressDTO.AddressDTOBuilder.builder()
                .number("")
                .road("")
                .district("")
                .state("")
                .city("")
                .build();

        Assertions.assertThat(this.addressController.registerNewAddress(userWhoWillRegisterTheAddress, addressToBeSaved, "pt-BR"))
                .isNotNull()
                .isEqualTo(new ResponseEntity<>(1L, HttpStatus.CREATED));
    }

    @Test
    void registerNewAddress_throwsExistingAddressException_whenTheAddressIsAlreadyRegisteredInTheDatabase() {

        BDDMockito.when(this.addressService.registerAddress(
                        ArgumentMatchers.any(UserDetails.class),
                        ArgumentMatchers.any(AddressDTO.class),
                        ArgumentMatchers.anyString()
                ))
                .thenThrow(new ExistingAddressException(""));

        User userWhoWillRegisterTheAddress = User.UserBuilder.builder().build();

        AddressDTO addressToBeSaved = AddressDTO.AddressDTOBuilder.builder()
                .number("")
                .road("")
                .district("")
                .state("")
                .city("")
                .build();

        Assertions.assertThatExceptionOfType(ExistingAddressException.class)
                .isThrownBy(() -> this.addressController.registerNewAddress(userWhoWillRegisterTheAddress, addressToBeSaved, "pt-BR"));
    }
}