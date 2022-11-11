package com.sensor.api.service;

import com.sensor.api.domain.Sensor;
import com.sensor.api.repository.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SensorServiceTest {

    private SensorService sensorService;

    @Mock
    private InMemoryRepository inMemoryRepository;


    @BeforeEach
    void setUp() {
        sensorService = new SensorService(inMemoryRepository);
    }

    @Test
    void shouldCallRepoWithSensorsToBeStored() {
        List<Sensor> sensors = List.of(
                new Sensor(1, 34.3, 90.2, 12.1, LocalDateTime.now()),
                new Sensor(2, 23.3, 60.2, 2.1, LocalDateTime.now()),
                new Sensor(3, 19.3, 50.2, 1.1, LocalDateTime.now()),
                new Sensor(4, 37.3, 0.2, 32.1, LocalDateTime.now())
        );
        sensorService.save(sensors);
        verify(inMemoryRepository).save(sensors);
    }

}