package com.tq.smartmeterapi.Utils;

import java.text.DateFormatSymbols;

/**
 * API Utils which contains helper methods
 */
public class Utils {

    /**+
     * Get month name by month id
     * @param month ID of the month
     * @return Month name
     */
    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }
}
