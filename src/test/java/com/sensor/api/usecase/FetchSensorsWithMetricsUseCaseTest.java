package com.sensor.api.usecase;

import com.sensor.api.domain.Sensor;
import com.sensor.api.repository.InMemoryRepository;
import com.sensor.api.service.SensorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class FetchSensorsWithMetricsUseCaseTest {

    private FetchSensorsWithMetricsUseCase fetchSensorsWithMetrics;

    @Mock
    private InMemoryRepository inMemoryRepository;

    @BeforeEach
    void setUp() {
        SensorService sensorService = new SensorService(inMemoryRepository);
        fetchSensorsWithMetrics = new FetchSensorsWithMetricsUseCase(sensorService);
    }

    @Test
    void shouldReturnSensorDataWithAvgMinAndMaxMetricsCalculated() {
        Sensor sensorOne = new Sensor(1, 34.3, 90.2, 12.1, LocalDateTime.of(2022, 11, 5, 0, 0, 0));
        Sensor sensorOneTwo = new Sensor(1, 36.3, 80.2, 32.1, LocalDateTime.of(2022, 11, 6, 0, 0, 0));
        Sensor sensorThree = new Sensor(3, 19.3, 50.2, 1.1, LocalDateTime.of(2022, 11, 2, 0, 0, 0));
        Sensor sensorTwo = new Sensor(2, 23.3, 60.2, 2.1, LocalDateTime.of(2022, 9, 2, 0, 0, 0));
        Sensor sensorFour = new Sensor(4, 27.3, 20.2, 12.1, LocalDateTime.of(2022, 10, 1, 0, 0, 0));
        Sensor sensorFourTwo = new Sensor(4, 35.3, 34.4, 32.3, LocalDateTime.of(2022, 10, 6, 0, 0, 0));

        Mockito.when(inMemoryRepository.getSensorsByIds(List.of(1,2,3,4)))
                .thenReturn(Set.of(sensorOne, sensorFour, sensorThree, sensorOneTwo, sensorTwo, sensorFourTwo));

        List<SensorResponse> result = fetchSensorsWithMetrics.fetchSensorsEventsWithinTimeRangeAndMapMetricsWithIds(
                List.of(1, 2, 3, 4),
                Optional.of(LocalDateTime.of(2022, 10, 1, 0, 0, 0)),
                Optional.of(LocalDateTime.of(2022, 12, 1, 0, 0, 0)));

        List<SensorResponse> expected = List.of(
                new SensorResponse(1, 35.3, 85.2, 22.1, 36.3, 34.3, 90.2, 80.2, 32.1 , 12.1),
                new SensorResponse(3, 19.3, 50.2, 1.1, 19.3, 19.3, 50.2, 50.2, 1.1 , 1.1),
                new SensorResponse(4, 31.3, 27.3, 22.2, 35.3, 27.3, 34.4, 20.2, 32.3 , 12.1)
        );

        assertThat(result, is(expected));
    }

    @Test
    void shouldReturnAllSensorsDataWithAvgMinAndMaxMetricsCalculatedWhenNoTimeRangesAreSpecified() {
        Sensor sensorOne = new Sensor(1, 34.3, 90.2, 12.1, LocalDateTime.of(2022, 11, 5, 0, 0, 0));
        Sensor sensorOneTwo = new Sensor(1, 36.3, 80.2, 32.1, LocalDateTime.of(2022, 11, 6, 0, 0, 0));
        Sensor sensorThree = new Sensor(3, 19.3, 50.2, 1.1, LocalDateTime.of(2022, 11, 2, 0, 0, 0));
        Sensor sensorTwo = new Sensor(2, 23.3, 60.2, 2.1, LocalDateTime.of(2022, 9, 2, 0, 0, 0));
        Sensor sensorFour = new Sensor(4, 27.3, 20.2, 12.1, LocalDateTime.of(2022, 10, 1, 0, 0, 0));
        Sensor sensorFourTwo = new Sensor(4, 35.3, 34.4, 32.3, LocalDateTime.of(2022, 10, 6, 0, 0, 0));

        Mockito.when(inMemoryRepository.getAllSensors())
                .thenReturn(Set.of(sensorOne, sensorFour, sensorThree, sensorOneTwo, sensorTwo, sensorFourTwo));

        List<SensorResponse> result = fetchSensorsWithMetrics.fetchAllSensorsWithRangesAndMapMetrics(
                Optional.empty(),
                Optional.empty());

        List<SensorResponse> expected = List.of(
                new SensorResponse(1, 35.3, 85.2, 22.1, 36.3, 34.3, 90.2, 80.2, 32.1 , 12.1),
                new SensorResponse(2, 23.3, 60.2, 2.1, 23.3, 23.3, 60.2, 60.2, 2.1 , 2.1),
                new SensorResponse(3, 19.3, 50.2, 1.1, 19.3, 19.3, 50.2, 50.2, 1.1 , 1.1),
                new SensorResponse(4, 31.3, 27.3, 22.2, 35.3, 27.3, 34.4, 20.2, 32.3 , 12.1)
        );
        assertThat(result, is(expected));
    }
}