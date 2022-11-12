package com.sensor.api.usecase;

public record SensorResponse(
        Integer id,
        Double avg_temperature,
        Double avg_humidity,
        Double avg_windspeed,
        Double max_temperature,
        Double min_temperature,
        Double max_humidity,
        Double min_humidity,
        Double max_windspeed,
        Double min_windspeed
        ) {
}
