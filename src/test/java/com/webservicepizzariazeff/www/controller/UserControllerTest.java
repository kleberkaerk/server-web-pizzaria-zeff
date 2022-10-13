package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.dto.request.UserDTO;
import com.webservicepizzariazeff.www.exception.ExistingUserException;
import com.webservicepizzariazeff.www.service.UserService;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void definitionOfBehaviorsForMocks() {

        BDDMockito.when(this.userService.registerUser(ArgumentMatchers.any(UserDTO.class), ArgumentMatchers.anyString()))
                .thenReturn(1L);
    }

    @Test
    void registerNewUser_createANewUserAndReturnsTheIdOfThisUser_WhenTheUserIsNotRegisteredInTheDatabase() {

        UserDTO userToBeSaved = UserDTO.UserDTOBuilder.builder()
                .password("")
                .username("")
                .build();

        Assertions.assertThat(this.userController.registerNewUser(userToBeSaved, ""))
                .isNotNull()
                .isEqualTo(new ResponseEntity<>(1L, HttpStatus.CREATED));
    }

    @Test
    void registerNewUser_throwsExistingUserException_whenTheUserIsAlreadyRegisteredInTheDatabase() {

        UserDTO user = UserDTO.UserDTOBuilder.builder()
                .password("")
                .username("")
                .build();

        BDDMockito.when(this.userService.registerUser(ArgumentMatchers.any(UserDTO.class), ArgumentMatchers.anyString()))
                .thenThrow(ExistingUserException.class);

        Assertions.assertThatExceptionOfType(ExistingUserException.class)
                .isThrownBy(() -> this.userController.registerNewUser(user, ""));
    }
}