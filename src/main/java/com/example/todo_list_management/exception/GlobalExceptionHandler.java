package com.example.todo_list_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundExceptionHandler.class)
    ProblemDetail handleNotFoundException(NotFoundExceptionHandler e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Not Found Exception");
        problemDetail.setProperty("timestamp ", Instant.now());
        problemDetail.setType(URI.create("localhost:8080/errors/not-found"));
        return problemDetail;
    }

    @ExceptionHandler(UserDuplicateExceptionHandler.class)
    ProblemDetail handleUserDuplicateException(UserDuplicateExceptionHandler e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("User Duplicate Exception");
        problemDetail.setProperty("timestamp ", Instant.now());
        problemDetail.setType(URI.create("localhost:8080/errors/user-duplicate"));
        return problemDetail;
    }

    @ExceptionHandler(FieldEmptyExceptionHandler.class)
    ProblemDetail handleFieldEmptyExceptionHandler(FieldEmptyExceptionHandler e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Filed Is Empty Exception");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setType(URI.create("localhost:8080/errors/bad-request"));
        return problemDetail;
    }

    @ExceptionHandler(FieldBlankExceptionHandler.class)
    ProblemDetail handleFieldBlankExceptionHandler(FieldBlankExceptionHandler e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Filed Is Blank Exception");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setType(URI.create("localhost:8080/errors/bad-request"));
        return problemDetail;
    }

    @ExceptionHandler(NotValidValueExceptionHandler.class)
    ProblemDetail handleNotValidValueExceptionHandler(NotValidValueExceptionHandler e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Not Valid Value Exception");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setType(URI.create("localhost:8080/errors/not-valid-value"));
        return problemDetail;
    }

    @ExceptionHandler(EmptyDataExceptionHandler.class)
    ProblemDetail handleEmptyDataExceptionHandler(EmptyDataExceptionHandler e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Empty Data Exception");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setType(URI.create("localhost:8080/errors/not-valid-value"));
        return problemDetail;
    }
}
