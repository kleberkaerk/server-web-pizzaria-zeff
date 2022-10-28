package com.webservicepizzariazeff.www.repository;

import com.webservicepizzariazeff.www.domain.Purchase;
import com.webservicepizzariazeff.www.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("SELECT DISTINCT p FROM Purchase p JOIN p.user u ON u = :user LEFT JOIN FETCH p.purchasedProducts JOIN FETCH p.user JOIN FETCH p.address where p.isActive = :isActive")
    List<Purchase> findByUserAndIsActive(@Param("user") User user, boolean isActive);

    @Query("SELECT DISTINCT p FROM Purchase p LEFT JOIN FETCH p.purchasedProducts WHERE p.id = :id")
    Optional<Purchase> findById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update Purchase p set p.isActive = :isActive where p.id = :id")
    void updateIsActiveById(@Param("isActive") boolean isActive, @Param("id") Long id);

    @Query("select DISTINCT p from Purchase p LEFT JOIN FETCH p.purchasedProducts where p.isDelivered = :isDelivered")
    List<Purchase> findByIsDelivered(@Param("isDelivered") boolean isDelivered);

    @Transactional
    @Modifying
    @Query("update Purchase p set p.isFinished = :isFinished where p.id = :id")
    void updateIsFinishedById(@Param("isFinished") boolean isFinished, @Param("id") Long id);
}
