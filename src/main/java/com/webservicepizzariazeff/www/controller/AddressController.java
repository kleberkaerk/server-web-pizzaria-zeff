package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.dto.request.AddressRequestDTO;
import com.webservicepizzariazeff.www.dto.response.AddressResponseDTO;
import com.webservicepizzariazeff.www.exception_handler.ExistingAddressExceptionHandler;
import com.webservicepizzariazeff.www.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("addresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    protected AddressController(AddressService addressService) {

        this.addressService = addressService;
    }

    @PutMapping(value = "register")
    @Operation(
            responses = {@ApiResponse(responseCode = "201", description = "If the request body is valid",
                    content = @Content(schema = @Schema(example = "1", type = "Integer"))),
                    @ApiResponse(responseCode = "409", description = "If the request body is invalid",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExistingAddressExceptionHandler.class)))}
    )
    public ResponseEntity<Long> registerNewAddress(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid AddressRequestDTO addressRequestDTO,
            @RequestHeader(value = "Accept-Language") @Parameter(example = "pt-BR") String acceptLanguage) {

        return new ResponseEntity<>(this.addressService.registerAddress(userDetails, addressRequestDTO, acceptLanguage), HttpStatus.CREATED);
    }

    @GetMapping(value = "find-by-user", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(responses = @ApiResponse(responseCode = "200", description = "Whenever called"))
    public ResponseEntity<List<AddressResponseDTO>> findAddressesByUser(@AuthenticationPrincipal UserDetails userDetails) {

        return new ResponseEntity<>(this.addressService.findByUser(userDetails), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    @Operation(responses = {@ApiResponse(responseCode = "204", description = "When the passed id is valid"),
            @ApiResponse(responseCode = "400", description = "When the passed id is invalid")})
    public ResponseEntity<Void> deleteAddress(@PathVariable @Parameter(example = "1") Long id) {

        this.addressService.deleteAAddress(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
