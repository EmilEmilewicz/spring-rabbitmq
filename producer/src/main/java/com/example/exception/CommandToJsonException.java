package com.example.exception;

public class CommandToJsonException extends RuntimeException {
    public CommandToJsonException(String message) {
        super(message);
    }
}
