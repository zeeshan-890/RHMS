package com.remote_vitals.backend.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class DateAndTimeService {



    public LocalDateTime analyzeDateTimeFormat(String dateTime, String format) {
        try {
            if (dateTime == null || format == null) return null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException | IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Validates whether the given date string strictly matches the specified format.
     * It checks for proper formatting and valid date values (e.g., no 32nd day of a month).
     */
    public boolean matchDateformat(String date, String format) {
        if (date == null || format == null) return false;
        try {
            date = date.trim();  // Trim any extra spaces
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDate.parse(date, formatter);  // Parse the date
            return true;  // Only reached if parsing succeeds
        } catch (DateTimeParseException | IllegalArgumentException e) {
            return false;
        }
    }




}
