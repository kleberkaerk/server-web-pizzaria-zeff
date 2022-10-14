package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.request.UserRequestDTO;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                        .name("name2")
                        .username("user2")
                        .password("1234")
                        .authorities("ROLE_USER")
                        .build());
    }

    @Test
    void registerUser_persistNewUserInDatabase_WhenTheUserIsNotRegisteredInTheDatabase() {

        UserRequestDTO user = UserRequestDTO.UserDTOBuilder.builder()
                .name("")
                .username("")
                .password("")
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
                                .name("")
                                .username("")
                                .password("")
                                .authorities("ROLE_USER")
                                .build()
                ));

        UserRequestDTO user = UserRequestDTO.UserDTOBuilder.builder()
                .name("")
                .username("")
                .password("")
                .build();

        Assertions.assertThatExceptionOfType(ExistingUserException.class)
                .isThrownBy(() -> this.userService.registerUser(user, "pt-BR"));
    }

    @Test
    void loadUserByUsername_findsAUserByUsernameAndReturnsThisUser_whenTheUserIsSavedInDatabase() {

        BDDMockito.when(this.userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(User.UserBuilder.builder()
                        .id(1L)
                        .name("name")
                        .username("username")
                        .password("password")
                        .authorities("ROLE_USER")
                        .build()));

        Assertions.assertThat(this.userService.loadUserByUsername("username"))
                .isNotNull()
                .isEqualTo(User.UserBuilder.builder()
                        .id(1L)
                        .name("name")
                        .username("username")
                        .password("password")
                        .authorities("ROLE_USER")
                        .build());
    }

    @Test
    void loadUserByUsername_throwsUsernameNotFoundException_whenTheUserIsNotSavedInDatabase() {

        BDDMockito.when(this.userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenThrow(UsernameNotFoundException.class);

        Assertions.assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(()-> this.userService.loadUserByUsername(""));
    }

}