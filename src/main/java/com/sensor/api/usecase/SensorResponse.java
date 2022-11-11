package com.sensor.api.usecase;

public record SensorResponse(
        Integer id,
        Double avgTemperature,
        Double avgHumidity,
        Double avgWindSpeed,
        Double maxTemperature,
        Double minTemperature,
        Double maxHumidity,
        Double minHumidity,
        Double maxWindSpeed,
        Double minWindSpeed
        ) {
}
