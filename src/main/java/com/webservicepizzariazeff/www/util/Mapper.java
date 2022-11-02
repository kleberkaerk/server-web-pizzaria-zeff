package com.webservicepizzariazeff.www.util;

import com.webservicepizzariazeff.www.domain.*;
import com.webservicepizzariazeff.www.dto.request.AddressRequestDTO;
import com.webservicepizzariazeff.www.dto.response.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class Mapper {

    private Mapper() {
    }

    public static Address ofTheAddressRequestDTOToAddress(AddressRequestDTO addressRequestDTO, User user) {

        return Address.AddressBuilder.builder()
                .number(addressRequestDTO.getNumber())
                .road(addressRequestDTO.getRoad())
                .district(addressRequestDTO.getDistrict())
                .city(addressRequestDTO.getCity())
                .state(addressRequestDTO.getState())
                .user(user)
                .build();
    }

    public static User ofTheUserDetailsToUser(UserDetails userDetails) {

        return (User) userDetails;
    }

    public static AddressResponseDTO ofTheAddressToAddressResponseDTO(Address address) {

        return AddressResponseDTO.AddressResponseDTOBuilder.builder()
                .id(address.getId())
                .number(address.getNumber())
                .road(address.getRoad())
                .district(address.getDistrict())
                .city(address.getCity())
                .state(address.getState())
                .build();
    }

    public static PurchasedProduct ofTheProductToPurchasedProduct(Product product, Purchase purchase) {

        return PurchasedProduct.PurchasedProductBuilder.builder()
                .name(product.getName())
                .purchase(purchase)
                .build();
    }

    private static List<PurchasedProductResponseDTO> ofThePurchasedProductListToPurchasedProductResponseDTOList(List<PurchasedProduct> purchasedProducts) {

        return purchasedProducts.stream()
                .map(purchasedProduct -> PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder
                        .builder()
                        .name(purchasedProduct.getName())
                        .build())
                .toList();
    }

    public static PurchaseUserResponseDTO ofThePurchaseToPurchaseUserResponseDTO(Purchase purchase) {

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
                        Mapper.ofThePurchasedProductListToPurchasedProductResponseDTOList(purchase.getPurchasedProducts())
                )
                .addressResponseDTO(Mapper.ofTheAddressToAddressResponseDTO(purchase.getAddress()))
                .build();
    }

    public static PurchaseRestaurantResponseDTO ofThePurchaseToPurchaseRestaurantResponseDTO(Purchase purchase) {

        return PurchaseRestaurantResponseDTO.PurchaseRestaurantResponseDTOBuilder.builder()
                .id(purchase.getId())
                .clientName(purchase.getUser().getName())
                .isActive(purchase.isActive())
                .isFinished(purchase.isFinished())
                .isDelivered(purchase.isDelivered())
                .isPaymentThroughTheWebsite(purchase.isPaymentThroughTheWebsite())
                .purchasedProductResponseDTOS(
                        Mapper.ofThePurchasedProductListToPurchasedProductResponseDTOList(purchase.getPurchasedProducts())
                )
                .addressResponseDTO(Mapper.ofTheAddressToAddressResponseDTO(purchase.getAddress()))
                .build();
    }

    public static ProductResponseDTO ofTheProductToProductResponseDTO(Product product){

        return ProductResponseDTO.ProductResponseDTOBuilder.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .type(product.getType())
                .priceRating(product.getPriceRating())
                .isStocked(product.isStocked())
                .image(product.getImage())
                .build();
    }
}
