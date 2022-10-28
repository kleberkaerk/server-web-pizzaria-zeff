package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.Purchase;
import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.response.PurchaseRestaurantResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchaseUserResponseDTO;
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
@SuppressWarnings("java:S115")
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private ResourceBundle messages;

    private static final String resourceBundleName = "messages";

    @Autowired
    protected PurchaseService(PurchaseRepository purchaseRepository) {

        this.purchaseRepository = purchaseRepository;
    }

    public Purchase save(Purchase purchase) {

        return this.purchaseRepository.save(purchase);
    }

    public Map<Boolean, List<PurchaseUserResponseDTO>> findByAllPurchasesOfTheAnUser(UserDetails userDetails) {

        User user = Mapper.ofTheUserDetailsForUser(userDetails);

        List<Purchase> userActivePurchases = this.purchaseRepository.findByUserAndIsActive(user, true);

        return userActivePurchases.stream()
                .map(Mapper::ofThePurchaseForPurchaseUserResponseDTO)
                .sorted(Comparator.comparing(PurchaseUserResponseDTO::getId).reversed())
                .collect(Collectors.groupingBy(PurchaseUserResponseDTO::isDelivered));
    }

    public void cancelPurchaseOfTheUser(UserDetails userDetails, Long id, String acceptLanguage) {

        String[] languageAndCountry = acceptLanguage.split("-");

        messages = ResourceBundle.getBundle(resourceBundleName, new Locale(languageAndCountry[0], languageAndCountry[1]));

        User user = Mapper.ofTheUserDetailsForUser(userDetails);

        Purchase purchaseToBeCanceled = this.purchaseRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        if (purchaseToBeCanceled.isFinished() || purchaseToBeCanceled.isDelivered()) {
            throw new PurchaseFinishedException(messages.getString("purchase.finalized"));
        }

        this.purchaseRepository.updateIsActiveById(false, id);
    }

    public Map<Boolean, List<PurchaseRestaurantResponseDTO>> findByAllUsersPurchases() {

        List<Purchase> purchasesIsNotDelivered = this.purchaseRepository.findByIsDelivered(false);

        return purchasesIsNotDelivered.stream()
                .map(Mapper::ofThePurchaseForPurchaseRestaurantResponseDTO)
                .sorted(Comparator.comparing(PurchaseRestaurantResponseDTO::getId))
                .collect(Collectors.groupingBy(PurchaseRestaurantResponseDTO::isActive));
    }

    private Purchase findById(Long id, String reason) {

        return this.purchaseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, reason));
    }

    public void preparePurchase(Long id, String acceptLanguage) {

        String[] languageAndCountry = acceptLanguage.split("-");

        messages = ResourceBundle.getBundle(resourceBundleName, new Locale(languageAndCountry[0], languageAndCountry[1]));

        Purchase purchaseToBePrepared = this.findById(id, messages.getString("invalid.id"));

        if (!purchaseToBePrepared.isActive() || purchaseToBePrepared.isDelivered()) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messages.getString("invalid.operation"));
        }

        this.purchaseRepository.updateIsFinishedById(true, id);
    }

    public void deliverPurchase(Long id, String acceptLanguage) {

        String[] languageAndCountry = acceptLanguage.split("-");

        messages = ResourceBundle.getBundle(resourceBundleName, new Locale(languageAndCountry[0], languageAndCountry[1]));

        Purchase purchaseToBeDelivered = this.findById(id, messages.getString("invalid.id"));

        if (!purchaseToBeDelivered.isActive() || !purchaseToBeDelivered.isFinished()) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messages.getString("invalid.operation"));
        }

        this.purchaseRepository.updateIsDeliveredById(true, id);
    }
}
