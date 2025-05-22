package com.example.schedulemanagementappupgrade.exception;

public class SameEmailExistException extends RuntimeException {
    public SameEmailExistException(String message) {
        super(message);
    }
}
