package com.tq.smartmeterapi.Utils;

import java.text.DateFormatSymbols;

public class Utils {

    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }
}
