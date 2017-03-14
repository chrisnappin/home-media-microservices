package com.nappin.homemedia.tvlisting.delegate.tvmaze;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Encapsulates a TV show returned by the TVMaze service.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Show {

    /** The show id. */
    private long id;

    /** The show name. */
    private String name;

    /** The show type. */
    private String type;

    /** The network scheduling the show. */
    private Network network;

    /**
     * Get the show id.
     * @return  The show id
     */
    public long getId() {
        return id;
    }

    /**
     * Set the show id.
     * @param id    The show id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the show name.
     * @return The show name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the show name.
     * @param name  The show name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the show type.
     * @return The show type
     */
    public String getType() {
        return type;
    }

    /**
     * Set the show type.
     * @param type  The show type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the network scheduling the show.
     * @return The network
     */
    public Network getNetwork() {
        return network;
    }

    /**
     * Set the network scheduling the show.
     * @param network   The network
     */
    public void setNetwork(Network network) {
        this.network = network;
    }

    /**
     * Convert the current object to a String representation.
     * @return The string representation
     */
    @Override
    public String toString() {
        return new StringBuilder("Show[")
                .append("id=").append(id)
                .append(",name='").append(name).append("'")
                .append(",type='").append(type).append("'")
                .append(",network=").append(network)
                .append(']').toString();
    }
}
