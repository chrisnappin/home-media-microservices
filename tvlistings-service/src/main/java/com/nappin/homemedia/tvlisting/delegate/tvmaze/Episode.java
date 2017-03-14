package com.nappin.homemedia.tvlisting.delegate.tvmaze;

/**
 * Encapsulates an episode of a TV show, returned by the TVMaze service.
 */
public class Episode {

    /** The episode id. */
    private long id;

    /** The date shown on air. */
    private String airdate;

    /** The time shown on air. */
    private String airtime;

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
     * Get the date shown on air.
     * @return The date
     */
    public String getAirdate() {
        return airdate;
    }

    /**
     * Set the date shown on air.
     * @param airdate   The date
     */
    public void setAirdate(String airdate) {
        this.airdate = airdate;
    }

    /**
     * Get the time shown on air.
     * @return The time
     */
    public String getAirtime() {
        return airtime;
    }

    /**
     * Set the time shown on air.
     * @param airtime   The time
     */
    public void setAirtime(String airtime) {
        this.airtime = airtime;
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
                .append(",airdate='").append(airdate).append("'")
                .append(",airtime='").append(airtime).append("'")
                .append(",runtime=").append(runtime)
                .append(",show=").append(show)
                .append(']').toString();
    }
}
