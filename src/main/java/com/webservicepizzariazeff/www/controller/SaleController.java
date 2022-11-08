package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.dto.request.SaleRequestDTO;
import com.webservicepizzariazeff.www.exception_handler.InvalidCardExceptionHandler;
import com.webservicepizzariazeff.www.service.SaleService;
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

@RestController
@RequestMapping("sales")
public class SaleController {

    private final SaleService saleService;

    @Autowired
    protected SaleController(SaleService saleService) {

        this.saleService = saleService;
    }

    @PostMapping(value = "sale")
    @Operation(responses = {@ApiResponse(responseCode = "201", description = "If the request body is valid",
            content = @Content(schema = @Schema(example = "1", type = "Integer"))),
            @ApiResponse(responseCode = "404", description = "If the request body is invalid",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvalidCardExceptionHandler.class)))})
    public ResponseEntity<Long> sale(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid SaleRequestDTO saleRequestDTO,
            @RequestHeader(value = "Accept-Language") @Parameter(example = "pt-BR") String acceptLanguage
    ) {

        return new ResponseEntity<>(this.saleService.sale(userDetails, saleRequestDTO, acceptLanguage), HttpStatus.CREATED);
    }
}
