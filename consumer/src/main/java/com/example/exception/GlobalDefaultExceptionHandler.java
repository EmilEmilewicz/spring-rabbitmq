package com.example.exception;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
class GlobalDefaultExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorMessage> defaultErrorHandler(Exception e) {

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessage.builder()
                        .type("error")
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(value = InvalidCommandException.class)
    public ResponseEntity<ErrorMessage> commandValidationHandler(Exception e) {

        return ResponseEntity
                .status(BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessage.builder()
                        .type("error")
                        .message(e.getMessage())
                        .build()
                );
    }
}