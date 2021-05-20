package com.tq.smartmeterapi.repository;

import com.tq.smartmeterapi.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository responsible for client CRUD and other operations
 */
public interface ClientRepository extends CrudRepository<Client,Long> {

    /**
     * Find all client records for specified active value
     * @param active is client active (false == deleted records)
     * @return Clients data
     */
    Iterable<Client> findByActive(Boolean active);

    /**
     * Find client by ID and active flag
     * @param id ID of the client
     * @param active is client active (false == deleted records)
     * @return Optional client data
     */
    Optional<Client> findByIdAndActive(Long id, Boolean active);
}
