package com.students.management.system.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {


    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handle(HttpMessageNotReadableException e) {
        log.error("CustomExceptionHandler :: handle :: Bad Request :: HttpMessageNotReadableException occurred :: INVALID REQUEST CAUSE :: {}, MESSAGE :: {}", e.getCause(), e.getMessage());
        return new ResponseEntity<>("Please provide valid request", HttpStatus.BAD_REQUEST);
    }
}
