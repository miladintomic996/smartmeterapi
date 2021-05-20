package com.tq.smartmeterapi.controller;

import com.tq.smartmeterapi.model.Client;
import com.tq.smartmeterapi.repository.AddressRepository;
import com.tq.smartmeterapi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.tq.smartmeterapi.custom.Constants.CLIENT_WITH_ADDRESS_ALREADY_EXIST;

/**
 * Controller for client CRUD operations
 */
@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    /**
     * This method will return all clients from database
     *
     * @return list of Clients
     */
    @GetMapping("/client")
    public ResponseEntity<List<Client>> getAllClients() {
        try {
            List<Client> clients = new ArrayList<Client>();
            clientRepository.findByActive(true).forEach(clients::add);

            if (clients.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method will return specific client data
     *
     * @param id ID of the client
     * @return Client data
     */
    @GetMapping("/client/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") long id) {
        Optional<Client> clientsData = clientRepository.findByIdAndActive(id, true);

        return clientsData.map(client -> new ResponseEntity<>(client, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Create new client record in database
     *
     * @param client Client object to be saved
     * @return Created clients data
     */
    @PostMapping("/client")
    public ResponseEntity<Object> createClient(@RequestBody Client client) {
        try {
            client.setDateChanged(new Date());
            client.setDateCreated(new Date());
            List<String> errors = new ArrayList<>();
            Boolean addressAlreadyExist = addressRepository.existsByCountryAndCountryCodeAndCityAndStreetAndStreetNumber(
                    client.getAddress().getCountry(),
                    client.getAddress().getCountryCode(),
                    client.getAddress().getCity(),
                    client.getAddress().getStreet(),
                    client.getAddress().getStreetNumber()
            );
            if (addressAlreadyExist) {
                errors.add(CLIENT_WITH_ADDRESS_ALREADY_EXIST);
                return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
            }
            Client _client = clientRepository
                    .save(client);
            return new ResponseEntity<>(_client, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete specific client from database
     *
     * @param id ID of the client
     * @return Deleted clients data
     */
    @DeleteMapping(value = "/client/{id}")
    public ResponseEntity<Boolean> deleteClient(@PathVariable Long id) {
        try {
            Optional<Client> client = clientRepository.findByIdAndActive(id, true);

            if (client.isPresent()) {
                Client _client = client.get();
                _client.setActive(false);
                clientRepository.save(_client);
                return new ResponseEntity<>(true, HttpStatus.OK);
            }

            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
