package com.webservicepizzariazeff.www.util;

import com.webservicepizzariazeff.www.domain.*;
import com.webservicepizzariazeff.www.dto.request.AddressRequestDTO;
import com.webservicepizzariazeff.www.dto.response.AddressResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchaseRestaurantResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchaseUserResponseDTO;
import com.webservicepizzariazeff.www.dto.response.PurchasedProductResponseDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class Mapper {

    private Mapper() {
    }

    public static Address ofTheAddressRequestDTOForAddress(AddressRequestDTO addressRequestDTO, User user) {

        return Address.AddressBuilder.builder()
                .number(addressRequestDTO.getNumber())
                .road(addressRequestDTO.getRoad())
                .district(addressRequestDTO.getDistrict())
                .city(addressRequestDTO.getCity())
                .state(addressRequestDTO.getState())
                .user(user)
                .build();
    }

    public static User ofTheUserDetailsForUser(UserDetails userDetails) {

        return (User) userDetails;
    }

    public static AddressResponseDTO ofTheAddressForAddressResponseDTO(Address address) {

        return AddressResponseDTO.AddressResponseDTOBuilder.builder()
                .id(address.getId())
                .number(address.getNumber())
                .road(address.getRoad())
                .district(address.getDistrict())
                .city(address.getCity())
                .state(address.getState())
                .build();
    }

    public static PurchasedProduct ofTheProductForPurchasedProduct(Product product, Purchase purchase) {

        return PurchasedProduct.PurchasedProductBuilder.builder()
                .name(product.getName())
                .purchase(purchase)
                .build();
    }

    private static List<PurchasedProductResponseDTO> ofThePurchasedProductListForPurchasedProductResponseDTOList(List<PurchasedProduct> purchasedProducts) {

        return purchasedProducts.stream()
                .map(purchasedProduct -> PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder
                        .builder()
                        .name(purchasedProduct.getName())
                        .build())
                .toList();
    }

    public static PurchaseUserResponseDTO ofThePurchaseForPurchaseUserResponseDTO(Purchase purchase) {

        return PurchaseUserResponseDTO.PurchaseUserResponseDTOBuilder.builder()
                .id(purchase.getId())
                .amount(purchase.getAmount())
                .dateAndTime(purchase.getDateAndTime())
                .cardName(purchase.getCardName())
                .isActive(purchase.isActive())
                .isFinished(purchase.isFinished())
                .isDelivered(purchase.isDelivered())
                .isPaymentThroughTheWebsite(purchase.isPaymentThroughTheWebsite())
                .purchasedProductResponseDTOS(
                        Mapper.ofThePurchasedProductListForPurchasedProductResponseDTOList(purchase.getPurchasedProducts())
                )
                .addressResponseDTO(Mapper.ofTheAddressForAddressResponseDTO(purchase.getAddress()))
                .build();
    }

    public static PurchaseRestaurantResponseDTO ofThePurchaseForPurchaseRestaurantResponseDTO(Purchase purchase) {

        return PurchaseRestaurantResponseDTO.PurchaseRestaurantResponseDTOBuilder.builder()
                .id(purchase.getId())
                .clientName(purchase.getUser().getName())
                .isActive(purchase.isActive())
                .isFinished(purchase.isFinished())
                .isDelivered(purchase.isDelivered())
                .isPaymentThroughTheWebsite(purchase.isPaymentThroughTheWebsite())
                .purchasedProductResponseDTOS(
                        Mapper.ofThePurchasedProductListForPurchasedProductResponseDTOList(purchase.getPurchasedProducts())
                )
                .addressResponseDTO(Mapper.ofTheAddressForAddressResponseDTO(purchase.getAddress()))
                .build();
    }
}
