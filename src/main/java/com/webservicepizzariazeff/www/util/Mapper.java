package com.webservicepizzariazeff.www.util;

import com.webservicepizzariazeff.www.domain.*;
import com.webservicepizzariazeff.www.dto.request.AddressRequestDTO;
import com.webservicepizzariazeff.www.dto.request.ProductRequestDTO;
import com.webservicepizzariazeff.www.dto.response.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class Mapper {

    private Mapper() {
    }

    public static Address fromAddressRequestDTOToAddress(AddressRequestDTO addressRequestDTO, User user) {

        return Address.AddressBuilder.builder()
                .number(addressRequestDTO.getNumber())
                .road(addressRequestDTO.getRoad())
                .district(addressRequestDTO.getDistrict())
                .city(addressRequestDTO.getCity())
                .state(addressRequestDTO.getState())
                .user(user)
                .build();
    }

    public static User fromUserDetailsToUser(UserDetails userDetails) {

        return (User) userDetails;
    }

    public static AddressResponseDTO fromAddressToAddressResponseDTO(Address address) {

        return AddressResponseDTO.AddressResponseDTOBuilder.builder()
                .id(address.getId())
                .number(address.getNumber())
                .road(address.getRoad())
                .district(address.getDistrict())
                .city(address.getCity())
                .state(address.getState())
                .build();
    }

    public static PurchasedProduct fromProductToPurchasedProduct(Product product, Purchase purchase) {

        return PurchasedProduct.PurchasedProductBuilder.builder()
                .name(product.getName())
                .purchase(purchase)
                .build();
    }

    private static List<PurchasedProductResponseDTO> fromPurchasedProductListToPurchasedProductResponseDTOList(List<PurchasedProduct> purchasedProducts) {

        return purchasedProducts.stream()
                .map(purchasedProduct -> PurchasedProductResponseDTO.PurchasedProductResponseDTOBuilder
                        .builder()
                        .name(purchasedProduct.getName())
                        .build())
                .toList();
    }

    public static PurchaseUserResponseDTO fromPurchaseToPurchaseUserResponseDTO(Purchase purchase) {

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
                        Mapper.fromPurchasedProductListToPurchasedProductResponseDTOList(purchase.getPurchasedProducts())
                )
                .addressResponseDTO(Mapper.fromAddressToAddressResponseDTO(purchase.getAddress()))
                .build();
    }

    public static PurchaseRestaurantResponseDTO fromPurchaseToPurchaseRestaurantResponseDTO(Purchase purchase) {

        return PurchaseRestaurantResponseDTO.PurchaseRestaurantResponseDTOBuilder.builder()
                .id(purchase.getId())
                .clientName(purchase.getUser().getName())
                .isActive(purchase.isActive())
                .isFinished(purchase.isFinished())
                .isDelivered(purchase.isDelivered())
                .isPaymentThroughTheWebsite(purchase.isPaymentThroughTheWebsite())
                .purchasedProductResponseDTOS(
                        Mapper.fromPurchasedProductListToPurchasedProductResponseDTOList(purchase.getPurchasedProducts())
                )
                .addressResponseDTO(Mapper.fromAddressToAddressResponseDTO(purchase.getAddress()))
                .build();
    }

    public static ProductResponseDTO fromProductToProductResponseDTO(Product product) {

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

    public static Product fromProductRequestDTOToProduct(ProductRequestDTO productRequestDTO) {

        return Product.ProductBuilder.builder()
                .name(productRequestDTO.getName())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .type(productRequestDTO.getType())
                .priceRating(productRequestDTO.getPriceRating())
                .image(createImageNameToProduct(productRequestDTO.getName()))
                .build();
    }

    private static String createImageNameToProduct(String productName) {

        return productName.toLowerCase().replace(" ", "-") + ".png";
    }

    public static String[] fromAcceptLanguageToStringArray(String acceptLanguage) {

        return acceptLanguage.split("-");
    }
}
