package com.nappin.homemedia.tvlisting.service.impl;

import com.nappin.homemedia.tvlisting.error.UnknownUserException;
import com.nappin.homemedia.tvlisting.model.Channel;
import com.nappin.homemedia.tvlisting.service.FavouriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles retrieval and manipulation of user favourites.
 */
public class FavouriteServiceImpl implements FavouriteService {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(FavouriteServiceImpl.class);

    /**
     * Get the favourite channels for the specified user.
     * @param userId    The user
     * @return A List of channels, possibly empty.
     * @throws UnknownUserException Specified user is unknown
     */
    @Override
    public List<Channel> getFavouriteChannels(long userId) throws UnknownUserException {
        logger.debug("In getFavouriteChannels, userId is {}", userId);

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
            throw new UnknownUserException(message);
        }
        return channels;
    }

    /**
     * Adds a favourite channel for the specified user.
     * @param userId        The user
     * @param channelId     The channel
     * @throws UnknownUserException Specified user is unknown
     */
    @Override
    public void addFavouriteChannel(long userId, long channelId) throws UnknownUserException {
        logger.debug("In addFavouriteChannel, userId is {}, channelId is {}", userId, channelId);

        if (userId == 1234L) {
            // simulate accepting the add

        } else {
            // simulate user unknown
            String message = "User " + userId + " is unknown";
            logger.warn(message);
            throw new UnknownUserException(message);
        }
    }

    /**
     * Removes a favourite channel for the specified user.
     * @param userId        The user
     * @param channelId     The channel
     * @throws UnknownUserException Specified user is unknown
     */
    @Override
    public void removeFavouriteChannel(long userId, long channelId) throws UnknownUserException {
        logger.debug("In removeFavouriteChannel, userId is {}, channelId is {}", userId, channelId);

        if (userId == 1234L) {
            // simulate accepting the remove (idempotent, ok if already deleted)

        } else {
            // simulate user unknown
            String message = "User " + userId + " is unknown";
            logger.warn(message);
            throw new UnknownUserException(message);
        }
    }
}
