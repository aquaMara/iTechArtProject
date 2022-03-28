package org.aquam.learnrest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.time.ZonedDateTime;

@ControllerAdvice
// @RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<AppResponse> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);    // 404
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<AppResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);    // 404
    }

    @ExceptionHandler(EntitiesNotFoundException.class)
    public ResponseEntity<AppResponse> handleEntitiesNotFoundException(EntitiesNotFoundException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);    // 404
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<AppResponse> handleEntityExistsException(EntityExistsException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<AppResponse> handleIOException(IOException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);     // 500
    }

    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<AppResponse> handleEmptyInputException(EmptyInputException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);  // 400 некорректный запрос
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<AppResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.BAD_REQUEST, exception.getConstraintViolations());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // error 403
    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<AppResponse> handleJwtAuthenticationException(JwtAuthenticationException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}

/*
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<AppResponse> handleNullPointerException(NullPointerException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
 */
