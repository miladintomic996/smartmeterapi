package com.tq.smartmeterapi.repository;

import com.tq.smartmeterapi.model.Client;
import com.tq.smartmeterapi.model.Meter;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MeterRepository extends CrudRepository<Meter,Long> {

    Iterable<Meter> findByActive(Boolean active);

    Optional<Meter> findByIdAndActive(Long id, Boolean active);

    Optional<Meter> findByClientAndActive(Client client, Boolean active);
}
