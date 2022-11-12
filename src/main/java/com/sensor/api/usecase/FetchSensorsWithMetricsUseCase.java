package com.sensor.api.usecase;

import com.sensor.api.domain.Sensor;
import com.sensor.api.service.SensorService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class FetchSensorsWithMetricsUseCase {

    private final SensorService sensorService;

    public FetchSensorsWithMetricsUseCase(SensorService sensorService) {
        this.sensorService = sensorService;
    }


    public List<SensorResponse> fetchSensorsEventsWithinTimeRangeAndMapMetricsWithId(
            Integer id, Optional<LocalDateTime> startsAt, Optional<LocalDateTime> endsAt) {
        Map<Integer, List<Sensor>> sensors = sensorService.fetchSensorsById(id, startsAt, endsAt);
        return mapSensorToResponseWithAllMetrics(sensors);
    }

    public List<SensorResponse> fetchAllSensorsWithRangesAndMapMetrics(Optional<LocalDateTime> startsAt, Optional<LocalDateTime> endsAt) {
        Map<Integer, List<Sensor>> sensors = sensorService.fetchAllSensors(startsAt, endsAt);
        return mapSensorToResponseWithAllMetrics(sensors);
    }

    private List<SensorResponse> mapSensorToResponseWithAllMetrics(Map<Integer, List<Sensor>> sensors) {
        return sensors.keySet().stream().map(id -> {
            return new SensorResponse(
                    id,
                    sensors.get(id).stream().mapToDouble(Sensor::temperature).average().stream().map(v -> Math.round(v * 100.0) / 100.0).findFirst().orElse(0),
                    sensors.get(id).stream().mapToDouble(Sensor::humidity).average().stream().map(v -> Math.round(v * 100.0) / 100.0).findFirst().orElse(0),
                    sensors.get(id).stream().mapToDouble(Sensor::windSpeed).average().stream().map(v -> Math.round(v * 100.0) / 100.0).findFirst().orElse(0),
                    sensors.get(id).stream().mapToDouble(Sensor::temperature).max().orElse(0),
                    sensors.get(id).stream().mapToDouble(Sensor::temperature).min().orElse(0),
                    sensors.get(id).stream().mapToDouble(Sensor::humidity).max().orElse(0),
                    sensors.get(id).stream().mapToDouble(Sensor::humidity).min().orElse(0),
                    sensors.get(id).stream().mapToDouble(Sensor::windSpeed).max().orElse(0),
                    sensors.get(id).stream().mapToDouble(Sensor::windSpeed).min().orElse(0)
                    );
        }).toList();
    }
}
