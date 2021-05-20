package com.tq.smartmeterapi.controller;

import com.tq.smartmeterapi.model.Client;
import com.tq.smartmeterapi.model.Meter;
import com.tq.smartmeterapi.repository.ClientRepository;
import com.tq.smartmeterapi.repository.MeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller for Meter CRUD operations
 */
@RestController
@RequestMapping("/api")
public class MeterController {

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private ClientRepository clientRepository;

    /**
     *  This method will return all clients from database
     * @return list of Meters
     */
    @GetMapping("/meter")
    public ResponseEntity<List<Meter>> getAllMeters() {
        try {
            List<Meter> meters = new ArrayList<Meter>();
            meterRepository.findByActive(true).forEach(meters::add);

            if (meters.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<List<Meter>>(meters, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method will return specific meter data
     * @param id ID of the meter
     * @return Meter data
     */
    @GetMapping("/meter/{id}")
    public ResponseEntity<Meter> getMeterByid(@PathVariable("id") long id) {
        try {
            Optional<Meter> meter = meterRepository.findByIdAndActive(id, true);
            if (meter.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return meter.map(client -> new ResponseEntity<>(client, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create new meter record in database
     * @param meter Meter object to be saved
     * @return Created clients data
     */
    @PostMapping("/meter")
    public ResponseEntity<Object> createMeterReading(@RequestBody Meter meter) {
        try {
            if (meter.getClient() != null) {
                Optional<Client> clientsData = clientRepository.findByIdAndActive(meter.getClient().getId(), true);
                if (clientsData.isPresent()) {
                    Optional<Meter> meterRecord = meterRepository.findByClientAndActive(clientsData.get(), true);
                    if (meterRecord.isPresent()) return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
                }
            }
            Meter _meter = meterRepository
                    .save(meter);
            return new ResponseEntity<>(_meter, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update meter data
     * @param id ID of the meter that needs to be updated
     * @return Updated meter data
     */
    @PutMapping("/meter/{id}")
    public ResponseEntity<Object> updateMeterReading(@RequestBody Meter meter, @PathVariable Long id) {
        try {
            Optional<Meter> meterRecord = meterRepository.findById(id);

            if (meterRecord.isPresent()) {
                Meter _meter = meterRecord.get();
                _meter.setSerialNumber(meter.getSerialNumber());
                return new ResponseEntity<>(meterRepository.save(_meter), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete specific meter from database
     * @param id ID of the meter
     * @return Deleted meter data
     */
    @DeleteMapping(value = "/meter/{id}")
    public ResponseEntity<Boolean> deleteMeter(@PathVariable Long id) {
        try {
            Optional<Meter> meterRecord = meterRepository.findByIdAndActive(id, true);

            if (meterRecord.isPresent()) {
                Meter _meter = meterRecord.get();
                _meter.setActive(false);
                meterRepository.save(_meter);
                return new ResponseEntity<>(true, HttpStatus.OK);
            }

            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
