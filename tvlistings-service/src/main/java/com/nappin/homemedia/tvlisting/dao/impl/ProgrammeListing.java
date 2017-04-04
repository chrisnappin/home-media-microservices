package com.nappin.homemedia.tvlisting.dao.impl;

import com.google.common.base.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Entity class (mutable POJO) encapsulating a TV Programme Listing stored as a top-level document in MongoDB.
 *
 * Note that the date is stored as a string, because MongoDB doesn't support storing dates without times.
 */
@Document(collection="programme")
public class ProgrammeListing {

    /** The mongo generated document id. */
    private String id;

    /** The date of the listing, as DD-MM-YYYY. */
    private String listingDate;

    /** The listing (various channels with programmes). */
    private List<ChannelDoc> listing;

    /**
     * Get the date of the listing.
     * @return The date, as DD-MM-YYYY
     */
    public String getListingDate() {
        return listingDate;
    }

    /**
     * Set the date of the listing.
     * @param listingDate   The date, as yyyy-mm-dd
     * @throws DateTimeFormatter The date was invalid
     */
    public void setListingDate(String listingDate) throws DateTimeParseException {
        // use strict parser to check date is valid
        LocalDate.parse(listingDate, DateTimeFormatter.ISO_LOCAL_DATE);

        this.listingDate = listingDate;
    }

    /**
     * Get the listing.
     * @return The listing
     */
    public List<ChannelDoc> getListing() {
        return listing;
    }

    /**
     * Set the lsiting.
     * @param listing   The listing
     */
    public void setListing(List<ChannelDoc> listing) {
        this.listing = listing;
    }

    /**
     * Get the document id.
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * Set the document id.
     * @param id    The id
     */
    @Id
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Convert the current object to a String representation.
     * @return The string representation
     */
    @Override
    public String toString() {
        return new StringBuilder("ProgrammeListing[")
                .append("id=").append(id)
                .append(",listingDate='").append(listingDate).append("'")
                .append(",listing=").append(listing).append("'")
                .append(']').toString();
    }

    /**
     * Determines if same as another instance, using value equality.
     * @param other     The other instance to compare with
     * @return <code>true</code> if of equal value
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof ProgrammeListing)) {
            return false;
        }

        ProgrammeListing otherProgrammeListing = (ProgrammeListing) other;
        return Objects.equal(id, otherProgrammeListing.id) &&
                Objects.equal(listingDate, otherProgrammeListing.listingDate) &&
                Objects.equal(listing, otherProgrammeListing.listing);
    }

    /**
     * Calculates a hashCode for this instance.
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id, listingDate, listing);
    }
}
