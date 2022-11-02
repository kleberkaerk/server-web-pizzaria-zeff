package com.webservicepizzariazeff.www.repository;

import com.webservicepizzariazeff.www.domain.PriceRating;
import com.webservicepizzariazeff.www.domain.Product;
import com.webservicepizzariazeff.www.domain.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsStocked(boolean isStocked);

    Page<Product> findByTypeAndIsStocked(Pageable pageable, Type type, boolean isStocked);

    List<Product> findByPriceRatingAndIsStocked(PriceRating priceRating, boolean isStocked);
}
