package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.dto.response.PurchaseRestaurantResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchaseUserResponseDTO;
import com.webservicepizzariazeff.www.exception_handler.PurchaseFinishedExceptionHandler;
import com.webservicepizzariazeff.www.service.PurchaseService;
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
    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "Whenever called",
                    content = @Content(schema = @Schema(example = "Map<Boolean, List<PurchaseUserResponseDTO>>")))
    })
    public ResponseEntity<Map<Boolean, List<PurchaseUserResponseDTO>>> findPurchasesOfTheUser(
            @AuthenticationPrincipal UserDetails userDetails) {

        return new ResponseEntity<>(this.purchaseService.findByAllPurchasesOfTheAnUser(userDetails), HttpStatus.OK);
    }

    @PutMapping("{id}")
    @Operation(
            responses = {@ApiResponse(responseCode = "204", description = "If the passed id is valid"),
                    @ApiResponse(responseCode = "400", description = "If the passed id not exist"),
                    @ApiResponse(responseCode = "409", description = "If the passed id is invalid",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PurchaseFinishedExceptionHandler.class)))}
    )
    public ResponseEntity<Void> cancelPurchase(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable @Parameter(example = "1") Long id,
            @RequestHeader("Accept-Language") @Parameter(example = "pt-BR") String acceptLanguage) {

        this.purchaseService.cancelPurchaseOfTheUser(userDetails, id, acceptLanguage);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "admin/find-all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "Whenever called",
                    content = @Content(schema = @Schema(example = "Map<Boolean, List<PurchaseRestaurantResponseDTO>>")))
    })
    public ResponseEntity<Map<Boolean, List<PurchaseRestaurantResponseDTO>>> findUsersPurchases() {

        return new ResponseEntity<>(this.purchaseService.findByAllUsersPurchases(), HttpStatus.OK);
    }

    @PutMapping("admin/finish/{id}")
    @Operation(
            responses = {@ApiResponse(responseCode = "204", description = "If the passed id is valid"),
                    @ApiResponse(responseCode = "400", description = "If the passed id is invalid")}
    )
    public ResponseEntity<Void> purchasePreparation(
            @PathVariable @Parameter(example = "1") Long id,
            @RequestHeader("Accept-Language") @Parameter(example = "pt-BR") String acceptLanguage) {

        this.purchaseService.preparePurchase(id, acceptLanguage);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("admin/deliver/{id}")
    @Operation(
            responses = {@ApiResponse(responseCode = "204", description = "If the passed id is valid"),
                    @ApiResponse(responseCode = "400", description = "If the passed id is invalid")}
    )
    public ResponseEntity<Void> purchaseDelivery(
            @PathVariable("id") @Parameter(example = "1") Long id,
            @RequestHeader("Accept-Language") @Parameter(example = "pt-BR") String acceptLanguage) {

        this.purchaseService.deliverPurchase(id, acceptLanguage);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("admin/delete/{id}")
    @Operation(responses = {
            @ApiResponse(responseCode = "204", description = "When the passed id is valid"),
            @ApiResponse(responseCode = "400", description = "When the passed id is invalid")
    })
    public ResponseEntity<Void> deletePurchase(
            @PathVariable("id") @Parameter(example = "1") Long id) {

        this.purchaseService.deleteAPurchase(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "admin/delivered-purchases", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "Whenever called")
    })
    public ResponseEntity<List<PurchaseRestaurantResponseDTO>> deliveredPurchases() {

        return new ResponseEntity<>(this.purchaseService.findDeliveredPurchases(), HttpStatus.OK);
    }

    @PutMapping("admin/disable-finalization/{id}")
    @Operation(
            responses = {@ApiResponse(responseCode = "204", description = "If the passed id is valid"),
                    @ApiResponse(responseCode = "400", description = "If the passed id is invalid")}
    )
    public ResponseEntity<Void> disableFinalization(@PathVariable("id") @Parameter(example = "1") Long id) {

        this.purchaseService.disableFinalizationById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
