package com.nappin.homemedia.tvlisting.service;

import com.nappin.homemedia.tvlisting.error.UnknownUserException;
import com.nappin.homemedia.tvlisting.model.Channel;

import java.util.List;

/**
 * Handles retrieval and manipulation of user favourites.
 */
public interface FavouriteService {

    /**
     * Get the favourite channels for the specified user.
     * @param userId    The user
     * @return A List of channels, possibly empty.
     * @throws UnknownUserException Specified user is unknown
     */
    List<Channel> getFavouriteChannels(long userId) throws UnknownUserException;

    /**
     * Adds a favourite channel for the specified user.
     * @param userId        The user
     * @param channelId     The channel
     * @throws UnknownUserException Specified user is unknown
     */
    void addFavouriteChannel(long userId, long channelId) throws UnknownUserException;

    /**
     * Removes a favourite channel for the specified user.
     * @param userId        The user
     * @param channelId     The channel
     * @throws UnknownUserException Specified user is unknown
     */
    void removeFavouriteChannel(long userId, long channelId) throws UnknownUserException;
}
