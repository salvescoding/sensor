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
        Set<Sensor> result = inMemoryRepository.getSensorsByIds(List.of(1, 2));
        assertThat(result, is(Set.of(sensorOne)));
    }
}