package com.sensor.api.domain;

import java.time.LocalDateTime;

public record Sensor(Integer id, Double temperature, Double humidity, Double windSpeed, LocalDateTime timestamp) {
}
