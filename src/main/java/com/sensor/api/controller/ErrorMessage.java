package com.sensor.api.controller;

public class ErrorMessage {

    private final Error error;

    private final String data;

    public ErrorMessage(int code, String message) {
        this.error = new Error(String.valueOf(code), message);
        this.data = null;

    }

    public Error getError() {
        return error;
    }

    public String getData() {
        return data;
    }

}
