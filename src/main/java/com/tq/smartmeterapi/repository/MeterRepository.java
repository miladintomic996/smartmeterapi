package com.tq.smartmeterapi.repository;

import com.tq.smartmeterapi.model.Client;
import com.tq.smartmeterapi.model.Meter;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository responsible for meter CRUD and other operations
 */
public interface MeterRepository extends CrudRepository<Meter,Long> {

    /**
     * Find all meter records for specified active value
     * @param active is meter active (false == deleted records)
     * @return Clients data
     */
    Iterable<Meter> findByActive(Boolean active);

    /**
     * Find meter by ID and active flag
     * @param id ID of the meter
     * @param active is meter active (false == deleted records)
     * @return Optional meter data
     */
    Optional<Meter> findByIdAndActive(Long id, Boolean active);

    /**
     * Find meter record by client and active flag
     * @param client clients data
     * @param active active is meter active (false == deleted records)
     * @return Optional meter data
     */
    Optional<Meter> findByClientAndActive(Client client, Boolean active);
}
