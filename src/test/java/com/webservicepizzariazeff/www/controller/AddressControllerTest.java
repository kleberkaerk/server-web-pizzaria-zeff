package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.request.AddressRequestDTO;
import com.webservicepizzariazeff.www.dto.response.AddressResponseDTO;
import com.webservicepizzariazeff.www.exception.ExistingAddressException;
import com.webservicepizzariazeff.www.service.AddressService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@ExtendWith(SpringExtension.class)
class AddressControllerTest {

    @InjectMocks
    private AddressController addressController;

    @Mock
    private AddressService addressService;

    private static List<AddressResponseDTO> addressResponseDTOForFindByUser;

    @BeforeAll
    static void setObjects() {

        addressResponseDTOForFindByUser = List.of(
                AddressResponseDTO.AddressResponseDTOBuilder.builder()
                        .id(1L)
                        .number("1")
                        .road("road1")
                        .district("district1")
                        .city("city1")
                        .state("state1")
                        .build(),
                AddressResponseDTO.AddressResponseDTOBuilder.builder()
                        .id(2L)
                        .number("2")
                        .road("road2")
                        .district("district2")
                        .city("city2")
                        .state("state2")
                        .build(),
                AddressResponseDTO.AddressResponseDTOBuilder.builder()
                        .id(3L)
                        .number("3")
                        .road("road3")
                        .district("district3")
                        .city("city3")
                        .state("state3")
                        .build(),
                AddressResponseDTO.AddressResponseDTOBuilder.builder()
                        .id(4L)
                        .number("4")
                        .road("road4")
                        .district("district4")
                        .city("city4")
                        .state("state4")
                        .build()
        );
    }

    @BeforeEach
    void definitionOfBehaviorsForMocks() {

        BDDMockito.when(this.addressService.registerAddress(
                        ArgumentMatchers.any(UserDetails.class),
                        ArgumentMatchers.any(AddressRequestDTO.class),
                        ArgumentMatchers.anyString()))
                .thenReturn(1L);

        BDDMockito.when(this.addressService.findByUser(ArgumentMatchers.any(UserDetails.class)))
                .thenReturn(addressResponseDTOForFindByUser);

        BDDMockito.doNothing()
                .when(this.addressService).deleteAAddress(ArgumentMatchers.any(Long.class));
    }

    @Test
    void registerNewAddress_createANewAddressAndReturnsTheIdOfThisAddress_WhenTheAddressIsNotRegisteredInTheDatabase() {

        User userWhoWillRegisterTheAddress = User.UserBuilder.builder().build();

        AddressRequestDTO addressToBeSaved = AddressRequestDTO.AddressRequestDTOBuilder.builder()
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
                        ArgumentMatchers.any(AddressRequestDTO.class),
                        ArgumentMatchers.anyString()
                ))
                .thenThrow(new ExistingAddressException(""));

        User userWhoWillRegisterTheAddress = User.UserBuilder.builder().build();

        AddressRequestDTO addressToBeSaved = AddressRequestDTO.AddressRequestDTOBuilder.builder()
                .number("")
                .road("")
                .district("")
                .state("")
                .city("")
                .build();

        Assertions.assertThatExceptionOfType(ExistingAddressException.class)
                .isThrownBy(() -> this.addressController.registerNewAddress(userWhoWillRegisterTheAddress, addressToBeSaved, "pt-BR"));
    }

    @Test
    void findAddressByUser_returnsAListOfAddressResponseDTOOfTheUser_wheneverCalled() {

        User user = User.UserBuilder.builder()
                .id(1L)
                .name("name")
                .username("username")
                .password("password")
                .authorities("ROLE_USER")
                .build();

        Assertions.assertThat(this.addressController.findAddressesByUser(user))
                .isNotNull()
                .isEqualTo(ResponseEntity.ok(addressResponseDTOForFindByUser));

        Assertions.assertThat(this.addressController.findAddressesByUser(user).getBody())
                .hasSize(4);

        Assertions.assertThat(this.addressController.findAddressesByUser(user).getBody())
                .asList()
                .contains(AddressResponseDTO.AddressResponseDTOBuilder.builder()
                        .id(3L)
                        .number("3")
                        .road("road3")
                        .district("district3")
                        .city("city3")
                        .state("state3")
                        .build());
    }

    @Test
    void deleteAAddress_deleteAnAddressUsingTheId_whenTheAddressExistsInTheDatabase() {

        Assertions.assertThatCode(() -> this.addressController.deleteAAddress(1L))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.addressController.deleteAAddress(1L))
                .isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Test
    void deleteAAddress_throwsResponseStatusException_whenTheAddressNotExistsInTheDatabase() {

        BDDMockito.doThrow(ResponseStatusException.class)
                .when(this.addressService).deleteAAddress(ArgumentMatchers.any(Long.class));

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.addressController.deleteAAddress(1L));
    }
}