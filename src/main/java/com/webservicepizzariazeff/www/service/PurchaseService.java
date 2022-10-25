package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.Purchase;
import com.webservicepizzariazeff.www.dto.response.PurchaseResponseDTOForUser;
import com.webservicepizzariazeff.www.repository.PurchaseRepository;
import com.webservicepizzariazeff.www.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Autowired
    protected PurchaseService(PurchaseRepository purchaseRepository) {

        this.purchaseRepository = purchaseRepository;
    }

    public Purchase save(Purchase purchase) {

        return this.purchaseRepository.save(purchase);
    }

    public Map<Boolean, List<PurchaseResponseDTOForUser>> findByAllPurchasesOfTheAnUser(UserDetails userDetails) {

        return this.filterAndGroup(this.purchaseRepository.findByUserAndIsActive(Mapper.ofTheUserDetailsForUser(userDetails), true));
    }

    private Map<Boolean, List<PurchaseResponseDTOForUser>> filterAndGroup(List<Purchase> purchases) {

        return purchases.stream()
                .map(Mapper::ofThePurchaseForPurchaseResponseDTOForUser)
                .sorted(Comparator.comparing(PurchaseResponseDTOForUser::getId).reversed())
                .collect(Collectors.groupingBy(PurchaseResponseDTOForUser::isDelivered));
    }
}
