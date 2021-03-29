package com.example.exception;

public class EventProcessingInterruptedException extends RuntimeException {

    public EventProcessingInterruptedException(String message) {
        super(message);
    }
}