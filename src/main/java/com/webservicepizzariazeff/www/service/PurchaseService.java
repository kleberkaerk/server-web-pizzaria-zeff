package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.Purchase;
import com.webservicepizzariazeff.www.domain.PurchasedProduct;
import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.response.PurchaseRestaurantResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchaseUserResponseDTO;
import com.webservicepizzariazeff.www.exception.PurchaseFinishedException;
import com.webservicepizzariazeff.www.repository.PurchaseRepository;
import com.webservicepizzariazeff.www.util.Mapper;
import com.webservicepizzariazeff.www.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final PurchasedProductService purchasedProductService;

    private ResourceBundle messages;

    private static final String RESOURCE_BUNDLE_NAME = "messages";

    private static final String INVALID_ID = "invalid.id";

    @Autowired
    protected PurchaseService(PurchaseRepository purchaseRepository, PurchasedProductService purchasedProductService) {

        this.purchaseRepository = purchaseRepository;
        this.purchasedProductService = purchasedProductService;
    }

    public Purchase save(Purchase purchase) {

        return this.purchaseRepository.save(purchase);
    }

    public Map<Boolean, List<PurchaseUserResponseDTO>> findByAllPurchasesOfTheAnUser(UserDetails userDetails) {

        User user = Mapper.fromUserDetailsToUser(userDetails);

        List<Purchase> userActivePurchases = this.purchaseRepository.findByUserAndIsActive(user, true);

        return userActivePurchases.stream()
                .map(Mapper::fromPurchaseToPurchaseUserResponseDTO)
                .sorted(Comparator.comparing(PurchaseUserResponseDTO::getId).reversed())
                .collect(Collectors.groupingBy(PurchaseUserResponseDTO::isDelivered));
    }

    public void cancelPurchaseOfTheUser(UserDetails userDetails, Long id, String acceptLanguage) {

        Validator.validateAcceptLanguage(acceptLanguage);
        String[] languageAndCountry = Mapper.fromAcceptLanguageToStringArray(acceptLanguage);

        messages = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, new Locale(languageAndCountry[0], languageAndCountry[1]));

        User user = Mapper.fromUserDetailsToUser(userDetails);

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
                .map(Mapper::fromPurchaseToPurchaseRestaurantResponseDTO)
                .sorted(Comparator.comparing(PurchaseRestaurantResponseDTO::getId))
                .collect(Collectors.groupingBy(PurchaseRestaurantResponseDTO::isActive));
    }

    private Purchase findById(Long id, String reason) {

        return this.purchaseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, reason));
    }

    public void preparePurchase(Long id, String acceptLanguage) {

        Validator.validateAcceptLanguage(acceptLanguage);
        String[] languageAndCountry = Mapper.fromAcceptLanguageToStringArray(acceptLanguage);

        messages = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, new Locale(languageAndCountry[0], languageAndCountry[1]));

        Purchase purchaseToBePrepared = this.findById(id, messages.getString(INVALID_ID));

        if (!purchaseToBePrepared.isActive() || purchaseToBePrepared.isDelivered()) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messages.getString("invalid.operation"));
        }

        this.purchaseRepository.updateIsFinishedById(true, id);
    }

    public void deliverPurchase(Long id, String acceptLanguage) {

        Validator.validateAcceptLanguage(acceptLanguage);
        String[] languageAndCountry = Mapper.fromAcceptLanguageToStringArray(acceptLanguage);

        messages = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, new Locale(languageAndCountry[0], languageAndCountry[1]));

        Purchase purchaseToBeDelivered = this.findById(id, messages.getString(INVALID_ID));

        if (!purchaseToBeDelivered.isActive() || !purchaseToBeDelivered.isFinished()) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messages.getString("invalid.operation"));
        }

        this.purchaseRepository.updateIsDeliveredById(true, id);
    }

    private void deletePurchasedProducts(List<PurchasedProduct> purchasedProducts) {

        purchasedProducts
                .forEach(purchasedProduct -> this.purchasedProductService.delete(purchasedProduct.getId()));
    }

    @Transactional
    public void deleteAPurchase(Long id) {

        messages = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, new Locale("en", "US"));

        Purchase purchaseToBeDeleted = this.findById(id, messages.getString(INVALID_ID));

        if (purchaseToBeDeleted.isActive() || purchaseToBeDeleted.isFinished() || purchaseToBeDeleted.isDelivered()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        this.deletePurchasedProducts(purchaseToBeDeleted.getPurchasedProducts());

        this.purchaseRepository.deleteById(id);
    }

    public List<PurchaseRestaurantResponseDTO> findDeliveredPurchases() {

        List<Purchase> deliveredPurchases = this.purchaseRepository.findDistinctByIsDelivered(true);

        return deliveredPurchases.stream()
                .sorted(Comparator.comparing(Purchase::getId).reversed())
                .map(Mapper::fromPurchaseToPurchaseRestaurantResponseDTO)
                .limit(50L)
                .toList();
    }

    public void disableDeliveryById(Long id) {

        messages = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, new Locale("en", "US"));

        this.findById(id, messages.getString(INVALID_ID));

        this.purchaseRepository.updateIsDeliveredById(false, id);
    }
}
