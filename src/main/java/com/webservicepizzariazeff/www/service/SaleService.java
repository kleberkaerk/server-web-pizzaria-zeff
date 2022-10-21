package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.*;
import com.webservicepizzariazeff.www.dto.request.SaleDTO;
import com.webservicepizzariazeff.www.repository.PaymentSimulationRepository;
import com.webservicepizzariazeff.www.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    public Long sale(UserDetails userDetails, SaleDTO saleDTO, String acceptLanguage) {

        String[] languageAndCountry = acceptLanguage.split("-");

        List<Product> filteredProducts = this.filterProductsById(this.productService.findAllNonPageable(), saleDTO.getProductsId());

        BigDecimal amount = this.calculateTotalAmount(filteredProducts);

        User user = Mapper.ofTheUserDetailsForUser(userDetails);

        Address userAddress = this.addressService.findById(saleDTO.getAddressId());

        Purchase savedPurchase = this.purchaseService.save(this.createPurchase(amount, saleDTO, user, userAddress));

        this.mapAndSavePurchasedProducts(filteredProducts, savedPurchase);

        if (saleDTO.isPaymentThroughTheWebsite()) {

            this.paymentSimulationRepository.payment(saleDTO.getCardDTO(), amount, languageAndCountry[0], languageAndCountry[1]);
        }

        return savedPurchase.getId();
    }

    private Purchase createPurchase(BigDecimal amount, SaleDTO saleDTO, User user, Address address) {

        return Purchase.PurchaseBuilder.builder()
                .amount(amount)
                .cardName(saleDTO.isPaymentThroughTheWebsite() ? saleDTO.getCardDTO().getNameOfCardHolder() : null)
                .isPaymentThroughTheWebsite(saleDTO.isPaymentThroughTheWebsite())
                .user(user)
                .address(address)
                .build();
    }

    private void mapAndSavePurchasedProducts(List<Product> products, Purchase purchase) {

        List<PurchasedProduct> purchasedProducts = products.stream()
                .map(product -> Mapper.ofTheProductForPurchasedProduct(product, purchase))
                .toList();

        purchasedProducts.forEach(this.purchasedProductService::save);
    }

    private List<Product> filterProductsById(List<Product> products, List<Long> productsId) {

        List<Product> filteredProducts = new ArrayList<>();

        productsId.forEach(productId -> products.stream()
                .filter(product -> product.getId().equals(productId))
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
