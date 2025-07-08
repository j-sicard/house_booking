package com.HomeBooking.HomeBooking.domain.exception;

public class TechnicalDatabaseException extends RuntimeException{

    public TechnicalDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
