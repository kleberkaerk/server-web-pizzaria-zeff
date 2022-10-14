package com.webservicepizzariazeff.www.util;

import com.webservicepizzariazeff.www.domain.Address;
import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.request.AddressRequestDTO;
import com.webservicepizzariazeff.www.dto.response.AddressResponseDTO;
import org.springframework.security.core.userdetails.UserDetails;

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
}
