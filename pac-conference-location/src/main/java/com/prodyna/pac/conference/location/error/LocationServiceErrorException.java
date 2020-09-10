package com.prodyna.pac.conference.location.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * Exception for errors in location service
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationServiceErrorException extends RuntimeException {

    LocationServiceError error;
    String details;

    public LocationServiceErrorException(LocationServiceError error, String details) {
        this.error = error;
        this.details = details;
    }

    public LocationServiceErrorException(LocationServiceError error, Exception e) {
        super(e);
        this.error = error;
        this.details = e.toString();
    }
}
