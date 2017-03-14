package com.nappin.homemedia.tvlisting.delegate;

import com.nappin.homemedia.tvlisting.model.Programme;

import java.util.List;

/**
 * Encapsulates obtaining programme listings.
 */
public interface ProgrammeListingDelegate {

    /**
     * Get a programme listing for the current day.
     * @return A List of Programmes
     */
    List<Programme> getProgrammeListing();
}
