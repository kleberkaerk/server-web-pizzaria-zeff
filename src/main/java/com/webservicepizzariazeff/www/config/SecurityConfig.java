package com.webservicepizzariazeff.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .authorizeRequests()
                .antMatchers("/users/register").not().authenticated()
                .antMatchers("/products**/**").permitAll()
                .antMatchers("/addresses/register").authenticated()
                .antMatchers("/addresses/find-by-user").fullyAuthenticated()
                .antMatchers("/addresses/**").authenticated()
                .antMatchers("/sales/sale").authenticated()
                .antMatchers("/purchases/user").fullyAuthenticated()
                .antMatchers("/purchases/**").authenticated()
                .anyRequest()
                .denyAll()
                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    public AuthenticationManager authentication(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }
}
