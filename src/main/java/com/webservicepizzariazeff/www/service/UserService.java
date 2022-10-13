package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.request.UserDTO;
import com.webservicepizzariazeff.www.exception.ExistingUserException;
import com.webservicepizzariazeff.www.repository.UserRepository;
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

    private User createUserToBeSaved(UserDTO userDTO) {

        return User.UserBuilder.builder()
                .name(userDTO.getName())
                .username(userDTO.getUsername())
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(userDTO.getPassword()))
                .authorities("ROLE_USER")
                .build();
    }

    public Long registerUser(UserDTO userDTO, String acceptLanguage) {

        String[] languageAndCountry = acceptLanguage.split("-");

        ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale(languageAndCountry[0], languageAndCountry[1]));

        Optional<User> optionalUser = this.userRepository.findByUsername(userDTO.getUsername());

        if (optionalUser.isPresent()) {
            throw new ExistingUserException(messages.getString("existing.email"));
        }

        User userToBeSaved = this.createUserToBeSaved(userDTO);

        return this.userRepository.save(userToBeSaved).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale("en", "US"));

        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(messages.getString("user.not.found")));
    }
}
