package com.sensor.api.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimestampParser {
        private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        public static LocalDateTime parseDateTime(String dateTime) {
            // TODO put personalized Exception here
            if (dateTime == null || dateTime.isEmpty()) return null;
            return LocalDateTime.parse(dateTime.replace("T", " "), FORMATTER);
        }

}
