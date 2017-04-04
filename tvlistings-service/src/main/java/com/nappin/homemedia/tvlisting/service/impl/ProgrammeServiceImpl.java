package com.nappin.homemedia.tvlisting.service.impl;

import com.nappin.homemedia.tvlisting.dao.ProgrammeDAO;
import com.nappin.homemedia.tvlisting.delegate.ProgrammeListingDelegate;
import com.nappin.homemedia.tvlisting.model.Channel;
import com.nappin.homemedia.tvlisting.service.ProgrammeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.metrics.CounterService;

import java.time.LocalDate;
import java.util.List;

/**
 * Handles retrieval of programme listings.
 */
public class ProgrammeServiceImpl implements ProgrammeService {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(ProgrammeServiceImpl.class);

    /** The programme listing delegate to use. */
    private final ProgrammeListingDelegate programmeListingDelegate;

    /** The programme DAO to use. */
    private final ProgrammeDAO programmeDAO;

    /** The metrics counter service to use. */
    private final CounterService counterService;

    private static final String GET_PROGRAMMES = "homemedia.programme.get";

    /**
     * Creates a new instance.
     * @param counterService            The metrics counter service to use
     * @param programmeListingDelegate  The programme listing delegate to use
     * @param programmeDAO              The programme DAO to use
     */
    public ProgrammeServiceImpl(CounterService counterService, ProgrammeListingDelegate programmeListingDelegate,
                                ProgrammeDAO programmeDAO) {
        this.counterService = counterService;
        this.programmeListingDelegate = programmeListingDelegate;
        this.programmeDAO = programmeDAO;
    }

    /**
     * Get the programmes for the current day.
     * @return A List of channels with programme listings, possibly empty.
     */
    @Override
    public List<Channel> getProgrammes() {
        logger.debug("In getProgrammes");
        counterService.increment(GET_PROGRAMMES);

        // check if programme listing already saved in the repository
        LocalDate today = LocalDate.now();
        logger.debug("Looking for listing covering date: {}", today);
        List<Channel> listing = programmeDAO.loadProgrammeListing(today);

        // if not found then load from external service, and save in DB
        if (listing == null) {
            logger.debug("No saved listing found, so calling external service");
            listing = programmeListingDelegate.getProgrammeListing();

            // save the listing in the repository
            programmeDAO.saveProgrammeListing(listing, today);
            logger.debug("Listing saved in repository");
        }

        return listing;
    }
}
