package com.nappin.homemedia.tvlisting.delegate;

import com.nappin.homemedia.tvlisting.model.Channel;

import java.util.List;

/**
 * Encapsulates obtaining programme listings.
 */
public interface ProgrammeListingDelegate {

    /**
     * Get a programme listing for the current day.
     * @return A List of Channels with programme listings, possibly empty
     * @throws ProgrammeListingException Error retrieving the programme listing
     */
    List<Channel> getProgrammeListing() throws ProgrammeListingException;
}
