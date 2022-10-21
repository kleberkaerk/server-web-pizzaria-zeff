package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.PriceRating;
import com.webservicepizzariazeff.www.domain.Product;
import com.webservicepizzariazeff.www.domain.Type;
import com.webservicepizzariazeff.www.repository.ProductRepository;
import com.webservicepizzariazeff.www.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    protected ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllNonPageable() {

        return this.productRepository.findAll();
    }

    public Page<Product> findAll(Pageable pageable) {

        return this.productRepository.findAll(pageable);
    }

    public Page<Product> findByType(Pageable pageable, String type) {

        if (Validator.checkThatStringIsNotAEnum(type, Type.values(), (enumeration, string) -> enumeration.name().equals(string))) {

            return Page.empty();
        }

        return this.productRepository.findByType(pageable, Type.valueOf(type));
    }

    public Page<Product> findByPriceRating(Pageable pageable, String priceRating) {

        if (Validator.checkThatStringIsNotAEnum(priceRating, PriceRating.values(), (enumeration, string) -> enumeration.name().equals(string))) {
            return Page.empty();
        }

        return this.productRepository.findByPriceRating(pageable, PriceRating.valueOf(priceRating));
    }

    public Page<Product> findByTypeAndPriceRating(Pageable pageable, String type, String priceRating) {

        if (Validator.checkThatStringIsNotAEnum(type, Type.values(), (enumeration, string) -> enumeration.name().equals(string)) ||
                Validator.checkThatStringIsNotAEnum(priceRating, PriceRating.values(), (enumeration, string) -> enumeration.name().equals(string))) {

            return Page.empty();
        }

        return this.productRepository.findByTypeAndPriceRating(pageable, Type.valueOf(type), PriceRating.valueOf(priceRating));
    }
}
