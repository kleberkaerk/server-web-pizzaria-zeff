package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.Address;
import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.request.AddressRequestDTO;
import com.webservicepizzariazeff.www.dto.response.AddressResponseDTO;
import com.webservicepizzariazeff.www.exception.ExistingAddressException;
import com.webservicepizzariazeff.www.repository.AddressRepository;
import com.webservicepizzariazeff.www.util.Mapper;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

    private Optional<Address> searchForExistingAddressInTheDatabase(AddressRequestDTO addressRequestDTO, User user) {

        return this.addressRepository.findByNumberAndRoadAndDistrictAndCityAndStateAndUser(
                addressRequestDTO.getNumber(),
                addressRequestDTO.getRoad(),
                addressRequestDTO.getDistrict(),
                addressRequestDTO.getCity(),
                addressRequestDTO.getState(),
                user
        );
    }

    public Long registerAddress(UserDetails userDetails, AddressRequestDTO addressRequestDTO, String acceptLanguage) {

        String[] languageAndCountry = acceptLanguage.split("-");

        ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale(languageAndCountry[0], languageAndCountry[1]));

        User user = Mapper.ofTheUserDetailsForUser(userDetails);

        Optional<Address> optionalAddress = this.searchForExistingAddressInTheDatabase(addressRequestDTO, user);

        if (optionalAddress.isPresent()) {
            throw new ExistingAddressException(messages.getString("existing.address"));
        }

        Address addressToBeSaved = Mapper.ofTheAddressRequestDTOForAddress(addressRequestDTO, user);

        return this.addressRepository.save(addressToBeSaved).getId();
    }

    public List<AddressResponseDTO> findByUser(UserDetails userDetails) {

        User user = Mapper.ofTheUserDetailsForUser(userDetails);

        List<Address> addressesFound = this.addressRepository.findByUser(user);

        return addressesFound.stream()
                .map(Mapper::ofTheAddressForAddressResponseDTO)
                .toList();
    }

    public void deleteAAddress(Long id) {

        Optional<Address> addressOptional = this.addressRepository.findById(id);

        if (addressOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        this.addressRepository.deleteById(id);
    }
}
