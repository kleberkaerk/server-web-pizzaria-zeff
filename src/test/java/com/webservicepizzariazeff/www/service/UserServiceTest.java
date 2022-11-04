package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.request.UserRequestDTO;
import com.webservicepizzariazeff.www.exception.ExistingUserException;
import com.webservicepizzariazeff.www.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private static User user;

    private static UserRequestDTO userRequestDTO;

    static void setUser() {

        user = User.UserBuilder.builder()
                .id(1L)
                .name("name")
                .username("username")
                .password("password")
                .authorities("ROLE_USER")
                .build();
    }

    static void setUserRequestDTO() {

        userRequestDTO = UserRequestDTO.UserDTOBuilder.builder()
                .name("name")
                .username("username")
                .password("password")
                .build();
    }

    @BeforeAll
    static void initializeObjects() {

        setUser();
        setUserRequestDTO();
    }

    @BeforeEach
    void definitionOfBehaviorsForMocks() {

        BDDMockito.when(this.userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        BDDMockito.when(this.userRepository.save(ArgumentMatchers.any(User.class)))
                .thenReturn(user);
    }

    @Test
    void registerUser_persistNewUserInDatabase_WhenTheUserIsNotRegisteredInTheDatabase() {

        Assertions.assertThatCode(() -> this.userService.registerUser(userRequestDTO, "pt-BR"))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.userService.registerUser(userRequestDTO, "pt-BR"))
                .isEqualTo(1L);
    }

    @Test
    void registerUser_throwsExistingUserException_whenTheUserIsAlreadyRegisteredInTheDatabase() {

        BDDMockito.when(this.userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(user));

        Assertions.assertThatExceptionOfType(ExistingUserException.class)
                .isThrownBy(() -> this.userService.registerUser(userRequestDTO, "pt-BR"));
    }

    @Test
    void registerUser_throwsResponseStatusException_whenThePassedAcceptLanguageIsInvalid() {

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.userService.registerUser(userRequestDTO, "a"));
    }

    @Test
    void loadUserByUsername_findsAUserByUsernameAndReturnsThisUser_whenTheUserIsSavedInDatabase() {

        BDDMockito.when(this.userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(user));

        Assertions.assertThatCode(() -> this.userService.loadUserByUsername("username"))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.userService.loadUserByUsername("username"))
                .isNotNull()
                .isEqualTo(user);
    }

    @Test
    void loadUserByUsername_throwsUsernameNotFoundException_whenTheUserIsNotSavedInDatabase() {

        BDDMockito.when(this.userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenThrow(UsernameNotFoundException.class);

        Assertions.assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> this.userService.loadUserByUsername(""));
    }
}