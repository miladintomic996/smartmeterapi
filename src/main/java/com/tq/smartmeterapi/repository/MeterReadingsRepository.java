package com.tq.smartmeterapi.repository;

import com.tq.smartmeterapi.custom.MonthRecord;
import com.tq.smartmeterapi.model.MeterReadings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MeterReadingsRepository extends JpaRepository<MeterReadings, Long> {

    @Query("SELECT COUNT(mr) FROM MeterReadings mr WHERE year(mr.dateCreated) = ?1 and month(mr.dateCreated) = ?2 and mr.active = true")
    Integer countPerYearAndMonth(Integer year, Integer month);

    @Query("SELECT COUNT(mr) FROM MeterReadings mr WHERE year(mr.dateCreated) = ?1 and mr.active = true")
    Integer countPerYear(Integer year);

    @Query("SELECT new com.tq.smartmeterapi.custom.MonthRecord(COUNT(mr), MONTH(mr.dateCreated)) FROM MeterReadings mr WHERE YEAR(mr.dateCreated)=?1 and mr.active = true GROUP BY MONTH(mr.dateCreated)")
    List<MonthRecord> countPerYearGroupByMonth(Integer year);

    Optional<MeterReadings> findByIdAndActive(Long id, Boolean active);

    Iterable<MeterReadings> findByActive(Boolean active);
}
