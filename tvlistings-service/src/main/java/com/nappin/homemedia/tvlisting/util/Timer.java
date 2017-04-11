package com.nappin.homemedia.tvlisting.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Timer utility functionality. Re-usable, but not intended to be thread safe.
 *
 * Logs timings to the system logger as <code>TRACE</code> statements.
 */
public class Timer {

    /** The logger. */
    private final Logger logger;

    /** The system time stamp to use. */
    private long start;

    /**
     * Creates a new instance.
     * @param suffix    The class and method, or external operation, being timed
     */
    public Timer(String suffix) {
        this.logger = LoggerFactory.getLogger("com.nappin.homemedia.timer." + suffix);
    }

    /**
     * Starts (or resets) the timer.
     */
    public void start() {
        // Use nano time as should be higher resolution on Windows.
        this.start = System.nanoTime();
    }

    /**
     * Stop the timer, and log the timing.
     */
    public void stop() {
        logger.trace("{} ns", System.nanoTime() - start);
    }
}
