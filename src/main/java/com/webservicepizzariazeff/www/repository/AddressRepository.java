package com.webservicepizzariazeff.www.repository;

import com.webservicepizzariazeff.www.domain.Address;
import com.webservicepizzariazeff.www.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByNumberAndRoadAndDistrictAndCityAndStateAndUser(
            String number,
            String road,
            String district,
            String city,
            String state,
            User user
    );

    List<Address> findByUser(User user);

    Optional<Address> findByIdAndUser(Long id, User user);
}
