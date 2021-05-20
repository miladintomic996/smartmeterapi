package com.tq.smartmeterapi.repository;

import com.tq.smartmeterapi.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository responsible for address CRUD and other operations
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     * This method checks does address already exist in database
     * @param country country
     * @param countryCode country code
     * @param city city
     * @param street street
     * @param streetNumber street number
     * @return does address exist
     */
    Boolean existsByCountryAndCountryCodeAndCityAndStreetAndStreetNumber(String country, String countryCode, String city, String street, String streetNumber);
}
