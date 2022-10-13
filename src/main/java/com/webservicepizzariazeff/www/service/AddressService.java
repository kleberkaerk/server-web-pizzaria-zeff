package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.Address;
import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.request.AddressDTO;
import com.webservicepizzariazeff.www.exception.ExistingAddressException;
import com.webservicepizzariazeff.www.repository.AddressRepository;
import com.webservicepizzariazeff.www.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    protected AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    private Optional<Address> searchForExistingAddressInTheDatabase(AddressDTO addressDTO, User user) {

        return this.addressRepository.findByNumberAndRoadAndDistrictAndCityAndStateAndUser(
                addressDTO.getNumber(),
                addressDTO.getRoad(),
                addressDTO.getDistrict(),
                addressDTO.getCity(),
                addressDTO.getState(),
                user
        );
    }

    public Long registerAddress(UserDetails userDetails, AddressDTO addressDTO, String acceptLanguage) {

        String[] languageAndCountry = acceptLanguage.split("-");

        ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale(languageAndCountry[0], languageAndCountry[1]));

        User user = Mapper.ofTheUserDetailsForUser(userDetails);

        Optional<Address> optionalAddress = this.searchForExistingAddressInTheDatabase(addressDTO, user);

        if (optionalAddress.isPresent()) {
            throw new ExistingAddressException(messages.getString("existing.address"));
        }

        Address addressToBeSaved = Mapper.ofTheAddressDTOForAddress(addressDTO, user);

        return this.addressRepository.save(addressToBeSaved).getId();
    }
}
