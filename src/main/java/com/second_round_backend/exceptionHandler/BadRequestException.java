package com.second_round_backend.exceptionHandler;


public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}