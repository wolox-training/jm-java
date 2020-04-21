package com.wolox.training.exceptions;

public final class BookIdMismatchException extends RuntimeException {

    public BookIdMismatchException() {

    }

    public BookIdMismatchException(final String message, Throwable cause) {
        super(message, cause);
    }
}
