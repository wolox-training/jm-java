package com.wolox.training.exceptions;

import static com.wolox.training.constants.ExceptionMessages.BOOK_ALREADY_OWNED;
import static com.wolox.training.constants.ExceptionMessages.BOOK_ID_MISMATCH;

import javax.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ BookNotFoundException.class })
    protected final ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, null,
            new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ BookIdMismatchException.class,
        ConstraintViolationException.class,
        DataIntegrityViolationException.class })
    public final ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, BOOK_ID_MISMATCH,
            new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ BookAlreadyOwnedException.class })
    protected final ResponseEntity<Object> handleBookAlreadyOwned(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, BOOK_ALREADY_OWNED,
            new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}