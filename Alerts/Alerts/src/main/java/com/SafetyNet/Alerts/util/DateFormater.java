package com.SafetyNet.Alerts.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormater {
    public static LocalDate stringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        return LocalDate.parse(date, formatter);
    }
}
