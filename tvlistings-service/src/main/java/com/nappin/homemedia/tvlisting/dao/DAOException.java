package com.nappin.homemedia.tvlisting.dao;

/**
 * Encapsulates a data access error.
 */
public class DAOException extends RuntimeException {

    /**
     * Creates a new instance.
     * @param message   The error message
     * @param cause     The wrapped exception
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
