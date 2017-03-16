package com.nappin.homemedia.tvlisting.delegate.tvmaze;

/**
 * Encapsulates an episode of a TV show, returned by the TVMaze service.
 */
public class Episode {

    /** The episode id. */
    private long id;

    /** The starting date and time the episode is shown on air. */
    private String airstamp;

    /** The episode running time, in minutes. */
    private int runtime;

    /** The show this episode is part of. */
    private Show show;

    /**
     * Get the episode id.
     * @return The episode id
     */
    public long getId() {
        return id;
    }

    /**
     * Set the episode id.
     * @param id    The episode id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the starting date and time the episode is shown on air.
     * @return The starting timestamp, in ISO 8601 format
     */
    public String getAirstamp() {
        return airstamp;
    }

    /**
     * Set the starting date and time the episode is shown on air.
     * @param airstamp   The starting timestamp, in ISO 8601 format
     */
    public void setAirstamp(String airstamp) {
        this.airstamp = airstamp;
    }

    /**
     * Get the running time, in minutes.
     * @return The running time
     */
    public int getRuntime() {
        return runtime;
    }

    /**
     * Set the running time, in minutes.
     * @param runtime   The running time
     */
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    /**
     * Get the show this episode is part of.
     * @return The show
     */
    public Show getShow() {
        return show;
    }

    /**
     * Set the show this episode is part.
     * @param show  The show
     */
    public void setShow(Show show) {
        this.show = show;
    }

    /**
     * Convert the current object to a String representation.
     * @return The string representation
     */
    @Override
    public String toString() {
        return new StringBuilder("Episode[")
                .append("id=").append(id)
                .append(",airstamp='").append(airstamp).append("'")
                .append(",runtime=").append(runtime)
                .append(",show=").append(show)
                .append(']').toString();
    }
}
