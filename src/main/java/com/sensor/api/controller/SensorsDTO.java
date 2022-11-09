package com.sensor.api.controller;

import com.sensor.api.domain.Sensor;

import java.util.List;

public record SensorsDTO(List<Sensor> sensorList) { }

