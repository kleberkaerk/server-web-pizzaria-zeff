package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.dto.UserDTO;
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

    @RequestMapping(value = "authorization", method = RequestMethod.HEAD)
    public ResponseEntity<Void> authorizationForRegistration (){

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> registerNewUser(
            @RequestBody @Valid UserDTO userDTO,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage) {

        return new ResponseEntity<>(this.userService.registerUser(userDTO, acceptLanguage), HttpStatus.CREATED);
    }

}
