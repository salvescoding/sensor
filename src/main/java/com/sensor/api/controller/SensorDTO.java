package com.sensor.api.controller;

import java.time.LocalDateTime;

public record SensorDTO(Integer id, Double temperature, Double humidity, Double windSpeed, String timestamp) {
}
