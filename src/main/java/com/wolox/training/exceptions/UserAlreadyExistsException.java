package com.wolox.training.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(){

    }

    public UserAlreadyExistsException(final String message, Throwable cause) {
        super(message, cause);
    }
}
