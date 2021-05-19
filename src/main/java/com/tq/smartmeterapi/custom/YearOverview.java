package com.tq.smartmeterapi.custom;

import java.util.List;

public class YearOverview {
    private Integer year;
    private List<MonthRecord> monthRecords;

    public YearOverview(int year, List<MonthRecord> monthRecords) {
        this.year = year;
        this.monthRecords = monthRecords;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<MonthRecord> getMonthRecords() {
        return monthRecords;
    }

    public void setMonthRecords(List<MonthRecord> monthRecords) {
        this.monthRecords = monthRecords;
    }
}
