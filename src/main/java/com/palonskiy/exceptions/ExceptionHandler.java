package com.palonskiy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationError(ConstraintViolationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NullAuthorException.class)
    public ResponseEntity<String> handleEmptyAuthor() {
        return new ResponseEntity<>("Author can not be null", HttpStatus.NOT_ACCEPTABLE);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NullBookException.class)
    public ResponseEntity<String> handleEmptyBook() {
        return new ResponseEntity<>("Book can not be null", HttpStatus.NOT_ACCEPTABLE);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ExistingEntityException.class)
    public ResponseEntity<String> handleExistEntity() {
        return new ResponseEntity<>("Entity already exist", HttpStatus.NOT_ACCEPTABLE);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoResultException.class)
    public ResponseEntity<String> handleNoResult() {
        return new ResponseEntity<>("Can not find any entities", HttpStatus.NOT_ACCEPTABLE);
    }
}
