package com.wolox.training.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(){

    }

    public UserNotFoundException(final String message, Throwable cause) {
        super(message, cause);
    }
}
