package com.example.LibraryManagementSystem.exceptions;

// since "RuntimeException" is "unchecked", hence we don't have to write "throws" on all methods
public class TransactionException extends RuntimeException{
    public TransactionException(String message) {
        super(message);
    }
}
