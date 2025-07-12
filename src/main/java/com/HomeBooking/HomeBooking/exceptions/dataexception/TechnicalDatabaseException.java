package com.HomeBooking.HomeBooking.exceptions.dataexception;

public class TechnicalDatabaseException extends RuntimeException{

    public TechnicalDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
