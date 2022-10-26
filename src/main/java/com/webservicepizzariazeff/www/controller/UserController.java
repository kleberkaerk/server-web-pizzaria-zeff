package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.dto.request.UserRequestDTO;
import com.webservicepizzariazeff.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Autowired
    protected UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(value = "register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> registerNewUser(
            @RequestBody @Valid UserRequestDTO userRequestDTO,
            @RequestHeader(value = "Accept-Language") String acceptLanguage) {

        return new ResponseEntity<>(this.userService.registerUser(userRequestDTO, acceptLanguage), HttpStatus.CREATED);
    }
}
