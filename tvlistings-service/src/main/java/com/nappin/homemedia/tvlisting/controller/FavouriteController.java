package com.nappin.homemedia.tvlisting.controller;

import com.nappin.homemedia.tvlisting.model.APIError;
import com.nappin.homemedia.tvlisting.model.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * REST controller that handles user favourites.
 */
@RestController()
public class FavouriteController {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(FavouriteController.class);

    /**
     * Get all the favourite channels for the specified user.
     * @param userId    The user id
     * @return An ok response with a list of zero or more channels, or a not found response if user is unknown.
     */
    @RequestMapping(value="/favourite/channel/{userId}", method=GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getFavouriteChannels(@PathVariable long userId) {
        try {
            logger.debug("In getFavouriteChannels for userId {}", userId);

            // TODO: add delegate service
            List<Channel> channels = new ArrayList<>();

            if (userId == 1234L) {
                // simulate populated response
                channels.add(new Channel(100L, "Example Channel #1"));
                channels.add(new Channel(101L, "Example Channel #2"));

            } else if (userId == 1235L) {
                // simulate empty response

            } else {
                // simulate user unknown
                String message = "User " + userId + " is unknown";
                logger.warn(message);
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(channels);

        } catch (Throwable ex) {
            // TODO: add generic error handling
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIError(ex.getMessage()));
        }
    }

    /**
     * Adds a favourite channel for the specified user, in an idempotent manner.
     * @param userId    The user id
     * @param channelId The channel id
     * @return A created response if successful, or a not found response if user is unknown.
     */
    @RequestMapping(value="/favourite/channel/{userId}", method=PUT)
    public ResponseEntity<?> addFavouriteChannel(@PathVariable long userId, @RequestParam long channelId) {
        try {
            logger.debug("In addFavouriteChannel for userId {} and channelId {}", userId, channelId);

            // TODO: add delegate service

            if (userId == 1234L) {
                // simulate accepting the add
                // Note: should really have a URI to read this resource
                return ResponseEntity.created(new URI("http://wibble/test")).build();

            } else {
                // simulate user unknown
                String message = "User " + userId + " is unknown";
                logger.warn(message);
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
    @RequestMapping(value="/favourite/channel/{userId}", method=DELETE)
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
