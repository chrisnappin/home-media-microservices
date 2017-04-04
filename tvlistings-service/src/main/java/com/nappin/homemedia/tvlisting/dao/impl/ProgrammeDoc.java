package com.nappin.homemedia.tvlisting.dao.impl;

import com.google.common.base.Objects;

import java.time.ZoneId;
import java.time.LocalDateTime;

/**
 * Encapsulates a programme as a POJO within a MongoDB document.
 *
 * Note that Spring Data requires a mutable POJO, and MongoDB doesn't support dates with time zones as a single field.
 */
public class ProgrammeDoc {

    /** The programme id. */
    private long programmeId;

    /** The programme name. */
    private String name;

    /** The programme type. */
    private String type;

    /** The data and time of the start of this programme. */
    private LocalDateTime startDateTime;

    /** The time zone of the start date and time. */
    private ZoneId startTimeZone;

    /** The programme running time, in minutes. */
    private int runningTime;

    /** The series id. */
    private long seriesId;

    /**
     * Get the programme id.
     * @return The programme id
     */
    public long getProgrammeId() {
        return programmeId;
    }

    /**
     * Set the programme id.
     * @param programmeId   The programme id
     */
    public void setProgrammeId(long programmeId) {
        this.programmeId = programmeId;
    }

    /**
     * Get the programme name.
     * @return The programme name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the programme name.
     * @param name  The programme name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the programme type.
     * @return The programme type
     */
    public String getType() {
        return type;
    }

    /**
     * Set the programme type.
     * @param type  The programme type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the programme start date and time.
     * @return The programme start date and time
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Set the programme start date and time.
     * @param startDateTime The programme start date and time
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Get the programme start time zone.
     * @return The time zone
     */
    public ZoneId getStartTimeZone() {
        return startTimeZone;
    }

    /**
     * Set the programme start time zone.
     * @param startTimeZone     The time zone
     */
    public void setStartTimeZone(ZoneId startTimeZone) {
        this.startTimeZone = startTimeZone;
    }

    /**
     * Get the programme running time, in minutes.
     * @return The programme running time
     */
    public int getRunningTime() {
        return runningTime;
    }

    /**
     * Set the programme running time, in minutes.
     * @param runningTime   The programme running time
     */
    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    /**
     * Get the series id this programme is part of.
     * @return The series id
     */
    public long getSeriesId() {
        return seriesId;
    }

    /**
     * Set the series id this programme is part of.
     * @param seriesId  The series id
     */
    public void setSeriesId(long seriesId) {
        this.seriesId = seriesId;
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
                .append(",startTimeZone='").append(startTimeZone).append("'")
                .append(",runningTime=").append(runningTime)
                .append(",seriesId=").append(seriesId)
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

        if (!(other instanceof ProgrammeDoc)) {
            return false;
        }

        ProgrammeDoc otherProgramme = (ProgrammeDoc) other;
        return Objects.equal(programmeId, otherProgramme.programmeId) &&
                Objects.equal(name, otherProgramme.name) &&
                Objects.equal(type, otherProgramme.type) &&
                Objects.equal(startDateTime, otherProgramme.startDateTime) &&
                Objects.equal(startTimeZone, otherProgramme.startTimeZone) &&
                Objects.equal(runningTime, otherProgramme.runningTime) &&
                Objects.equal(seriesId, otherProgramme.seriesId);
    }

    /**
     * Calculates a hashCode for this instance.
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(programmeId, name, type, startDateTime, startTimeZone, runningTime, seriesId);
    }
}
