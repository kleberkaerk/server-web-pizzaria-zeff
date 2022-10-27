package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.dto.response.PurchaseRestaurantResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchaseUserResponseDTO;
import com.webservicepizzariazeff.www.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    protected PurchaseController(PurchaseService purchaseService) {

        this.purchaseService = purchaseService;
    }

    @GetMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Boolean, List<PurchaseUserResponseDTO>>> findPurchasesOfTheUser(
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        return new ResponseEntity<>(this.purchaseService.findByAllPurchasesOfTheAnUser(userDetails), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> cancelPurchase(@PathVariable Long id, @RequestHeader("Accept-Language") String acceptLanguage) {

        this.purchaseService.cancelPurchaseOfTheUser(id, acceptLanguage);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "admin/find-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Boolean, List<PurchaseRestaurantResponseDTO>>> findUsersPurchases(){

        return new ResponseEntity<>(this.purchaseService.findByAllUsersPurchases(), HttpStatus.OK);
    }
}
