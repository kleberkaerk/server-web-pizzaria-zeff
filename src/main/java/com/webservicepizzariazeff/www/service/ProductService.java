package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.PriceRating;
import com.webservicepizzariazeff.www.domain.Product;
import com.webservicepizzariazeff.www.domain.Type;
import com.webservicepizzariazeff.www.dto.response.ProductResponseDTO;
import com.webservicepizzariazeff.www.repository.ProductRepository;
import com.webservicepizzariazeff.www.util.Mapper;
import com.webservicepizzariazeff.www.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    protected ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {

        return this.productRepository.findAll();
    }

    private Map<Type, List<ProductResponseDTO>> mapAndSortAndGroupProductByType(List<Product> products) {

        return products.stream()
                .map(Mapper::ofTheProductToProductResponseDTO)
                .sorted(Comparator.comparing(ProductResponseDTO::getName))
                .collect(Collectors.groupingBy(ProductResponseDTO::getType));
    }

    public Map<Type, List<ProductResponseDTO>> findAllProductsInStock() {

        List<Product> allProductsInStock = this.productRepository.findByIsStocked(true);

        return mapAndSortAndGroupProductByType(allProductsInStock);
    }

    public Page<ProductResponseDTO> findProductsByTypeAndInStock(Pageable pageable, String type) {

        if (Validator.checkThatStringIsNotAEnum(type, Type.values(), (enumeration, string) -> enumeration.name().equals(string))) {

            return Page.empty();
        }

        Page<Product> productsByType = this.productRepository.findByTypeAndIsStocked(pageable, Type.valueOf(type), true);

        return productsByType.map(Mapper::ofTheProductToProductResponseDTO);
    }

    public Map<Type, List<ProductResponseDTO>> findProductsInPromotionAndInStock() {

        List<Product> productsByPriceRating = this.productRepository.findByPriceRatingAndIsStocked(PriceRating.PROMOTION, true);

        return mapAndSortAndGroupProductByType(productsByPriceRating);
    }

    public Map<Boolean, List<ProductResponseDTO>> findAllGrouped() {

        return this.productRepository.findAll().stream()
                .map(Mapper::ofTheProductToProductResponseDTO)
                .sorted(Comparator.comparing(ProductResponseDTO::getPriceRating))
                .collect(Collectors.groupingBy(ProductResponseDTO::isStocked));
    }

    private Product findById(Long id) {

        return this.productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public void updateProductStock(Long id, boolean isStocked) {

        Product productToBeUpdated = this.findById(id);

        this.productRepository.updateIsStockedById(isStocked, productToBeUpdated.getId());
    }

    public void updatePriceOfProduct(Long id, BigDecimal newPrice) {

        Product productToBeUpdated = this.findById(id);

        this.productRepository.updatePriceById(newPrice, productToBeUpdated.getId());
    }
}
