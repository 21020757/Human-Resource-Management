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
    @ExceptionHandler(ChangePasswordException.class)
    public ResponseEntity<CustomResponse> handleChangePasswordException(ChangePasswordException ex) {
        logger.error("Handling ChangePasswordException: {}", ex.getMessage());
        CustomResponse response = new CustomResponse(false, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<CustomResponse> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        CustomResponse customResponse = new CustomResponse(false, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customResponse);
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<CustomResponse> handleCustomAuthenticationException(CustomAuthenticationException ex) {
        CustomResponse customResponse = new CustomResponse(false, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(customResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse> handleException(Exception ex) {
        CustomResponse response = new CustomResponse(false, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

