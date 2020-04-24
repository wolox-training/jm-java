package com.wolox.training.exceptions;

public final class BookAlreadyOwnedException extends RuntimeException {

    public BookAlreadyOwnedException() {

    }

    public BookAlreadyOwnedException(final String message, Throwable cause) {
        super(message, cause);
    }
}
