package com.wolox.training.exceptions;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(){

    }

    public BookNotFoundException(final String message, Throwable cause) {
        super(message, cause);
    }
}
