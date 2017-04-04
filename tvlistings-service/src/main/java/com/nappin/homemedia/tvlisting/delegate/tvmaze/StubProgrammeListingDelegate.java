package com.nappin.homemedia.tvlisting.delegate.tvmaze;

import com.nappin.homemedia.tvlisting.delegate.ProgrammeListingDelegate;
import com.nappin.homemedia.tvlisting.delegate.ProgrammeListingException;
import com.nappin.homemedia.tvlisting.model.Channel;
import com.nappin.homemedia.tvlisting.model.Programme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

/**
 * Stub to produce some canned data for offline testing.
 */
public class StubProgrammeListingDelegate implements ProgrammeListingDelegate {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(StubProgrammeListingDelegate.class);

    /**
     * Get a programme listing for the current day.
     *
     * @return A List of Channels with programme listings, possibly empty
     * @throws ProgrammeListingException Error retrieving the programme listing
     */
    @Override
    public List<Channel> getProgrammeListing() throws ProgrammeListingException {
        logger.debug("In Stub getProgrammeListing...");

        List<Channel> expected = new ArrayList<>();
        List<Programme> listing1 = new ArrayList<>();
        listing1.add(new Programme(1000L, "Show #1", "AAA",
                ZonedDateTime.parse("2017-03-15T08:10:00-04:00", ISO_OFFSET_DATE_TIME), 30,1L));
        listing1.add(new Programme(1002L, "Show #3", "CCC",
                ZonedDateTime.parse("2017-03-15T08:10:30-04:00", ISO_OFFSET_DATE_TIME), 30,3L));
        listing1.add(new Programme(1003L, "Show #4", "DDD",
                ZonedDateTime.parse("2017-03-15T08:11:00-04:00", ISO_OFFSET_DATE_TIME), 30,4L));
        Channel channel1 = new Channel(100L, "Network #1", listing1);
        expected.add(channel1);
        List<Programme> listing2 = new ArrayList<>();
        listing2.add(new Programme(1001L, "Show #2", "BBB",
                ZonedDateTime.parse("2017-03-15T08:10:00-04:00", ISO_OFFSET_DATE_TIME), 30,2L));
        Channel channel2 = new Channel(101L, "Network #2", listing2);
        expected.add(channel2);
        return expected;
    }
}
