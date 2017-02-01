package com.nappin.homemedia.tvlisting.model;

/**
 * Encapsulates an error response.
 */
public class APIError {

    private String message;

    public APIError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
