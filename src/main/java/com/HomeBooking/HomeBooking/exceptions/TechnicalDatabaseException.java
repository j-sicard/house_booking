package com.HomeBooking.HomeBooking.exceptions;

public class TechnicalDatabaseException extends RuntimeException{

    public TechnicalDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
