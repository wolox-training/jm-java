package com.wolox.training.exceptions;

public final class UserIdMismatchException extends RuntimeException {

    public UserIdMismatchException() {

    }

    public UserIdMismatchException(final String message, Throwable cause) {
        super(message, cause);
    }
}
