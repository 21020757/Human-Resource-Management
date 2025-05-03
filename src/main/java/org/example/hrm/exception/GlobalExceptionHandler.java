package org.example.hrm.exception;

import org.example.hrm.dto.CustomResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CoreException.class)
    public ResponseEntity<CustomResponse> handleAppException(CoreException ex) {
        return ResponseEntity.status(ex.getStatus()).body(
                getCustomResponse(ex.getMessage()));
    }

    @ExceptionHandler(ChangePasswordException.class)
    public ResponseEntity<CustomResponse> handleChangePasswordException(ChangePasswordException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                getCustomResponse(ex.getMessage()));
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<CustomResponse> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                getCustomResponse(ex.getMessage()));
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<CustomResponse> handleCustomAuthenticationException(CustomAuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                getCustomResponse(ex.getMessage()));
    }

    @ExceptionHandler(UserIsDisabledException.class)
    public ResponseEntity<CustomResponse> handleUserIsDisabledException(UserIsDisabledException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                getCustomResponse(ex.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                getCustomResponse(ex.getMessage())
        );
    }


    public CustomResponse getCustomResponse(String message) {
        return CustomResponse.builder()
                .success(false)
                .message(message)
                .build();
    }
}

