package com.nappin.homemedia.tvlisting.controller;

import com.nappin.homemedia.tvlisting.error.UnknownUserException;
import com.nappin.homemedia.tvlisting.model.APIError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Common error handling for all REST controllers in this package.
 */
@ControllerAdvice(basePackages="com.nappin.homemedia.tvlisting.controller")
public class ErrorHandler extends ResponseEntityExceptionHandler {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    /**
     * Handles any <code>UnknownUserException</code>s thrown from a controller.
     * @param ex    The exception thrown
     * @return The response to use
     */
    @ExceptionHandler(UnknownUserException.class)
    public ResponseEntity<?> handleUnknownUserException(UnknownUserException ex) {
        logger.debug("In handleUnknownUserException");

        logger.error("Returning not found", ex);
        return ResponseEntity.notFound().build();
    }

    /**
     * Handles any <code>RuntimeException</code>s thrown from a controller.
     * @param ex    The exception thrown
     * @return The response to use
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        logger.debug("In handleRuntimeException");

        logger.error("Returning internal server error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIError(ex.getMessage()));
    }
}
