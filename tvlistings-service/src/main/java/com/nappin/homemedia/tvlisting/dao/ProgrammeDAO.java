package com.nappin.homemedia.tvlisting.dao;

import com.nappin.homemedia.tvlisting.model.Channel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Encapsulates persistence of programme listings.
 */
public interface ProgrammeDAO {

    /**
     * Saves a programme listing to the programme collection.
     * @param channels  The listing
     * @param date      The date the listing applies to
     * @throws DAOException Error saving the programme listing
     */
    void saveProgrammeListing(List<Channel> channels, LocalDate date) throws DAOException;

    /**
     * Loads a programme listing for the specified date.
     * @param date      The date of the listing
     * @return The listing, if any
     * @throws DAOException Error saving the programme listing
     */
    Optional<List<Channel>> loadProgrammeListing(LocalDate date) throws DAOException;
}
