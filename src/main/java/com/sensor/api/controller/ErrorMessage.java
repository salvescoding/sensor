package com.sensor.api.controller;

public class ErrorMessage {

    private final java.lang.Error error;

    private final String data;

    public ErrorMessage(int code, String message) {
        this.error = new java.lang.Error(String.valueOf(code), message);
        this.data = null;

    }

    public java.lang.Error getError() {
        return error;
    }

    public String getData() {
        return data;
    }

}
