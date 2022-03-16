package org.aquam.learnrest.exception;

public class EmptyInputException extends RuntimeException {

    public EmptyInputException() {
    }

    public EmptyInputException(String message) {
        super(message);
    }
}
