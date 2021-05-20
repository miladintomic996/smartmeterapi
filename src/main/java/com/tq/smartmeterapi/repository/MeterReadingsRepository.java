package com.tq.smartmeterapi.repository;

import com.tq.smartmeterapi.custom.MonthRecord;
import com.tq.smartmeterapi.model.MeterReadings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository responsible for meter readings CRUD and other operations
 */
public interface MeterReadingsRepository extends JpaRepository<MeterReadings, Long> {

    /**
     * Returns the meter reading data for specified year and month
     * @param year year created
     * @param month month created
     * @return Total records for specified year and month
     */
    @Query("SELECT COUNT(mr) FROM MeterReadings mr WHERE year(mr.dateCreated) = ?1 and month(mr.dateCreated) = ?2 and mr.active = true")
    Integer countPerYearAndMonth(Integer year, Integer month);

    /**
     * Returns the meter readings total for specified year
     * @param year year created
     * @return Total records for specified year
     */
    @Query("SELECT COUNT(mr) FROM MeterReadings mr WHERE year(mr.dateCreated) = ?1 and mr.active = true")
    Integer countPerYear(Integer year);

    /**
     * Returns the meter reading data for specified year grouped by month
     * @param year year created
     * @return list of meter readings grouped by month
     */
    @Query("SELECT new com.tq.smartmeterapi.custom.MonthRecord(COUNT(mr), MONTH(mr.dateCreated)) FROM MeterReadings mr WHERE YEAR(mr.dateCreated)=?1 and mr.active = true GROUP BY MONTH(mr.dateCreated)")
    List<MonthRecord> countPerYearGroupByMonth(Integer year);

    /**
     * Find meter reading by ID and active flag
     * @param id ID of the meter reading
     * @param active is meter reading active (false == deleted records)
     * @return Optional meter reading data
     */
    Optional<MeterReadings> findByIdAndActive(Long id, Boolean active);

    /**
     * Find all meter reading records for specified active value
     * @param active is meter reading active (false == deleted records)
     * @return Meter Reading data
     */
    Iterable<MeterReadings> findByActive(Boolean active);
}
