package com.webservicepizzariazeff.www.repository;

import com.webservicepizzariazeff.www.domain.PriceRating;
import com.webservicepizzariazeff.www.domain.Product;
import com.webservicepizzariazeff.www.domain.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByType(Pageable pageable, Type type);

    Page<Product> findByPriceRating(Pageable pageable, PriceRating priceRating);

    Page<Product> findByTypeAndPriceRating(Pageable pageable, Type type, PriceRating priceRating);
}
