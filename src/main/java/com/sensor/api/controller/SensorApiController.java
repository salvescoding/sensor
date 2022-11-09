package com.sensor.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sensor.api.service.SensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SensorApiController {

    private final ObjectWriter OBJECT_WRITER = new ObjectMapper().writer().withDefaultPrettyPrinter();

    private final SensorService sensorService;

    public SensorApiController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping(value = "/sensors", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addSensorsEvents(@RequestBody SensorsDTO sensorsEvent) {
        sensorService.save(sensorsEvent.sensorList());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
