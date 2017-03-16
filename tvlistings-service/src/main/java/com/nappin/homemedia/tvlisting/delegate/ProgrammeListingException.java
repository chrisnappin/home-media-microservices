package com.nappin.homemedia.tvlisting.delegate;

/**
 * Encapsulates an error retrieving a programme listing from the third party service.
 */
public class ProgrammeListingException extends RuntimeException {

    /**
     * Creates a new instance.
     * @param message   The error message
     */
    public ProgrammeListingException(String message) {
        super(message);
    }

    /**
     * Creates a new instance.
     * @param message   The error message
     * @param cause     The original cause
     */
    public ProgrammeListingException(String message, Throwable cause) {
        super(message, cause);
    }
}
