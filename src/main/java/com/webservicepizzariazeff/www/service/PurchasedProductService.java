package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.PurchasedProduct;
import com.webservicepizzariazeff.www.repository.PurchasedProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchasedProductService {

    private final PurchasedProductRepository purchasedProductRepository;

    @Autowired
    protected PurchasedProductService(PurchasedProductRepository purchasedProductRepository) {

        this.purchasedProductRepository = purchasedProductRepository;
    }

    public void save(PurchasedProduct purchase) {

        this.purchasedProductRepository.save(purchase);
    }
}
