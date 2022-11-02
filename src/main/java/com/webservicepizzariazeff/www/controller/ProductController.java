package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.domain.Type;
import com.webservicepizzariazeff.www.dto.response.ProductResponseDTO;
import com.webservicepizzariazeff.www.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Map<Type, List<ProductResponseDTO>>> findProductsInStock() {

        return new ResponseEntity<>(this.productService.findAllProductsInStock(), HttpStatus.OK);
    }

    @GetMapping(value = "find-by-type", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProductResponseDTO>> findByType(Pageable pageable, @RequestParam() String type) {

        return new ResponseEntity<>(this.productService.findProductsByTypeAndInStock(pageable, type), HttpStatus.OK);
    }

    @GetMapping(value = "find-promotions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Type, List<ProductResponseDTO>>> findProductsInPromotion() {

        return new ResponseEntity<>(this.productService.findProductsInPromotionAndInStock(), HttpStatus.OK);
    }

    @GetMapping(value = "admin/find-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Boolean, List<ProductResponseDTO>>> findAllProducts() {

        return new ResponseEntity<>(this.productService.findAllGrouped(), HttpStatus.OK);
    }

    @PutMapping("admin/stock/{id}/{isStocked}")
    public ResponseEntity<Void> updateStock(@PathVariable Long id, @PathVariable boolean isStocked) {

        this.productService.updateProductStock(id, isStocked);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("admin/price/{id}/{newPrice}")
    public ResponseEntity<Void> updatePrice(@PathVariable Long id, @PathVariable BigDecimal newPrice) {

        this.productService.updatePriceOfProduct(id, newPrice);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PutMapping("admin/category/{id}/{category}")
}
