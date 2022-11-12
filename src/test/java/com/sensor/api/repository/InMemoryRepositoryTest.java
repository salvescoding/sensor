package com.sensor.api.repository;

import com.sensor.api.domain.Sensor;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class InMemoryRepositoryTest {

    private final InMemoryRepository inMemoryRepository = new InMemoryRepository();


    @Test
    void shouldSaveSensorsCorrectly() {
        Sensor sensorOne = new Sensor(1, 34.3, 90.2, 12.1, LocalDateTime.of(2022, 10, 3, 12, 21, 0));
        List<Sensor> sensors = List.of(
                sensorOne,
                new Sensor(4, 37.3, 0.2, 32.1, LocalDateTime.now())
        );

        inMemoryRepository.save(sensors);
        Set<Sensor> result = inMemoryRepository.getSensorsById(1);
        assertThat(result, is(Set.of(sensorOne)));
    }
    

    @Test
    void shouldGetAllSensorsValues() {
        Sensor sensorOne = new Sensor(1, 34.3, 90.2, 12.1, LocalDateTime.of(2022, 10, 3, 12, 21, 0));
        Sensor sensorFour = new Sensor(4, 37.3, 20.2, 32.1, LocalDateTime.now());
        Sensor sensorTen = new Sensor(10, 32.3, 50.2, 52.1, LocalDateTime.now());
        List<Sensor> sensors = List.of(
                sensorOne,
                sensorFour,
                sensorTen
        );
        inMemoryRepository.save(sensors);

        Set<Sensor> result = inMemoryRepository.getAllSensors();
        assertThat(result, is(Set.of(sensorOne, sensorFour, sensorTen)));
    }
}