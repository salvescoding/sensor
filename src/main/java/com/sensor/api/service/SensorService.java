package com.sensor.api.service;

import com.sensor.api.domain.Sensor;
import com.sensor.api.repository.InMemoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SensorService {

    private final InMemoryRepository inMemoryRepository;

    public SensorService(InMemoryRepository inMemoryRepository) {
        this.inMemoryRepository = inMemoryRepository;
    }

    public void save(List<Sensor> sensors) {
        inMemoryRepository.save(sensors);
    }

    public Map<Integer, List<Sensor>> fetchAllSensors(Optional<LocalDateTime> start, Optional<LocalDateTime> end) {
        return filterSensorEventsWithinTimeRange(inMemoryRepository.getAllSensors(), start, end);
    }

    public Map<Integer, List<Sensor>> fetchSensorsByIds(List<Integer> ids, Optional<LocalDateTime> start, Optional<LocalDateTime> end) {
        return filterSensorEventsWithinTimeRange(inMemoryRepository.getSensorsByIds(ids), start, end);
    }

    private Map<Integer, List<Sensor>> filterSensorEventsWithinTimeRange(Set<Sensor> sensors, Optional<LocalDateTime> start, Optional<LocalDateTime> end) {
           return sensors
                .stream()
                .filter(sensor -> sensor.isEventStartingAfterOrEqualTo(start))
                .filter(sensor -> sensor.isEventEndingBeforeOrEqualTo(end))
                .collect(Collectors.groupingBy(Sensor::id));
    }
}
