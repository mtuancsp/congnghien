package com.example.springboot.exception;

public class InvalidBorrowCodeException extends RuntimeException {
    public InvalidBorrowCodeException(String message) {
        super(message);
    }
}
