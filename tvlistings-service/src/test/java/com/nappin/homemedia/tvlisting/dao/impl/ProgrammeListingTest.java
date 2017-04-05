package com.nappin.homemedia.tvlisting.dao.impl;

import org.junit.Test;

import java.time.format.DateTimeParseException;

/**
 * Tests the <code>ProgrammeListing</code> class.
 */
public class ProgrammeListingTest {

    /**
     * Tests that valid format dates are accepted.
     */
    @Test
    public void validDate() {
        ProgrammeListing programmeListing = new ProgrammeListing();
        programmeListing.setListingDate("2000-12-31");
    }

    /**
     * Tests that invalid format dates are not accepted.
     */
    @Test(expected=DateTimeParseException.class)
    public void invalidDate1() {
        ProgrammeListing programmeListing = new ProgrammeListing();
        programmeListing.setListingDate("123-456-789");
    }

    /**
     * Tests that invalid format dates are not accepted.
     */
    @Test(expected=DateTimeParseException.class)
    public void invalidDate2() {
        ProgrammeListing programmeListing = new ProgrammeListing();
        programmeListing.setListingDate("wibble");
    }

    /**
     * Tests that invalid format dates are not accepted.
     */
    @Test(expected=DateTimeParseException.class)
    public void invalidDate3() {
        ProgrammeListing programmeListing = new ProgrammeListing();
        programmeListing.setListingDate("");
    }

    /**
     * Tests that invalid format dates are not accepted.
     */
    @Test(expected=DateTimeParseException.class)
    public void invalidDate4() {
        ProgrammeListing programmeListing = new ProgrammeListing();
        programmeListing.setListingDate("2001-02-29");
    }
}
