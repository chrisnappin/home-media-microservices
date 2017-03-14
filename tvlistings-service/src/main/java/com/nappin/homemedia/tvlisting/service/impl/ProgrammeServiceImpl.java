package com.nappin.homemedia.tvlisting.service.impl;

import com.nappin.homemedia.tvlisting.delegate.ProgrammeListingDelegate;
import com.nappin.homemedia.tvlisting.model.Programme;
import com.nappin.homemedia.tvlisting.service.ProgrammeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.metrics.CounterService;

import java.util.List;

/**
 * Handles retrieval of programme listings.
 */
public class ProgrammeServiceImpl implements ProgrammeService {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(ProgrammeServiceImpl.class);

    /** The programme listing delegate to use. */
    private final ProgrammeListingDelegate programmeListingDelegate;

    /** The metrics counter service to use. */
    private final CounterService counterService;

    private static final String GET_PROGRAMMES = "homemedia.programme.get";

    /**
     * Creates a new instance.
     * @param counterService            The metrics counter service to use
     * @param programmeListingDelegate  The programme listing delegate to use
     */
    public ProgrammeServiceImpl(CounterService counterService, ProgrammeListingDelegate programmeListingDelegate) {
        this.counterService = counterService;
        this.programmeListingDelegate = programmeListingDelegate;
    }

    /**
     * Get the programmes for the current day.
     * @return A List of programmes, possibly empty.
     */
    @Override
    public List<Programme> getProgrammes() {
        logger.debug("In getProgrammes");
        counterService.increment(GET_PROGRAMMES);

        return programmeListingDelegate.getProgrammeListing();
    }
}
