package com.sensor.api.repository;

import com.sensor.api.domain.Sensor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryRepository {

    private final ConcurrentHashMap<Integer, ArrayList<Sensor>> repository;

    public InMemoryRepository() {
        this.repository =  new ConcurrentHashMap<>(10000);
    }

    public void save(List<Sensor> sensors) {
        sensors.forEach(sensor -> {
            ArrayList<Sensor> values = repository.get(sensor.id());
            if (values == null)  {
                ArrayList<Sensor> newValues = new ArrayList<>();
                newValues.add(sensor);
                repository.put(sensor.id(),  newValues);
            }
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
                .flatMap(ArrayList::stream)
                .collect(Collectors.toSet());
    }

    public Set<Sensor> getAllSensors() {
        return repository.values().stream().flatMap(ArrayList::stream).collect(Collectors.toSet());
    }
}
