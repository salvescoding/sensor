package com.sensor.api.repository;

import com.sensor.api.domain.Sensor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryRepository {

    private final ConcurrentHashMap<Integer, Set<Sensor>> repository;

    public InMemoryRepository() {
        this.repository =  new ConcurrentHashMap<>(10000);
    }

    public void save(List<Sensor> sensors) {
        sensors.forEach(sensor -> {
            Set<Sensor> values = repository.get(sensor.id());
            if (values == null) repository.put(sensor.id(), Set.of(sensor));
            else {
                values.add(sensor);
                repository.put(sensor.id(), values);
            }
        });
    }

    public Set<Sensor> getSensorsByIds(List<Integer> ids) {
        return ids.stream()
                .map(repository::get)
                .filter(Objects::nonNull)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public Set<Sensor> getAllSensors() {
        return repository.values().stream().flatMap(Set::stream).collect(Collectors.toSet());
    }
}
