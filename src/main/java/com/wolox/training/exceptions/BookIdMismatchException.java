package com.wolox.training.exceptions;

public class BookIdMismatchException extends RuntimeException {

    public BookIdMismatchException() {

    }

    public BookIdMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
