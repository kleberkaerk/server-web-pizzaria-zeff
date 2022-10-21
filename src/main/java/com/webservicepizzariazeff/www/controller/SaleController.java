package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.dto.request.SaleDTO;
import com.webservicepizzariazeff.www.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("sales")
public class SaleController {

    private final SaleService saleService;

    @Autowired
    protected SaleController(SaleService saleService) {

        this.saleService = saleService;
    }

    @PostMapping("sale")
    public ResponseEntity<Long> sale(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid SaleDTO saleDTO,
            @RequestHeader(value = "Accept-Language") String acceptLanguage
    ) {

        return new ResponseEntity<>(this.saleService.sale(userDetails, saleDTO, acceptLanguage), HttpStatus.CREATED);
    }

}
