package com.nappin.homemedia.tvlisting.delegate.tvmaze;

import com.nappin.homemedia.tvlisting.delegate.ProgrammeListingDelegate;
import com.nappin.homemedia.tvlisting.delegate.ProgrammeListingException;
import com.nappin.homemedia.tvlisting.model.Channel;
import com.nappin.homemedia.tvlisting.model.Programme;
import com.nappin.homemedia.tvlisting.util.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

/**
 * Delegate that handles obtaining programme listings from the TV Maze service.
 */
public class TVMazeProgrammeListingDelegate implements ProgrammeListingDelegate {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(TVMazeProgrammeListingDelegate.class);

    /** The REST template to use. */
    private final RestTemplate restTemplate;

    /** The Gauge Metrics to use. */
    private final GaugeService gaugeService;

    private static final String TVMAZE_RESPONSE = "homemedia.tvmaze.responseTime.ms";

    /**
     * Creates a new instance.
     * @param restTemplate  The REST template to use
     * @param gaugeService  The Gauge metrics to use
     */
    public TVMazeProgrammeListingDelegate(RestTemplate restTemplate, GaugeService gaugeService) {
        this.restTemplate = restTemplate;
        this.gaugeService = gaugeService;
    }

    /**
     * Get a programme listing for the current day.
     *
     * @return A List of Channels with programme listings, possibly empty
     * @throws ProgrammeListingException Error retrieving the programme listing
     */
    @Override
    public List<Channel> getProgrammeListing() throws ProgrammeListingException {
        logger.debug("Querying TV Maze...");
        Timer requestTimer = new Timer("TVMazeProgrammeListingDelegate.rest-request");
        Timer unmarshallTimer = new Timer("TVMazeProgrammeListingDelegate.unmarshall");
        try {
            long start = System.currentTimeMillis();
            requestTimer.start();
            Episode[] result =
                    restTemplate.getForObject("http://api.tvmaze.com/schedule?country={country}", Episode[].class, "GB");
            requestTimer.stop();
            long end = System.currentTimeMillis();
            gaugeService.submit(TVMAZE_RESPONSE, end - start); // record how many milliseconds the GET took
            logger.debug("response received, {} episodes listed", result.length);

            unmarshallTimer.start();
            Map<Long, Channel> channels = new HashMap<>();
            for (Episode episode : result) {
                Show show = episode.getShow();
                if (show == null) {
                    String message = "Invalid response, episode with no show populated";
                    logger.error(message);
                    throw new ProgrammeListingException(message);
                }

                Network network = show.getNetwork();
                if (network == null) {
                    String message = "Invalid response, episode and show with no network populated";
                    logger.error(message);
                    throw new ProgrammeListingException(message);
                }

                long channelId = network.getId();
                Channel channel;
                if (channels.containsKey(channelId)) {
                    channel = channels.get(channelId);
                } else {
                    channel = new Channel(channelId, network.getName());
                    channels.put(channelId, channel);
                }

                channel.getListing().add(new Programme(episode.getId(), show.getName(), show.getType(),
                        ZonedDateTime.parse(episode.getAirstamp(), ISO_OFFSET_DATE_TIME), episode.getRuntime(), show.getId()));
            }
            unmarshallTimer.stop();

            return new ArrayList<>(channels.values());

        } catch (RestClientException ex) {
            String message = "Error, invalid response from TVMaze";
            logger.error(message, ex);
            throw new ProgrammeListingException(message, ex);

        } catch (DateTimeParseException ex) {
            String message = "Error, invalid date format from TVMaze";
            logger.error(message, ex);
            throw new ProgrammeListingException(message, ex);
        }
    }
}
