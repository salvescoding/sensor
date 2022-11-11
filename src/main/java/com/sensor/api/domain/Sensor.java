package com.sensor.api.domain;

import java.time.LocalDateTime;
import java.util.Optional;

public record Sensor(Integer id, Double temperature, Double humidity, Double windSpeed, LocalDateTime timestamp) {


    public boolean isEventStartingAfterOrEqualTo(Optional<LocalDateTime> startsAt) {
        return startsAt.map(time -> this.timestamp.isAfter(time) || this.timestamp.isEqual(time)).orElse(true);
    }

    public boolean isEventEndingBeforeOrEqualTo(Optional<LocalDateTime> endsAt) {
        return endsAt.map(time ->this.timestamp.isBefore(time) || this.timestamp.isEqual(time)).orElse(true);
    }
}
