package org.billy.resortmanagementsystem.controller;//package miu.edu.resort.controller;

import java.util.HashMap;
import java.util.Map;

import org.billy.resortmanagementsystem.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    // TODO: Review the project and add diffrent error handlers made by others.

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> fieldError = new HashMap<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            fieldError.put(error.getField(), error.getDefaultMessage());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("error", fieldError);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    // HANDLE 404 ERRORS
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleNotFound(NoResourceFoundException e) {
        return new ResponseEntity<>(getErrorMsg(e), HttpStatus.NOT_FOUND);
    }

    // MAP ALL USER THROWN EXCEPTIONS TO BAD REQUESTS
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleBadRequest(Exception e) {
        return new ResponseEntity<>(getErrorMsg(e), HttpStatus.BAD_REQUEST);
    }

    public static Map<String, String> getErrorMsg(Throwable e) {
        HashMap<String, String> err = new HashMap<>();
        err.put("error", e.getMessage());
        return err;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFound(ProductNotFoundException e) {
        return new ResponseEntity<>(getErrorMsg(e), HttpStatus.NOT_FOUND);
    }

    // HANDLE NULL POINTER EXCEPTIONS
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException e) {
        return new ResponseEntity<>(getErrorMsg(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
