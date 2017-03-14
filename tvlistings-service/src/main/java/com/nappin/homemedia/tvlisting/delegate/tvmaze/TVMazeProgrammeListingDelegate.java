package com.nappin.homemedia.tvlisting.delegate.tvmaze;

import com.nappin.homemedia.tvlisting.delegate.ProgrammeListingDelegate;
import com.nappin.homemedia.tvlisting.model.Programme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Delegate that handles obtaining programme listings from the TV Maze service.
 */
public class TVMazeProgrammeListingDelegate implements ProgrammeListingDelegate {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(TVMazeProgrammeListingDelegate.class);

    /** The REST template to use. */
    private final RestTemplate restTemplate;

    /**
     * Creates a new instance.
     * @param restTemplate  The REST template to use
     */
    public TVMazeProgrammeListingDelegate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Get a programme listing for the current day.
     *
     * @return A List of Programmes
     */
    @Override
    public List<Programme> getProgrammeListing() {
        // TODO: add metrics to time responses

        logger.debug("Querying TV Maze...");

        Episode[] result =
                restTemplate.getForObject("http://api.tvmaze.com/schedule?country={country}", Episode[].class, "GB");

        logger.debug("response received, {} episodes listed", result.length);

        List<Programme> programmes = new ArrayList<>();
        for (Episode episode: result) {
            programmes.add(new Programme(episode.getShow().getName()));
        }
        return programmes;
    }
}
