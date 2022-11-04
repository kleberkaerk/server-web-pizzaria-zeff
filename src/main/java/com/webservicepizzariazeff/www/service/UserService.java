package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.request.UserRequestDTO;
import com.webservicepizzariazeff.www.exception.ExistingUserException;
import com.webservicepizzariazeff.www.repository.UserRepository;
import com.webservicepizzariazeff.www.util.Mapper;
import com.webservicepizzariazeff.www.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    protected UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User createUserToBeSaved(UserRequestDTO userRequestDTO) {

        return User.UserBuilder.builder()
                .name(userRequestDTO.getName())
                .username(userRequestDTO.getUsername())
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(userRequestDTO.getPassword()))
                .authorities("ROLE_USER")
                .build();
    }

    public Long registerUser(UserRequestDTO userRequestDTO, String acceptLanguage) {

        Validator.validateAcceptLanguage(acceptLanguage);
        String[] languageAndCountry = Mapper.fromAcceptLanguageToStringArray(acceptLanguage);

        ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale(languageAndCountry[0], languageAndCountry[1]));

        Optional<User> optionalUser = this.userRepository.findByUsername(userRequestDTO.getUsername());

        if (optionalUser.isPresent()) {
            throw new ExistingUserException(messages.getString("existing.email"));
        }

        User userToBeSaved = this.createUserToBeSaved(userRequestDTO);

        return this.userRepository.save(userToBeSaved).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en", "US"));

        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(messages.getString("user.not.found")));
    }
}
