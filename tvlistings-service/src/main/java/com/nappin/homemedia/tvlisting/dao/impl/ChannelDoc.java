package com.nappin.homemedia.tvlisting.dao.impl;

import com.google.common.base.Objects;

import java.util.List;

/**
 * Encapsulates a TV channel, as a MongoDB document.
 *
 * Note that Spring Data requires a mutable POJO.
 */
public class ChannelDoc {

    /** The channel name. */
    private String name;

    /** The channel id. */
    private long id;

    /** The channel listing (optional). */
    private List<ProgrammeDoc> listing;

    /**
     * Get the channel id.
     * @return The id
     */
    public long getId() {
        return id;
    }

    /**
     * Set the channel id.
     * @param id    The id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the channel name.
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the channel name.
     * @param name  The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the channel list (if any).
     * @return The listing, may be empty or null
     */
    public List<ProgrammeDoc> getListing() {
        return listing;
    }

    /**
     * Set the channel list (if any).
     * @param listing The listing
     */
    public void setListing(List<ProgrammeDoc> listing) {
        this.listing = listing;
    }
    /**
     * Convert the current object to a String representation.
     * @return The string representation
     */
    @Override
    public String toString() {
        return new StringBuilder("Channel[")
                .append("id=").append(id)
                .append(",name='").append(name).append("'")
                .append(",listing={").append(listing).append("}")
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

        if (!(other instanceof ChannelDoc)) {
            return false;
        }

        ChannelDoc otherChannel = (ChannelDoc) other;
        return Objects.equal(id, otherChannel.id) &&
                Objects.equal(name, otherChannel.name) &&
                Objects.equal(listing, otherChannel.listing);
    }

    /**
     * Calculates a hashCode for this instance.
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, listing);
    }
}
