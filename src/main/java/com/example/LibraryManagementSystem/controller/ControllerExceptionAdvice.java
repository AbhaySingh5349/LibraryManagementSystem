package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.exceptions.TransactionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// to avoid try catch blocks at individual controller method, we can write common logic here & send client our custom error message
@ControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {

    @ExceptionHandler(value = TransactionException.class)
    public ResponseEntity<String> handleTransactionException(TransactionException exception){
        log.error("TransactionException occurred: {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_GATEWAY);
    }
}
