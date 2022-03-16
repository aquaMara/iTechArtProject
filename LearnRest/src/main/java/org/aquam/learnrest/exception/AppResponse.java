package org.aquam.learnrest.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class AppResponse {

    private String message;
    private ZonedDateTime zonedDateTime;
    private HttpStatus httpStatus;
    private Throwable throwable;
    private Throwable cause;

    public AppResponse(String message, ZonedDateTime zonedDateTime) {
        this.message = message;
        this.zonedDateTime = zonedDateTime;
    }

    public AppResponse(String message, ZonedDateTime zonedDateTime, HttpStatus httpStatus) {
        this.message = message;
        this.zonedDateTime = zonedDateTime;
        this.httpStatus = httpStatus;
    }

    public AppResponse(String message, ZonedDateTime zonedDateTime, Throwable throwable) {
        this.message = message;
        this.zonedDateTime = zonedDateTime;
        this.throwable = throwable;
    }

    public AppResponse(String message, ZonedDateTime zonedDateTime, Throwable throwable, Throwable cause) {
        this.message = message;
        this.zonedDateTime = zonedDateTime;
        this.throwable = throwable;
        this.cause = cause;
    }

    public AppResponse(String message, ZonedDateTime zonedDateTime, HttpStatus httpStatus, Throwable throwable) {
        this.message = message;
        this.zonedDateTime = zonedDateTime;
        this.httpStatus = httpStatus;
        this.throwable = throwable;
    }

    public String getMessage() {
        return message;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public Throwable getCause() {
        return cause;
    }
}
