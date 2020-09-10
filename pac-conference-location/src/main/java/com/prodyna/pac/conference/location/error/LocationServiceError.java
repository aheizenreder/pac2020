package com.prodyna.pac.conference.location.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

/**
 * Definition of errors for location service
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum LocationServiceError {

    UNKNOWN_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "unknown.service.error", "9000"),
    NO_LOCATION_FOUND(HttpStatus.BAD_REQUEST, "no.location.found", "9001"),
    LOCATION_NOT_SAVED(HttpStatus.INTERNAL_SERVER_ERROR, "no.location.saved", "9002"),
    LOCATION_NOT_UPDATED(HttpStatus.INTERNAL_SERVER_ERROR, "no.location.updated", "9003"),
    LOCATION_NOT_DELETED(HttpStatus.INTERNAL_SERVER_ERROR, "no.location.deleted", "9004");

    HttpStatus status;
    String messageKey;
    String errorCode;

}
