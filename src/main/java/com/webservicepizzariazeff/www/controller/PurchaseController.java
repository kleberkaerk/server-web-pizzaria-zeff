package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.dto.response.PurchaseResponseDTOForUser;
import com.webservicepizzariazeff.www.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    protected PurchaseController(PurchaseService purchaseService) {

        this.purchaseService = purchaseService;
    }

    @GetMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Boolean, List<PurchaseResponseDTOForUser>>> findPurchasesByUser(
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        return new ResponseEntity<>(this.purchaseService.findByAllPurchasesOfTheAnUser(userDetails), HttpStatus.OK);
    }
}
