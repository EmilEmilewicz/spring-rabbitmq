package com.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
class GlobalDefaultExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorMessage> defaultErrorHandler(Exception e) {

        log.error("{}", Arrays.toString(e.getStackTrace()));

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

        log.error("{}", Arrays.toString(e.getStackTrace()));

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