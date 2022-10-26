package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.Purchase;
import com.webservicepizzariazeff.www.dto.response.PurchaseResponseDTOForUser;
import com.webservicepizzariazeff.www.exception.PurchaseFinishedException;
import com.webservicepizzariazeff.www.repository.PurchaseRepository;
import com.webservicepizzariazeff.www.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
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

    public void cancelPurchaseOfTheUser(Long id, String acceptLanguage) {

        String[] languageAndCountry = acceptLanguage.split("-");

        ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale(languageAndCountry[0], languageAndCountry[1]));

        Purchase purchaseToBeCanceled = this.purchaseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        if (purchaseToBeCanceled.isFinished() || purchaseToBeCanceled.isDelivered()) {
            throw new PurchaseFinishedException(messages.getString("purchase.finalized"));
        }

        this.purchaseRepository.updateIsActiveById(false, id);
    }
}
