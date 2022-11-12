package com.sensor.api.controller;

public class TimestampParsingException extends RuntimeException {

    public TimestampParsingException(String message) {
        super("Could not parse timestamp with message: " + message);
    }
}