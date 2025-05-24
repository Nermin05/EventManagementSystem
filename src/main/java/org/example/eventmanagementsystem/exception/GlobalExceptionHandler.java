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
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongVerificationCodeException.class)
    public ResponseEntity<ErrorResponse> handleWrongVerificationCode(WrongVerificationCodeException wrongVerificationCodeException) {
        ErrorResponse errorResponse = new ErrorResponse(wrongVerificationCodeException.getMessage(), "wrong verification code");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException badRequestException) {
        ErrorResponse errorResponse = new ErrorResponse(badRequestException.getMessage(), "You need to enter correct one");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LateEventBookingDateTimeException.class)
    public ResponseEntity<ErrorResponse> handleLateEventBookingDateException(LateEventBookingDateTimeException lateEventBookingDateTimeException) {
        ErrorResponse errorResponse = new ErrorResponse(lateEventBookingDateTimeException.getMessage(), "You cannot book tickets for past events");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LateEventDateException.class)
    public ResponseEntity<ErrorResponse> handleLateEventDateException(LateEventDateException lateEventDateException) {
        ErrorResponse errorResponse = new ErrorResponse(lateEventDateException.getMessage(), "The event has ended");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughSpaceException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughSpaceException(NotEnoughSpaceException notEnoughSpaceException) {
        ErrorResponse errorResponse = new ErrorResponse(notEnoughSpaceException.getMessage(), "Event is sold out");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoActivePricePeriodException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughSpaceException(NoActivePricePeriodException noActivePricePeriodException) {
        ErrorResponse errorResponse = new ErrorResponse(noActivePricePeriodException.getMessage(), "No active price period");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MismatchCardNumberException.class)
    public ResponseEntity<ErrorResponse> handleMismatchCardNumberException(MismatchCardNumberException mismatchCardNumberException) {
        ErrorResponse errorResponse = new ErrorResponse(mismatchCardNumberException.getMessage(), "Mismatch card number");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TicketHaveAlreadySoldException.class)
    public ResponseEntity<ErrorResponse> handleTicketHaveAlreadySoldException(TicketHaveAlreadySoldException ticketHaveAlreadySoldException) {
        ErrorResponse errorResponse = new ErrorResponse(ticketHaveAlreadySoldException.getMessage(), "Ticket have already sold");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(InsufficientBalanceException insufficientBalanceException) {
        ErrorResponse errorResponse = new ErrorResponse(insufficientBalanceException.getMessage(), "Insufficient balance");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> generalExceptionHandler(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), "Internal Server Error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
