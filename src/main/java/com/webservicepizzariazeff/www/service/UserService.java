package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.UserDTO;
import com.webservicepizzariazeff.www.exception.ExistingUserException;
import com.webservicepizzariazeff.www.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    protected UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long registerUser(UserDTO userDTO, String acceptLanguage) {

        String[] languageAndCountry = acceptLanguage.split("-");

        ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale(languageAndCountry[0], languageAndCountry[1]));

        Optional<User> optionalUser = this.userRepository.findByUsername(userDTO.getUsername());

        if (optionalUser.isPresent()) {
            throw new ExistingUserException(messages.getString("existing.email"));
        }

        User userToBeSaved = User.UserBuilder.builder()
                .username(userDTO.getUsername())
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(userDTO.getPassword()))
                .authorities("ROLE_USER")
                .build();

        return this.userRepository.save(userToBeSaved).getId();
    }
}
