package com.prodyna.pac.conference.location.controller;

import com.prodyna.pac.conference.location.error.LocationServiceErrorException;
import com.prodyna.pac.conference.location.error.LocationServiceErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

/**
 * Error handler for mapping service errors to error response.
 */
@Log4j2
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<LocationServiceErrorResponse> handleLocationServiceErrorException(LocationServiceErrorException ex, Locale locale) {
        LocationServiceErrorResponse errorResponse = new LocationServiceErrorResponse(ex, locale);
        return createResponseEntity(errorResponse);
    }


    @ExceptionHandler
    public ResponseEntity<LocationServiceErrorResponse> handleException(Exception ex, Locale locale) {
        LocationServiceErrorResponse errorResponse = new LocationServiceErrorResponse(ex, locale);
        return createResponseEntity(errorResponse);
    }

    private ResponseEntity<LocationServiceErrorResponse> createResponseEntity(LocationServiceErrorResponse errorResponse) {
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}
