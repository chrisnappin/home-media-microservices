package com.nappin.homemedia.tvlisting.service;

import com.nappin.homemedia.tvlisting.model.Channel;

import java.util.List;

/**
 * Handles retrieval of programme listings.
 */
public interface ProgrammeService {

    /**
     * Get the programme listings for the current day.
     * @return A List of Channels with programme listings, possibly empty.
     */
    List<Channel> getProgrammes();
}
