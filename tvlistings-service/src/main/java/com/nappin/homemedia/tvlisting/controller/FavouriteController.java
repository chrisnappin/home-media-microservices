package com.nappin.homemedia.tvlisting.controller;

import com.nappin.homemedia.tvlisting.error.UnknownUserException;
import com.nappin.homemedia.tvlisting.model.APIError;
import com.nappin.homemedia.tvlisting.service.FavouriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * REST controller that handles user favourites.
 */
@RestController
@RequestMapping(value="/favourite")
public class FavouriteController {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(FavouriteController.class);

    /** The favourite service to delegate to. */
    @Autowired
    private FavouriteService favouriteService;

    /**
     * Get all the favourite channels for the specified user.
     * @param userId    The user id
     * @return An ok response with a list of zero or more channels, or a not found response if user is unknown.
     */
    @RequestMapping(value="/channel/{userId}", method=GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getFavouriteChannels(@PathVariable long userId) {
        logger.debug("In getFavouriteChannels for userId {}", userId);

        return ResponseEntity.ok(favouriteService.getFavouriteChannels(userId));
    }

    /**
     * Adds a favourite channel for the specified user, in an idempotent manner.
     * @param userId    The user id
     * @param channelId The channel id
     * @return A created response if successful, or a not found response if user is unknown.
     */
    @RequestMapping(value="/channel/{userId}", method=PUT)
    public ResponseEntity<?> addFavouriteChannel(@PathVariable long userId, @RequestParam long channelId) {
        try {
            logger.debug("In addFavouriteChannel for userId {} and channelId {}", userId, channelId);

            try {
                favouriteService.addFavouriteChannel(userId, channelId);

                // Note: should really have a URI to read this resource
                return ResponseEntity.created(new URI("http://wibble/test")).build();

            } catch (UnknownUserException ex) {
                return ResponseEntity.notFound().build();
            }

        } catch (Throwable ex) {
            // TODO: add generic error handling
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIError(ex.getMessage()));
        }
    }

    /**
     * Removes a favourite channel for the specified user, in an idempotent manner.
     * @param userId    The user id
     * @param channelId The channel id
     * @return An ok response if successful, or a not found response if user is unknown.
     */
    @RequestMapping(value="/channel/{userId}", method=DELETE)
    public ResponseEntity<?> removeFavouriteChannel(@PathVariable long userId, @RequestParam long channelId) {
        try {
            logger.debug("In removeFavouriteChannel for userId {} and channelId {}", userId, channelId);

            // TODO: add delegate service

            if (userId == 1234L) {
                // simulate accepting the remove, idempotent (if already deleted return OK)
                return ResponseEntity.ok().build();

            } else {
                // simulate user unknown
                String message = "User " + userId + " is unknown";
                logger.warn(message);
                return ResponseEntity.notFound().build();
            }

            // TODO: channel unknown etc

        } catch (Throwable ex) {
            // TODO: add generic error handling
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIError(ex.getMessage()));
        }
    }
}
