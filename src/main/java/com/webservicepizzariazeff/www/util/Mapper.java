package com.webservicepizzariazeff.www.util;

import com.webservicepizzariazeff.www.domain.Address;
import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.request.AddressDTO;
import org.springframework.security.core.userdetails.UserDetails;

public class Mapper {

    private Mapper() {
    }

    public static Address ofTheAddressDTOForAddress(AddressDTO addressDTO, User user) {

        return Address.AddressBuilder.builder()
                .number(addressDTO.getNumber())
                .road(addressDTO.getRoad())
                .district(addressDTO.getDistrict())
                .city(addressDTO.getCity())
                .state(addressDTO.getState())
                .user(user)
                .build();
    }

    public static User ofTheUserDetailsForUser(UserDetails userDetails) {

        return (User) userDetails;
    }
}
