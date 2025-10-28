package com.HomeBooking.HomeBooking.mapper;

import com.HomeBooking.HomeBooking.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseManager {

    public static <T> ResponseEntity<T> success(T body) {
        return ResponseEntity.ok(body);
    }

    public static <T> ResponseEntity<T> created(T body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    public static ResponseEntity<ApiErrorResponse> error(HttpStatus status, String message) {
        ApiErrorResponse error = new ApiErrorResponse(status.value(), message);
        return ResponseEntity.status(status).body(error);
    }

}