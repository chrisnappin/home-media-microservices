package com.nappin.homemedia.tvlisting.model;

/**
 * Encapsulates a TV channel.
 */
public class Channel {

    private String name;

    private long id;

    public Channel(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
