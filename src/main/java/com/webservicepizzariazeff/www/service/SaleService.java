package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.*;
import com.webservicepizzariazeff.www.dto.request.SaleRequestDTO;
import com.webservicepizzariazeff.www.exception.InvalidCardException;
import com.webservicepizzariazeff.www.repository.PaymentSimulationRepository;
import com.webservicepizzariazeff.www.util.Mapper;
import com.webservicepizzariazeff.www.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Service
public class SaleService {

    private final PurchaseService purchaseService;

    private final ProductService productService;

    private final AddressService addressService;

    private final PaymentSimulationRepository paymentSimulationRepository;

    private final PurchasedProductService purchasedProductService;

    @Autowired
    protected SaleService(
            PurchaseService purchaseService,
            ProductService productService,
            AddressService addressService,
            PaymentSimulationRepository paymentSimulationRepository,
            PurchasedProductService purchasedProductService
    ) {

        this.purchaseService = purchaseService;
        this.productService = productService;
        this.addressService = addressService;
        this.paymentSimulationRepository = paymentSimulationRepository;
        this.purchasedProductService = purchasedProductService;
    }

    @Transactional
    public Long sale(UserDetails userDetails, SaleRequestDTO saleRequestDTO, String acceptLanguage) {

        Validator.validateAcceptLanguage(acceptLanguage);
        String[] languageAndCountry = Mapper.fromAcceptLanguageToStringArray(acceptLanguage);

        List<Product> filteredProducts = this.filterProductsById(this.productService.findAll(), saleRequestDTO.getProductsId());

        BigDecimal amount = this.calculateTotalAmount(filteredProducts);

        User user = Mapper.fromUserDetailsToUser(userDetails);

        Address userAddress = this.addressService.findById(saleRequestDTO.getAddressId());

        this.validatePaymentForm(saleRequestDTO, languageAndCountry[0], languageAndCountry[1]);

        Purchase savedPurchase = this.purchaseService.save(this.createPurchase(amount, saleRequestDTO, user, userAddress));

        this.mapAndSavePurchasedProducts(filteredProducts, savedPurchase);

        if (saleRequestDTO.isPaymentThroughTheWebsite()) {

            this.paymentSimulationRepository.payment(saleRequestDTO.getCardRequestDTO(), amount, languageAndCountry[0], languageAndCountry[1]);
        }

        return savedPurchase.getId();
    }

    private void validatePaymentForm(SaleRequestDTO saleRequestDTO, String language, String country) {

        ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale(language, country));

        if (saleRequestDTO.isPaymentThroughTheWebsite() && saleRequestDTO.getCardRequestDTO() == null){
            throw new InvalidCardException(messages.getString("invalid.card"));
        }
    }

    private Purchase createPurchase(BigDecimal amount, SaleRequestDTO saleRequestDTO, User user, Address address) {

        return Purchase.PurchaseBuilder.builder()
                .amount(amount)
                .cardName(saleRequestDTO.isPaymentThroughTheWebsite() ? saleRequestDTO.getCardRequestDTO().getNameOfCardHolder() : null)
                .isPaymentThroughTheWebsite(saleRequestDTO.isPaymentThroughTheWebsite())
                .user(user)
                .address(address)
                .build();
    }

    private void mapAndSavePurchasedProducts(List<Product> products, Purchase purchase) {

        List<PurchasedProduct> purchasedProducts = products.stream()
                .map(product -> Mapper.fromProductToPurchasedProduct(product, purchase))
                .toList();

        purchasedProducts.forEach(this.purchasedProductService::save);
    }

    private List<Product> filterProductsById(List<Product> products, List<Long> productsId) {

        List<Product> filteredProducts = new ArrayList<>();

        productsId.forEach(productId -> products.stream()
                .filter(product -> product.getId().equals(productId))
                .filter(Product::isStocked)
                .findFirst().ifPresent(filteredProducts::add));

        return filteredProducts;
    }

    private BigDecimal calculateTotalAmount(List<Product> products) {

        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal::add)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
