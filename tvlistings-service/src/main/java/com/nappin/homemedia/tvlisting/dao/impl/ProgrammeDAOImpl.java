package com.nappin.homemedia.tvlisting.dao.impl;

import com.nappin.homemedia.tvlisting.dao.DAOException;
import com.nappin.homemedia.tvlisting.dao.ProgrammeDAO;
import com.nappin.homemedia.tvlisting.model.Channel;
import com.nappin.homemedia.tvlisting.model.Programme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Encapsulates persistence of programme listings.
 */
public class ProgrammeDAOImpl implements ProgrammeDAO {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(ProgrammeDAOImpl.class);

    /** The programme listing repository to use. */
    private final ListingRepository listingRepository;

    /** The programme listing date format to use. */
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-mm-dd

    /**
     * Creates a new instance.
     * @param listingRepository The Programme Listing repository to use
     */
    public ProgrammeDAOImpl(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    /**
     * Saves a programme listing to the programme collection.
     *
     * @param channels The listing
     * @param date     The date the listing applies to
     * @throws DAOException Error saving the programme listing
     */
    @Override
    public void saveProgrammeListing(List<Channel> channels, LocalDate date) throws DAOException {
        logger.debug("In saveProgrammeListing...");

        ProgrammeListing listing = modelToDoc(channels, date);

        listingRepository.insert(listing);
    }

    /**
     * Loads a programme listing for the specified date.
     *
     * @param date The date of the listing
     * @return The listing, or null if not found
     * @throws DAOException Error saving the programme listing
     */
    @Override
    public Optional<List<Channel>> loadProgrammeListing(LocalDate date) throws DAOException {
        logger.debug("In loadProgrammeListing...");

        ProgrammeListing listing = listingRepository.findByListingDate(date.format(dateFormatter));

        return docToModel(listing);
    }

    /**
     * Converts from model objects to DAO POJOs (without id populated).
     * @param channels  The model objects
     * @param date      The date of this listing
     * @return The DAO POJO equivalent
     */
    private ProgrammeListing modelToDoc(List<Channel> channels, LocalDate date) {
        List<ChannelDoc> channelDocs = new ArrayList<>();
        for (Channel channel : channels) {
            List<ProgrammeDoc> programmeDocs = new ArrayList<>();
            for (Programme programme : channel.getListing()) {
                ProgrammeDoc programmeDoc = new ProgrammeDoc();
                programmeDoc.setProgrammeId(programme.getProgrammeId());
                programmeDoc.setName(programme.getName());
                programmeDoc.setType(programme.getType());
                programmeDoc.setStartDateTime(programme.getStartDateTime().toLocalDateTime());
                programmeDoc.setStartTimeZone(programme.getStartDateTime().getZone());
                programmeDoc.setRunningTime(programme.getRunningTime());
                programmeDoc.setSeriesId(programme.getSeriesId());
                programmeDocs.add(programmeDoc);
            }

            ChannelDoc channelDoc = new ChannelDoc();
            channelDoc.setId(channel.getId());
            channelDoc.setName(channel.getName());
            channelDoc.setListing(programmeDocs);
            channelDocs.add(channelDoc);
        }

        ProgrammeListing programmeListing = new ProgrammeListing();
        programmeListing.setListing(channelDocs);
        programmeListing.setListingDate(date.format(dateFormatter));
        return programmeListing;
    }

    /**
     * Converts DAO POJOs (with id populated) to model objects.
     * @param programmeListing  The DAO POJO
     * @return The model object equivalents
     */
    private Optional<List<Channel>> docToModel(ProgrammeListing programmeListing) {
        List<Channel> channels = null;

        if (programmeListing != null) {
            channels = new ArrayList<>();
            for (ChannelDoc channelDoc : programmeListing.getListing()) {
                List<Programme> programmes = new ArrayList<>();
                for (ProgrammeDoc programmeDoc : channelDoc.getListing()) {
                    programmes.add(new Programme(programmeDoc.getProgrammeId(), programmeDoc.getName(),
                            programmeDoc.getType(),
                            ZonedDateTime.of(programmeDoc.getStartDateTime(), programmeDoc.getStartTimeZone()),
                            programmeDoc.getRunningTime(), programmeDoc.getSeriesId()));
                }

                channels.add(new Channel(channelDoc.getId(), channelDoc.getName(), programmes));
            }
        }

        return Optional.ofNullable(channels);
    }
}
