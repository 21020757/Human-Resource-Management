package org.example.hrm.exception;

public class UserIsDisabledException extends RuntimeException {
    public UserIsDisabledException(String message) {
        super(message);
    }
}
