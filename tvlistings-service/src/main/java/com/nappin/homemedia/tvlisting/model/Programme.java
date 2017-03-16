package com.nappin.homemedia.tvlisting.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Objects;

import java.time.ZonedDateTime;

/**
 * Encapsulates a TV programme.
 */
public class Programme {

    /** The programme id. */
    private final long programmeId;

    /** The programme name. */
    private final String name;

    /** The programme type. */
    private final String type;

    /** The data and time of the start of this programme. */
    private final ZonedDateTime startDateTime;

    /** The programme running time, in minutes. */
    private final int runningTime;

    /** The series id. */
    private final long seriesId;

    /**
     * Creates a new instance.
     * @param programmeId   The programme id
     * @param name          The programme name
     * @param type          The programme type
     * @param startDateTime The programme start date and time
     * @param runningTime   The programme running time, in minutes
     * @param seriesId      The series this programme is part of
     */
    public Programme(long programmeId, String name, String type, ZonedDateTime startDateTime, int runningTime,
                     long seriesId) {
        this.programmeId = programmeId;
        this.name = name;
        this.type = type;
        this.startDateTime = startDateTime;
        this.runningTime = runningTime;
        this.seriesId = seriesId;
    }

    /**
     * Get the programme id.
     * @return The programme id
     */
    public long getProgrammeId() {
        return programmeId;
    }

    /**
     * Get the programme name.
     * @return The programme name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the programme type.
     * @return The programme type
     */
    public String getType() {
        return type;
    }

    /**
     * Get the programme start date and time.
     * @return The programme start date and time
     */
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssxxx")
    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Get the programme running time, in minutes.
     * @return The programme running time
     */
    public int getRunningTime() {
        return runningTime;
    }

    /**
     * Get the series id this programme is part of.
     * @return The series id
     */
    public long getSeriesId() {
        return seriesId;
    }

    /**
     * Convert the current object to a String representation.
     * @return The string representation
     */
    @Override
    public String toString() {
        return new StringBuilder("Programme[")
                .append("programmeId=").append(programmeId)
                .append(",name='").append(name).append("'")
                .append(",type='").append(type).append("'")
                .append(",startDateTime='").append(startDateTime).append("'")
                .append(",runningTime=").append(runningTime)
                .append(",seriesId=").append(seriesId)
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

        if (!(other instanceof Programme)) {
            return false;
        }

        Programme otherProgramme = (Programme) other;
        return Objects.equal(programmeId, otherProgramme.programmeId) &&
                Objects.equal(name, otherProgramme.name) &&
                Objects.equal(type, otherProgramme.type) &&
                Objects.equal(startDateTime, otherProgramme.startDateTime) &&
                Objects.equal(runningTime, otherProgramme.runningTime) &&
                Objects.equal(seriesId, otherProgramme.seriesId);
    }

    /**
     * Calculates a hashCode for this instance.
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(programmeId, name, type, startDateTime, runningTime, seriesId);
    }
}
