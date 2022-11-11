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
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void shouldReturnOnlySensorEventsWithinTimeRange() {
        Sensor sensorOne = new Sensor(1, 34.3, 90.2, 12.1, LocalDateTime.of(2022, 11, 5, 0, 0, 0));
        Sensor sensorOneTwo = new Sensor(1, 36.3, 90.2, 32.1, LocalDateTime.of(2022, 11, 6, 0, 0, 0));
        Sensor sensorThree = new Sensor(3, 19.3, 50.2, 1.1, LocalDateTime.of(2022, 11, 2, 0, 0, 0));
        Sensor sensorTwo = new Sensor(2, 23.3, 60.2, 2.1, LocalDateTime.of(2022, 10, 2, 0, 0, 0));
        Sensor sensorFour = new Sensor(4, 37.3, 0.2, 32.1, LocalDateTime.of(2022, 10, 1, 0, 0, 0));
        List<Sensor> sensors = List.of(
                sensorOne,
                sensorOneTwo,
                sensorTwo,
                sensorThree,
                sensorFour
        );
        sensorService.save(sensors);
        when(inMemoryRepository.getAllSensors()).thenReturn(Set.of(sensorOne, sensorOneTwo, sensorThree, sensorFour, sensorTwo));

        Map<Integer, List<Sensor>> result = sensorService.fetchAllSensors(
                Optional.of(LocalDateTime.of(2022, 11, 1, 0,0,0)),
                Optional.of(LocalDateTime.now()));

        assertThat(result.size(), is(2));
        assertThat(result.get(1).size(), is(2));
        assertThat(result.get(3).size(), is(1));
        assertThat(result.get(1), containsInAnyOrder(sensorOne, sensorOneTwo));
        assertThat(result.get(3), containsInAnyOrder(sensorThree));
        assertThat(Optional.ofNullable(result.get(2)), is(Optional.empty()));
        assertThat(Optional.ofNullable(result.get(4)), is(Optional.empty()));
    }
}