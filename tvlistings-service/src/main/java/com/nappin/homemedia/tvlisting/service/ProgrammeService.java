package com.nappin.homemedia.tvlisting.service;

import com.nappin.homemedia.tvlisting.model.Programme;

import java.util.List;

/**
 * Handles retrieval of programme listings.
 */
public interface ProgrammeService {

    /**
     * Get the programme listings for the current day.
     * @return A List of Programmes, possibly empty.
     */
    List<Programme> getProgrammes();
}
