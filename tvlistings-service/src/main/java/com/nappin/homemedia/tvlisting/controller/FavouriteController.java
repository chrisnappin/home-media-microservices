package com.nappin.homemedia.tvlisting.controller;

import com.nappin.homemedia.tvlisting.model.APIError;
import com.nappin.homemedia.tvlisting.model.Channel;
import com.nappin.homemedia.tvlisting.service.FavouriteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * REST controller that handles user favourites.
 */
@RestController
@RequestMapping(value="/favourite")
@Api(description="Favourites Endpoint")
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
    @ApiOperation(value="Get all favourite channels", notes="Gets all favourite channels for the specified user")
    @ApiResponses(value={
        @ApiResponse(code=200, message="Favourites successfully returned", response=Channel.class, responseContainer="List"),
        @ApiResponse(code=400, message="userId is invalid", response=Void.class),
        @ApiResponse(code=404, message="User not known", response=Void.class),
        @ApiResponse(code=406, message="Unsupported media type", response=Void.class),
        @ApiResponse(code=500, message="Internal error", response=APIError.class)
        })
    public ResponseEntity<List<Channel>> getFavouriteChannels(@PathVariable long userId) {
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
    @ApiOperation(value="Add favourite channel", notes="Adds a favourite channel for the specified user")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value={
            @ApiResponse(code=201, message="Addition successful", response=Void.class),
            @ApiResponse(code=400, message="userId is invalid", response=Void.class),
            @ApiResponse(code=404, message="User not known", response=Void.class),
            @ApiResponse(code=406, message="Unsupported media type", response=Void.class),
            @ApiResponse(code=500, message="Internal error", response=APIError.class)
    })
    public ResponseEntity<?> addFavouriteChannel(@PathVariable long userId, @RequestParam long channelId) {
        logger.debug("In addFavouriteChannel for userId {} and channelId {}", userId, channelId);

         favouriteService.addFavouriteChannel(userId, channelId);

        try {
            // Note: should really have a URI to read this resource
            return ResponseEntity.created(new URI("http://wibble/test")).build();

        } catch (URISyntaxException ex) {
            String message = "favourite channel URI error";
            logger.error(message, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIError(message));
        }
    }

    /**
     * Removes a favourite channel for the specified user, in an idempotent manner.
     * @param userId    The user id
     * @param channelId The channel id
     * @return An ok response if successful, or a not found response if user is unknown.
     */
    @RequestMapping(value="/channel/{userId}", method=DELETE)
    @ApiOperation(value="Remove favourite channel", notes="Removes a favourite channel for the specified user")
    @ApiResponses(value={
            @ApiResponse(code=200, message="Removal successful", response=Void.class),
            @ApiResponse(code=400, message="userId is invalid", response=Void.class),
            @ApiResponse(code=404, message="User not known", response=Void.class),
            @ApiResponse(code=406, message="Unsupported media type", response=Void.class),
            @ApiResponse(code=500, message="Internal error", response=APIError.class)
    })
    public ResponseEntity<Void> removeFavouriteChannel(@PathVariable long userId, @RequestParam long channelId) {
        logger.debug("In removeFavouriteChannel for userId {} and channelId {}", userId, channelId);

        favouriteService.removeFavouriteChannel(userId, channelId);

        return ResponseEntity.ok().build();
    }
}
