package com.sensor.api.controller;

public class MissingTimestampException extends RuntimeException {

    public MissingTimestampException(String message) {
        super("Could not parse timestamp with message: " + message);
    }
}