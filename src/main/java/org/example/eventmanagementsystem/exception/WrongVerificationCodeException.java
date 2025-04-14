package org.example.eventmanagementsystem.exception;

public class WrongVerificationCodeException extends Exception{
    public WrongVerificationCodeException(String message) {
        super(message);
    }
}
