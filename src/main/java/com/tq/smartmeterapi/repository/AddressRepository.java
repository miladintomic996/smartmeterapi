package com.tq.smartmeterapi.repository;

import com.tq.smartmeterapi.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Long> {
    Boolean existsByCountryAndCountryCodeAndCityAndStreetAndStreetNumber(String country, String countryCode, String city, String street, String streetNumber);
}
