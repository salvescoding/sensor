package com.sensor.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sensor.api.domain.Sensor;
import com.sensor.api.repository.InMemoryRepository;
import com.sensor.api.service.SensorService;
import com.sensor.api.usecase.FetchSensorsWithMetricsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class SensorApiControllerTest {

    private SensorApiController sensorApiController;

    @Mock
    private InMemoryRepository inMemoryRepository;


    @BeforeEach
    void setUp() {
        SensorService sensorService = new SensorService(inMemoryRepository);
        FetchSensorsWithMetricsUseCase fetchSensorsWithMetricsUseCase = new FetchSensorsWithMetricsUseCase(sensorService);
        sensorApiController = new SensorApiController(sensorService, fetchSensorsWithMetricsUseCase);
    }


    @Test
    void shouldGetTheCorrectMetricsForSensorOne() throws JsonProcessingException {
        Sensor sensorOne = new Sensor(1, 34.3, 90.2, 12.1, LocalDateTime.of(2022, 11, 5, 0, 0, 0));
        Sensor sensorOneTwo = new Sensor(1, 36.3, 90.2, 32.1, LocalDateTime.of(2022, 11, 6, 0, 0, 0));

        Mockito.when(inMemoryRepository.getSensorsById(1)).thenReturn(
                Set.of(sensorOne, sensorOneTwo)
        );

        ResponseEntity<String> response = sensorApiController.fetchSensorMetrics("1", "2022-06-30T20:00:00", "2022-12-01T22:00:00");

        assertThat(response.getBody(),
                is("""  
                        [ {
                          "id" : 1,
                          "avg_temperature" : 35.3,
                          "avg_humidity" : 90.2,
                          "avg_windspeed" : 22.1,
                          "max_temperature" : 36.3,
                          "min_temperature" : 34.3,
                          "max_humidity" : 90.2,
                          "min_humidity" : 90.2,
                          "max_windspeed" : 32.1,
                          "min_windspeed" : 12.1
                        } ]"""));
    }

    @Test
    void shouldGetAllSensorsData() throws JsonProcessingException {
        Sensor sensorOne = new Sensor(1, 34.3, 90.2, 12.1, LocalDateTime.of(2022, 11, 5, 0, 0, 0));
        Sensor sensorOneTwo = new Sensor(1, 36.3, 90.2, 32.1, LocalDateTime.of(2022, 11, 6, 0, 0, 0));
        Sensor sensorTwo = new Sensor(2, 23.1, 80.2, 12.1, LocalDateTime.of(2022, 11, 6, 0, 0, 0));

        Mockito.when(inMemoryRepository.getAllSensors()).thenReturn(
                Set.of(sensorOne, sensorOneTwo, sensorTwo)
        );

        ResponseEntity<String> response = sensorApiController.fetchAllSensorsMetrics(null, null);

        assertThat(response.getBody(),
                is("""  
                        [ {
                          "id" : 1,
                          "avg_temperature" : 35.3,
                          "avg_humidity" : 90.2,
                          "avg_windspeed" : 22.1,
                          "max_temperature" : 36.3,
                          "min_temperature" : 34.3,
                          "max_humidity" : 90.2,
                          "min_humidity" : 90.2,
                          "max_windspeed" : 32.1,
                          "min_windspeed" : 12.1
                        }, {
                          "id" : 2,
                          "avg_temperature" : 23.1,
                          "avg_humidity" : 80.2,
                          "avg_windspeed" : 12.1,
                          "max_temperature" : 23.1,
                          "min_temperature" : 23.1,
                          "max_humidity" : 80.2,
                          "min_humidity" : 80.2,
                          "max_windspeed" : 12.1,
                          "min_windspeed" : 12.1
                        } ]"""));
    }

    @Test
    void shouldValidateTimestampAtCreationTime() {
        SensorsRequestDTO sensorsRequestDTO = new SensorsRequestDTO(
                List.of(
                        new SensorDTO(1, 34.3, 90.2, 12.1, null)));

        assertThrows(MissingTimestampException.class, () -> sensorApiController.addSensorsEvents(sensorsRequestDTO));
    }
}

