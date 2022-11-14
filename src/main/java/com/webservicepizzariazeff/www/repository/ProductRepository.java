package com.webservicepizzariazeff.www.repository;

import com.webservicepizzariazeff.www.domain.PriceRating;
import com.webservicepizzariazeff.www.domain.Product;
import com.webservicepizzariazeff.www.domain.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsStocked(boolean isStocked);

    Page<Product> findByTypeAndIsStocked(Pageable pageable, Type type, boolean isStocked);

    List<Product> findByPriceRatingAndIsStocked(PriceRating priceRating, boolean isStocked);

    Page<Product> findByNameContainsAndIsStockedAllIgnoreCase(String name, boolean isStocked, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Product p set p.isStocked = :isStocked where p.id = :id")
    void updateIsStockedById(@Param("isStocked") boolean isStocked, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update Product p set p.price = :price where p.id = :id")
    void updatePriceById(@Param("price") BigDecimal price, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update Product p set p.priceRating = :priceRating where p.id = :id")
    void updatePriceRatingById(@Param("priceRating") PriceRating priceRating, @Param("id") Long id);

    Optional<Product> findByNameAndDescription(String name, String description);
}
