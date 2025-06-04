package com.example.devFlow.exception;

public class PasswordTooShort extends RuntimeException {
    public PasswordTooShort(String message) {
        super(message);
    }
}