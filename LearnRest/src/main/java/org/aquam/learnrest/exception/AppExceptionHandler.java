package org.aquam.learnrest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.ZonedDateTime;

@ControllerAdvice
// @RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<AppResponse> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.NOT_FOUND, exception.fillInStackTrace());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);    // 404
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<AppResponse> handleIOException(IOException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, exception.fillInStackTrace());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);     // 500
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<AppResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.NOT_FOUND, exception.fillInStackTrace());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);    // 404
    }

    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<AppResponse> handleEmptyInputException(EmptyInputException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.BAD_REQUEST, exception.fillInStackTrace());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);  // 400 некорректный запрос
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<AppResponse> handleNullPointerException(NullPointerException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.NOT_FOUND, exception.fillInStackTrace());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<AppResponse> handleEntityExistsException(EntityExistsException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.BAD_REQUEST, exception.fillInStackTrace());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
