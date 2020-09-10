package com.prodyna.pac.conference.location.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Response representation for errors.
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationServiceErrorResponse {
    public static final String ERROR_MESSAGES = "ErrorMessages";
    HttpStatus status;
    String message;
    String errorCode;
    String details;

    public LocationServiceErrorResponse(LocationServiceErrorException serviceErrorException, Locale locale) {
        this.status = serviceErrorException.getError().getStatus();
        this.details = serviceErrorException.getDetails();
        this.message = getLocalizedMessage(serviceErrorException.getError().getMessageKey(), locale);
        this.errorCode = serviceErrorException.getError().getErrorCode();
    }

    public LocationServiceErrorResponse(LocationServiceError error, Locale locale) {
        this.status = error.getStatus();
        this.errorCode = error.getErrorCode();
        this.message = getLocalizedMessage(error.getMessageKey(), locale);
    }

    public LocationServiceErrorResponse(LocationServiceError error, String details, Locale locale) {
        this.status = error.getStatus();
        this.errorCode = error.getErrorCode();
        this.message = getLocalizedMessage(error.getMessageKey(), locale);
        this.details = details;
    }

    public LocationServiceErrorResponse(Exception e, Locale locale) {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.errorCode = "0";
        this.message = e.getLocalizedMessage();
        this.details = e.toString();
    }

    public LocationServiceErrorResponse(HttpStatus status, String errorCode, String messageKey, String details, Locale locale) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = getLocalizedMessage(messageKey, locale);
        this.details = details;
    }

    /**
     * method to get localized error message.
     *
     * @param messageKey key for error message.
     * @param locale     locale for error message.
     * @return localized error message.
     */
    private String getLocalizedMessage(String messageKey, Locale locale) {
        ResourceBundle resourceBundle =
                ResourceBundle.getBundle(ERROR_MESSAGES, locale);
        return resourceBundle.getString(messageKey);
    }

}
