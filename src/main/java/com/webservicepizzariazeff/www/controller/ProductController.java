package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.domain.Product;
import com.webservicepizzariazeff.www.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    protected ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "find-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Product>> findAll(Pageable pageable) {

        return new ResponseEntity<>(this.productService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "find-by-type", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Product>> findByType(Pageable pageable, @RequestParam() String type) {

        return new ResponseEntity<>(this.productService.findByType(pageable, type), HttpStatus.OK);
    }

    @GetMapping(value = "find-by-price-rating", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Product>> findByPriceRating(Pageable pageable, @RequestParam() String priceRating) {

        return new ResponseEntity<>(this.productService.findByPriceRating(pageable, priceRating), HttpStatus.OK);
    }

    @GetMapping(value = "find-by-type-and-price-rating", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Product>> findByTypeAndPriceRating(
            Pageable pageable,
            @RequestParam() String type,
            @RequestParam() String priceRating) {

        return new ResponseEntity<>(this.productService.findByTypeAndPriceRating(pageable, type, priceRating), HttpStatus.OK);

    }
}
