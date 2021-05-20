package com.tq.smartmeterapi.custom;

import com.tq.smartmeterapi.Utils.Utils;

/**
 * Month Record DTO
 */
public class MonthRecord {
    private Long total;
    private Integer month;
    private String monthName;

    public MonthRecord(long total, int month) {
        this.total = total;
        this.month = month;
        this.monthName = Utils.getMonth(month);
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }
}
