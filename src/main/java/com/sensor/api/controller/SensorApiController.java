package com.sensor.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sensor.api.domain.Sensor;
import com.sensor.api.service.SensorService;
import com.sensor.api.usecase.FetchSensorsWithMetricsUseCase;
import com.sensor.api.usecase.SensorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.sensor.api.util.TimestampParser.parseDateTime;

@RestController
@RequestMapping("/")
public class SensorApiController {

    private final ObjectWriter OBJECT_WRITER = new ObjectMapper().writer().withDefaultPrettyPrinter();

    private final SensorService sensorService;
    private final FetchSensorsWithMetricsUseCase fetchSensorsWithMetricsUseCase;

    public SensorApiController(SensorService sensorService, FetchSensorsWithMetricsUseCase fetchSensorsWithMetricsUseCase) {
        this.sensorService = sensorService;
        this.fetchSensorsWithMetricsUseCase = fetchSensorsWithMetricsUseCase;
    }

    @PostMapping(value = "sensors", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addSensorsEvents(@RequestBody SensorsRequestDTO sensorsEvent) {
        List<Sensor> sensorList = mapDtoToSensor(sensorsEvent.sensors());
        sensorService.save(sensorList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "metrics/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> fetchSensorMetrics(
            @PathVariable(value = "id") String id,
            @RequestParam(required = false) String starts_at,
            @RequestParam(required = false) String ends_at) throws JsonProcessingException {
        LocalDateTime startsAt = parseDateTime(starts_at);
        LocalDateTime endsAt = parseDateTime(ends_at);

        List<SensorResponse> sensorsWithMetrics =
                fetchSensorsWithMetricsUseCase.fetchSensorsEventsWithinTimeRangeAndMapMetricsWithId(
                        Integer.valueOf(id), Optional.ofNullable(startsAt), Optional.ofNullable(endsAt));

        return ResponseEntity.status(HttpStatus.OK).body(OBJECT_WRITER.writeValueAsString(sensorsWithMetrics));
    }

    @GetMapping(value = "metrics", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> fetchAllSensorsMetrics(
            @RequestParam(required = false) String starts_at,
            @RequestParam(required = false) String ends_at) throws JsonProcessingException {
        LocalDateTime startsAt = parseDateTime(starts_at);
        LocalDateTime endsAt = parseDateTime(ends_at);

        List<SensorResponse> sensorsWithMetrics = fetchSensorsWithMetricsUseCase
                .fetchAllSensorsWithRangesAndMapMetrics(Optional.ofNullable(startsAt), Optional.ofNullable(endsAt));


        return ResponseEntity.status(HttpStatus.OK).body(OBJECT_WRITER.writeValueAsString(sensorsWithMetrics));
    }

    private List<Sensor> mapDtoToSensor(List<SensorDTO> sensorList) {
        return sensorList.stream().map(sensorDTO -> {
            return new Sensor(
                    sensorDTO.id(),
                    sensorDTO.temperature(),
                    sensorDTO.humidity(),
                    sensorDTO.windSpeed(),
                    parseTimestamp(sensorDTO.timestamp()));
        }).toList();
    }

    private static LocalDateTime parseTimestamp(String timestamp) {
        if (timestamp == null || timestamp.isEmpty()) throw new MissingTimestampException("Missing timestamp for creation");
        else return parseDateTime(timestamp);

    }

}
