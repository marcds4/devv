package com.example.devFlow.exception;

public class UsernameTaken extends RuntimeException {
    public UsernameTaken(String message) {
        super(message);
    }
}