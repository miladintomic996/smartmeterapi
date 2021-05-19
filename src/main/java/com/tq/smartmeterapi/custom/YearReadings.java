package com.tq.smartmeterapi.custom;

public class YearReadings {

    private Integer year;
    private Integer total;

    public YearReadings(int year, int total){
        this.year = year;
        this.total = total;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
