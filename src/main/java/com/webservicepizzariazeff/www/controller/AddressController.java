package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.dto.request.AddressRequestDTO;
import com.webservicepizzariazeff.www.dto.response.AddressResponseDTO;
import com.webservicepizzariazeff.www.service.AddressService;
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

    @PutMapping(
            value = "register",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Long> registerNewAddress(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid AddressRequestDTO addressRequestDTO,
            @RequestHeader(value = "Accept-Language") String acceptLanguage
    ) {

        return new ResponseEntity<>(this.addressService.registerAddress(userDetails, addressRequestDTO, acceptLanguage), HttpStatus.CREATED);
    }

    @GetMapping(value = "find-by-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AddressResponseDTO>> findAddressesByUser(@AuthenticationPrincipal UserDetails userDetails) {

        return new ResponseEntity<>(this.addressService.findByUser(userDetails), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {

        this.addressService.deleteAAddress(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
