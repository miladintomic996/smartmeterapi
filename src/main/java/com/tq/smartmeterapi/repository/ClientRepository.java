package com.tq.smartmeterapi.repository;

import com.tq.smartmeterapi.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client,Long> {

    Iterable<Client> findByActive(Boolean active);

    Optional<Client> findByIdAndActive(Long id, Boolean active);
}
