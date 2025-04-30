package com.teleport.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ListenToChanges.class)
    public ResponseEntity<String> handleMyCustomException(ListenToChanges ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.METHOD_FAILURE);
    }

    @ExceptionHandler(Exception.class)  // catch all other exceptions
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("Internal server error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
