package com.nappin.homemedia.tvlisting.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Encapsulates a TV programme.
 */
public class Programme {

    private String name;

    public Programme(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Convert the current object to a String representation.
     * @return The string representation
     */
    @Override
    public String toString() {
        return new StringBuilder("Programme[")
                .append(",name='").append(name).append("'")
                .append(']').toString();
    }

    /**
     * Determines if same as another programme instance, using value equality.
     * @param other     The other instance to compare with
     * @return <code>true</code> if equals
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
        return Objects.equal(name, otherProgramme.name);
    }

    /**
     * Calculates a hashCode for this instance.
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
