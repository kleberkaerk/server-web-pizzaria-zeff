package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.dto.request.UserRequestDTO;
import com.webservicepizzariazeff.www.exception_handler.ExistingUserExceptionHandler;
import com.webservicepizzariazeff.www.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @GetMapping(value = "auth")
    @Operation(responses = {
            @ApiResponse(responseCode = "204", description = "Whenever called")
    })
    public ResponseEntity<Void> authenticateUser() {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "register")
    @Operation(responses = {
            @ApiResponse(responseCode = "201", description = "If the request body is valid",
                    content = @Content(schema = @Schema(example = "1", type = "Integer"))),
            @ApiResponse(responseCode = "409", description = "If the request body is invalid",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExistingUserExceptionHandler.class)))
    })
    public ResponseEntity<Long> registerNewUser(
            @RequestBody @Valid UserRequestDTO userRequestDTO,
            @RequestHeader(value = "Accept-Language") @Parameter(example = "pt-BR") String acceptLanguage) {

        return new ResponseEntity<>(this.userService.registerUser(userRequestDTO, acceptLanguage), HttpStatus.CREATED);
    }
}
