package com.sensor.api.service;

import com.sensor.api.domain.Sensor;
import com.sensor.api.repository.InMemoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {

    private final InMemoryRepository inMemoryRepository;

    public SensorService(InMemoryRepository inMemoryRepository) {
        this.inMemoryRepository = inMemoryRepository;
    }

    public void save(List<Sensor> sensors) {
        inMemoryRepository.save(sensors);
    }
}
