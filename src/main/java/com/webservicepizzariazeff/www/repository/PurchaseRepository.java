package com.webservicepizzariazeff.www.repository;

import com.webservicepizzariazeff.www.domain.Purchase;
import com.webservicepizzariazeff.www.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("SELECT DISTINCT p FROM Purchase p JOIN p.user u ON u = :user LEFT JOIN FETCH p.purchasedProducts JOIN FETCH p.user JOIN FETCH p.address where p.isActive = :isActive")
    List<Purchase> findByUserAndIsActive(@Param("user") User user, boolean isActive);
}
