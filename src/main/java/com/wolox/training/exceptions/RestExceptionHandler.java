package com.wolox.training.exceptions;

import static com.wolox.training.constants.ExceptionMessages.BOOK_ALREADY_OWNED;
import static com.wolox.training.constants.ExceptionMessages.BOOK_ID_MISMATCH;
import static com.wolox.training.constants.ExceptionMessages.USERNAME_ALREADY_TAKEN;
import static com.wolox.training.constants.ExceptionMessages.USER_ID_MISMATCH;

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
    protected final ResponseEntity<Object> handleBookNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, null,
            new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ UserNotFoundException.class })
    protected final ResponseEntity<Object> handleUserNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, null,
            new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ BookIdMismatchException.class })
    public final ResponseEntity<Object> handleBookBadRequest(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, BOOK_ID_MISMATCH,
            new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ UserIdMismatchException.class })
    public final ResponseEntity<Object> handleUserBadRequest(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, USER_ID_MISMATCH,
            new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ BookAlreadyOwnedException.class })
    protected final ResponseEntity<Object> handleBookAlreadyOwned(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, BOOK_ALREADY_OWNED,
            new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ UserAlreadyExistsException.class })
    public final ResponseEntity<Object> handleTakenUsername(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, USERNAME_ALREADY_TAKEN,
            new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
