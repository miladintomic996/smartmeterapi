package com.tq.smartmeterapi.controller;

import com.tq.smartmeterapi.custom.MonthRecord;
import com.tq.smartmeterapi.custom.YearMonthReadings;
import com.tq.smartmeterapi.custom.YearOverview;
import com.tq.smartmeterapi.custom.YearReadings;
import com.tq.smartmeterapi.model.MeterReadings;
import com.tq.smartmeterapi.repository.MeterReadingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller for Meter Readings CRUD operations
 */
@RestController
@RequestMapping("/api")
public class MeterReadingsController {

    @Autowired
    private MeterReadingsRepository meterReadingsRepository;

    /**
     * This method will return all meter readings from database
     *
     * @return list of Meter Reading
     */
    @GetMapping("/meter-reading")
    public ResponseEntity<List<MeterReadings>> getAll() {
        try {
            List<MeterReadings> meterReadings = new ArrayList<MeterReadings>();
            meterReadingsRepository.findByActive(true).forEach(meterReadings::add);

            if (meterReadings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<List<MeterReadings>>(meterReadings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method will return specific meter reading data
     *
     * @param id ID of the meter reading
     * @return Meter Reading data
     */
    @GetMapping("/meter-reading/{id}")
    public ResponseEntity<MeterReadings> getmeterReadingById(@PathVariable("id") long id) {
        try {
            Optional<MeterReadings> meterReading = meterReadingsRepository.findByIdAndActive(id, true);
            if (meterReading.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return meterReading.map(client -> new ResponseEntity<>(client, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create new meter reading record in database
     *
     * @param meterReadings Meter Reading object to be saved
     * @return Created meter reading data
     */
    @PostMapping("/meter-reading")
    public ResponseEntity<Object> createMeterReading(@RequestBody MeterReadings meterReadings) {
        try {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(meterReadings.getDateCreated());

            Integer year = calendar.get(Calendar.YEAR);
            Integer month = calendar.get(Calendar.MONTH) + 1;

            if (meterReadingsRepository.countPerYearAndMonth(year, month) > 0) {
                return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
            }
            MeterReadings _meterReadings = meterReadingsRepository
                    .save(meterReadings);
            return new ResponseEntity<>(_meterReadings, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update meter reading data
     *
     * @param id ID of the meter reading that needs to be updated
     * @return Updated meter reading data
     */
    @PutMapping("/meter-reading/{id}")
    public ResponseEntity<Object> updateMeterReading(@RequestBody MeterReadings meterReadings, @PathVariable Long id) {
        try {
            Optional<MeterReadings> meterReadingRecord = meterReadingsRepository.findById(id);

            if (meterReadingRecord.isPresent()) {
                MeterReadings _meterReadings = meterReadingRecord.get();
                _meterReadings.setDateChanged(new Date());
                _meterReadings.setValue(meterReadings.getValue());
                return new ResponseEntity<>(meterReadingsRepository.save(_meterReadings), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete specific meter reading from database
     *
     * @param id ID of the meter reading
     * @return Deleted meter reading data
     */
    @DeleteMapping(value = "/meter-reading/{id}")
    public ResponseEntity<Boolean> deleteMeterReading(@PathVariable Long id) {
        try {
            Optional<MeterReadings> meterReading = meterReadingsRepository.findByIdAndActive(id, true);

            if (meterReading.isPresent()) {
                MeterReadings _meterReading = meterReading.get();
                _meterReading.setActive(false);
                meterReadingsRepository.save(_meterReading);
                return new ResponseEntity<>(true, HttpStatus.OK);
            }

            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * This method will return the meter reading data for specified year and month
     *
     * @param year  year criteria
     * @param month month criteria
     * @return Month total meter readings for specified year
     */
    @GetMapping("/meter-reading/year/{year}/month/{month}")
    public ResponseEntity<YearMonthReadings> getReadingsPerYearAndMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
        try {
            YearMonthReadings yearMonthReadings = new YearMonthReadings(year, month, meterReadingsRepository.countPerYearAndMonth(year, month));

            return new ResponseEntity<YearMonthReadings>(yearMonthReadings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method will return the meter reading data for specified year grouped by month
     *
     * @param year year criteria
     * @return Returns the data grouped by month for specified year
     */
    @GetMapping("/meter-reading/month-records/year/{year}")
    public ResponseEntity<YearOverview> getMonthReadingsPerYear(@PathVariable("year") int year) {
        try {
            List<MonthRecord> monthReadings = meterReadingsRepository.countPerYearGroupByMonth(year);
            YearOverview yearOverview = new YearOverview(year, monthReadings);

            return new ResponseEntity<YearOverview>(yearOverview, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method will return the meter readings total for specified year
     *
     * @param year year criteria
     * @return Year total meter readings
     */
    @GetMapping("/meter-reading/year/{year}")
    public ResponseEntity<YearReadings> getReadingsPerYear(@PathVariable("year") int year) {
        try {
            List<MonthRecord> monthReadings = meterReadingsRepository.countPerYearGroupByMonth(year);
            YearReadings yearlyCount = new YearReadings(year, meterReadingsRepository.countPerYear(year));

            return new ResponseEntity<YearReadings>(yearlyCount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
