package com.webservicepizzariazeff.www.repository;

import com.webservicepizzariazeff.www.domain.PurchasedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasedProductRepository extends JpaRepository<PurchasedProduct, Long> {
}
