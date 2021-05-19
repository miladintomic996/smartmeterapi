package com.tq.smartmeterapi.controller;

import com.tq.smartmeterapi.custom.*;
import com.tq.smartmeterapi.model.MeterReadings;
import com.tq.smartmeterapi.repository.MeterReadingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class MeterReadingsController {

    @Autowired
    private MeterReadingsRepository meterReadingsRepository;

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

    @GetMapping("/meter-reading/{id}")
    public ResponseEntity<MeterReadings> getmeterReadingById(@PathVariable("id") long id) {
        try {
            Optional<MeterReadings> meterReading = meterReadingsRepository.findByIdAndActive(id, true);
            if (meterReading.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return meterReading.map(client -> new ResponseEntity<>(client, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/meter-reading/year/{year}/month/{month}")
    public ResponseEntity<YearMonthReadings> getReadingsPerYearAndMonth(@PathVariable("year") int year, @PathVariable("month") int month) {
        try {
            YearMonthReadings yearMonthReadings = new YearMonthReadings(year, month, meterReadingsRepository.countPerYearAndMonth(year,month));

            return new ResponseEntity<YearMonthReadings>(yearMonthReadings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    @GetMapping("/meter-reading/year/{year}")
    public ResponseEntity<YearReadings> getReadingsPerYear(@PathVariable("year") int year) {
        try {
            List<MonthRecord> monthReadings = meterReadingsRepository.countPerYearGroupByMonth(year);
            YearReadings yearlyCount = new YearReadings(year, meterReadingsRepository.countPerYear(year)) ;

            return new ResponseEntity<YearReadings>(yearlyCount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/meter-reading")
    public ResponseEntity<Object> createMeterReading(@RequestBody MeterReadings meterReadings) {
        try {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(meterReadings.getDateCreated());

            Integer year = calendar.get(Calendar.YEAR);
            Integer month = calendar.get(Calendar.MONTH) + 1;

            if (meterReadingsRepository.countPerYearAndMonth(year, month) > 0){
                return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
            }
            MeterReadings _meterReadings = meterReadingsRepository
                    .save(meterReadings);
            return new ResponseEntity<>(_meterReadings, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

            return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
