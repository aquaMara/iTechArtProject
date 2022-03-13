package org.aquam.learnrest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.ZonedDateTime;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {AppRequestException.class})
    public ResponseEntity<Object> handleAppRequestException(AppRequestException e) {
        AppException apiException = new AppException(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now());
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    // ?????????
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> handleIOException(IOException e) {
        AppException apiException = new AppException("IO EXCEPTION", HttpStatus.BAD_REQUEST, ZonedDateTime.now());
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}
