package com.HomeBooking.HomeBooking.exceptions;

import org.springframework.data.crossstore.ChangeSetPersister;

public class HouseNotFoundException extends RuntimeException {

    public HouseNotFoundException(String message) {
        super(message);
    }

    public HouseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

