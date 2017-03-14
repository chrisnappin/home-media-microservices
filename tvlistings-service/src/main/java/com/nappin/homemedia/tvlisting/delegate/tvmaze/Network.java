package com.nappin.homemedia.tvlisting.delegate.tvmaze;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Encapsulates a TV network returned by the TVMaze service.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Network {

    /** The Network Id. */
    private long id;

    /** The Network name. */
    private String name;

    /**
     * Get the Network Id.
     * @return The id
     */
    public long getId() {
        return id;
    }

    /**
     * Get the Network name.
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the Network Id.
     * @param id    The id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Set the Network name.
     * @param name      The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Convert the current object to a String representation.
     * @return The string representation
     */
    @Override
    public String toString() {
        return new StringBuilder("Network[")
                .append("id=").append(id)
                .append(",name='").append(name).append("'")
                .append(']').toString();
    }
}
