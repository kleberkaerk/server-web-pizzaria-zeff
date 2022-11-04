package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.PriceRating;
import com.webservicepizzariazeff.www.domain.Product;
import com.webservicepizzariazeff.www.domain.Type;
import com.webservicepizzariazeff.www.dto.request.ProductRequestDTO;
import com.webservicepizzariazeff.www.dto.response.ProductResponseDTO;
import com.webservicepizzariazeff.www.exception.ExistingProductException;
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
import java.util.*;
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
                .map(Mapper::fromProductToProductResponseDTO)
                .sorted(Comparator.comparing(ProductResponseDTO::getName))
                .collect(Collectors.groupingBy(ProductResponseDTO::getType));
    }

    public Map<Type, List<ProductResponseDTO>> findAllProductsInStock() {

        List<Product> allProductsInStock = this.productRepository.findByIsStocked(true);

        return mapAndSortAndGroupProductByType(allProductsInStock);
    }

    public Page<ProductResponseDTO> findProductsByTypeAndInStock(Pageable pageable, Type type) {

        Page<Product> productsByType = this.productRepository.findByTypeAndIsStocked(pageable, type, true);

        return productsByType.map(Mapper::fromProductToProductResponseDTO);
    }

    public Map<Type, List<ProductResponseDTO>> findProductsInPromotionAndInStock() {

        List<Product> productsByPriceRating = this.productRepository.findByPriceRatingAndIsStocked(PriceRating.PROMOTION, true);

        return mapAndSortAndGroupProductByType(productsByPriceRating);
    }

    public Map<Boolean, List<ProductResponseDTO>> findAllGrouped() {

        return this.productRepository.findAll().stream()
                .map(Mapper::fromProductToProductResponseDTO)
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

    public void updatePriceRatingOfProduct(Long id, PriceRating priceRating) {

        Product productToBeUpdated = this.findById(id);

        this.productRepository.updatePriceRatingById(priceRating, productToBeUpdated.getId());
    }

    public void deleteProduct(Long id) {

        Product productToBeDeleted = this.findById(id);

        this.productRepository.deleteById(productToBeDeleted.getId());
    }

    public Long registerNewProduct(ProductRequestDTO productRequestDTO, String acceptLanguage) {

        Validator.validateAcceptLanguage(acceptLanguage);
        String[] languageAndCountry = Mapper.fromAcceptLanguageToStringArray(acceptLanguage);

        ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale(languageAndCountry[0], languageAndCountry[1]));

        Optional<Product> optionalProduct = this.productRepository.findByNameAndDescription(
                productRequestDTO.getName(),
                productRequestDTO.getDescription()
        );

        if (optionalProduct.isPresent()) {

            throw new ExistingProductException(messages.getString("existing.product"));
        }

        return this.productRepository.save(Mapper.fromProductRequestDTOToProduct(productRequestDTO)).getId();
    }
}
