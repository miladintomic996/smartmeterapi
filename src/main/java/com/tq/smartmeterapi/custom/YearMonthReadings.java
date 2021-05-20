package com.tq.smartmeterapi.custom;

/**
 * Year-Month Readings Record DTO
 */
public class YearMonthReadings {
    private Integer year;
    private MonthRecord monthRecord;

    public YearMonthReadings(Integer year, Integer month, Integer total) {
        this.year = year;
        this.monthRecord = new MonthRecord(total, month);
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public MonthRecord getMonthRecord() {
        return monthRecord;
    }

    public void setMonthRecord(MonthRecord monthRecord) {
        this.monthRecord = monthRecord;
    }
}
