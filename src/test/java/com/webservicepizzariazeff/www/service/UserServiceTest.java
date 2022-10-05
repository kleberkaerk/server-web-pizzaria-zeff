package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.UserDTO;
import com.webservicepizzariazeff.www.exception.ExistingUserException;
import com.webservicepizzariazeff.www.repository.UserRepository;
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
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void definitionOfBehaviorsForMocks() {

        BDDMockito.when(this.userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        BDDMockito.when(this.userRepository.save(ArgumentMatchers.any(User.class)))
                .thenReturn(User.UserBuilder.builder()
                        .id(2L)
                        .username("user2")
                        .password("1234")
                        .authorities("ROLE_USER")
                        .build());
    }

    @Test
    void registerUser_persistNewUserInDatabase_WhenTheUserIsNotRegisteredInTheDatabase() {

        UserDTO user = UserDTO.UserDTOBuilder.builder()
                .password("")
                .username("")
                .build();

        Assertions.assertThat(this.userService.registerUser(user, "pt-BR"))
                .isEqualTo(2L);
    }

    @Test
    void registerUser_throwsExistingUserException_whenTheUserIsAlreadyRegisteredInTheDatabase() {

        BDDMockito.when(this.userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(
                        User.UserBuilder.builder()
                                .id(1L)
                                .password("")
                                .username("")
                                .authorities("ROLE_USER")
                                .build()
                ));

        UserDTO user = UserDTO.UserDTOBuilder.builder()
                .password("")
                .username("")
                .build();

        Assertions.assertThatExceptionOfType(ExistingUserException.class)
                .isThrownBy(() -> this.userService.registerUser(user, "pt-BR"));
    }

}