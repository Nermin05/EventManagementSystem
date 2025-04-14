package org.example.eventmanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException resourceNotFoundException) {
        ErrorResponse errorResponse = new ErrorResponse(resourceNotFoundException.getMessage(), "No further details");
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongVerificationCodeException.class)
    public ResponseEntity<ErrorResponse> handleWrongVerificationCode(ResourceNotFoundException resourceNotFoundException) {
        ErrorResponse errorResponse = new ErrorResponse(resourceNotFoundException.getMessage(), "wrong verification code");
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> generalExceptionHandler(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), "Internal Server Error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
