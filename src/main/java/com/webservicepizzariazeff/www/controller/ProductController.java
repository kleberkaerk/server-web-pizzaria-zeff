package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.domain.PriceRating;
import com.webservicepizzariazeff.www.domain.Type;
import com.webservicepizzariazeff.www.dto.request.ProductRequestDTO;
import com.webservicepizzariazeff.www.dto.response.ProductResponseDTO;
import com.webservicepizzariazeff.www.exception_handler.ExistingProductExceptionHandler;
import com.webservicepizzariazeff.www.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    protected ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "find-products", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(responses = @ApiResponse(responseCode = "200", description = "Whenever called",
            content = @Content(schema = @Schema(example = "Map<Type, List<ProductResponseDTO>>"))))
    public ResponseEntity<Map<Type, List<ProductResponseDTO>>> findProductsInStock() {

        return new ResponseEntity<>(this.productService.findAllProductsInStock(), HttpStatus.OK);
    }

    @GetMapping(value = "find-by-type", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(responses = @ApiResponse(responseCode = "200", description = "Whenever called"))
    public ResponseEntity<Page<ProductResponseDTO>> findByType(
            @ParameterObject Pageable pageable,
            @RequestParam @Parameter(example = "SWEET_ESFIHA") Type type) {

        return new ResponseEntity<>(this.productService.findProductsByTypeAndInStock(pageable, type), HttpStatus.OK);
    }

    @GetMapping(value = "find-promotions", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(responses = @ApiResponse(responseCode = "200", description = "Whenever called",
            content = @Content(schema = @Schema(example = "Map<Type, List<ProductResponseDTO>>"))))
    public ResponseEntity<Map<Type, List<ProductResponseDTO>>> findProductsInPromotion() {

        return new ResponseEntity<>(this.productService.findProductsInPromotionAndInStock(), HttpStatus.OK);
    }

    @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            responses = {@ApiResponse(responseCode = "200", description = "Whenever called")}
    )
    public ResponseEntity<Page<ProductResponseDTO>> searchProducts(
            @ParameterObject Pageable pageable,
            @RequestParam @Parameter(example = "foobar") String name) {

        return new ResponseEntity<>(this.productService.searchProductsByNameAndInStock(pageable, name), HttpStatus.OK);
    }

    @GetMapping(value = "admin/find-all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(responses = @ApiResponse(responseCode = "200", description = "Whenever called",
            content = @Content(schema = @Schema(example = "Map<Boolean, List<ProductResponseDTO>>"))))
    public ResponseEntity<Map<Boolean, List<ProductResponseDTO>>> findAllProducts() {

        return new ResponseEntity<>(this.productService.findAllGrouped(), HttpStatus.OK);
    }

    @PutMapping("admin/stock/{id}/{isStocked}")
    @Operation(
            responses = {@ApiResponse(responseCode = "204", description = "When the passed id is valid"),
                    @ApiResponse(responseCode = "400", description = "When the passed id is invalid")}
    )
    public ResponseEntity<Void> updateStock(
            @PathVariable @Parameter(example = "1") Long id,
            @PathVariable @Parameter(example = "false") boolean isStocked) {

        this.productService.updateProductStock(id, isStocked);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("admin/price/{id}/{newPrice}")
    @Operation(
            responses = {@ApiResponse(responseCode = "204", description = "When the passed id is valid"),
                    @ApiResponse(responseCode = "400", description = "When the passed id is invalid")}
    )
    public ResponseEntity<Void> updatePrice(
            @PathVariable @Parameter(example = "1") Long id,
            @PathVariable @Parameter(example = "10.55") BigDecimal newPrice) {

        this.productService.updatePriceOfProduct(id, newPrice);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("admin/rating/{id}/{priceRating}")
    @Operation(
            responses = {@ApiResponse(responseCode = "204", description = "When the passed id is valid"),
                    @ApiResponse(responseCode = "400", description = "When the passed id is invalid")}
    )
    public ResponseEntity<Void> updatePriceRating(
            @PathVariable @Parameter(example = "1") Long id,
            @PathVariable @Parameter(example = "PROMOTION") PriceRating priceRating) {

        this.productService.updatePriceRatingOfProduct(id, priceRating);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("admin/delete/{id}")
    @Operation(
            responses = {@ApiResponse(responseCode = "204", description = "When the passed id is valid"),
                    @ApiResponse(responseCode = "400", description = "When the passed id is invalid")}
    )
    public ResponseEntity<Void> delete(@PathVariable @Parameter(example = "1") Long id) {

        this.productService.deleteProduct(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "admin/register")
    @Operation(
            responses = {@ApiResponse(responseCode = "201", description = "If the request body is valid",
                    content = @Content(schema = @Schema(example = "1", type = "Integer"))),
                    @ApiResponse(responseCode = "409", description = "If the request body is invalid",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExistingProductExceptionHandler.class)))}
    )
    public ResponseEntity<Long> registerProduct(
            @RequestBody @Valid ProductRequestDTO productRequestDTO,
            @RequestHeader("Accept-Language") @Parameter(example = "pt-BR") String acceptLanguage) {

        return new ResponseEntity<>(this.productService.registerNewProduct(productRequestDTO, acceptLanguage), HttpStatus.CREATED);
    }
}
