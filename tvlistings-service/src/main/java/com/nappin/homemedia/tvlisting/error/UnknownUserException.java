package com.nappin.homemedia.tvlisting.error;

/**
 * Encapsulates an unknown user error.
 */
public class UnknownUserException extends RuntimeException {

    /**
     * Creates a new instance.
     * @param message   The error message
     */
    public UnknownUserException(String message) {
        super(message);
    }
}
