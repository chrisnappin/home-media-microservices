package com.nappin.homemedia.tvlisting.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates a TV channel.
 */
public class Channel {

    /** The channel name. */
    private final String name;

    /** The channel id. */
    private final long id;

    /** The channel listing (optional). */
    private final List<Programme> listing;

    /**
     * Creates a new instance.
     * @param id        The channel id
     * @param name      The channel name
     */
    public Channel(long id, String name) {
        // channel with no listing
        this(id, name, new ArrayList<Programme>());
    }

    /**
     * Creates a new instance.
     * @param id        The channel id
     * @param name      The channel name
     * @param listing   The channel listing
     */
    public Channel(long id, String name, List<Programme> listing) {
        this.id = id;
        this.name = name;
        this.listing = listing;
    }

    /**
     * Get the channel id.
     * @return The id
     */
    public long getId() {
        return id;
    }

    /**
     * Get the channel name.
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the channel list (if any).
     * @return The listing, may be empty
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<Programme> getListing() {
        return listing;
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
     * Determines if same as another programme instance, using value equality.
     * @param other     The other instance to compare with
     * @return <code>true</code> if of equal value
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof Channel)) {
            return false;
        }

        Channel otherChannel = (Channel) other;
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
