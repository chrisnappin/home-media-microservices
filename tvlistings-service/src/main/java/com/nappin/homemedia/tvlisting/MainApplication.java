package com.nappin.homemedia.tvlisting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The start of the spring boot tvlistings microservice.
 */
@SpringBootApplication
public class MainApplication {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);

    /**
     * The main service startup point.
     * @param args		Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MainApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

        logger.debug("TV Listings microservice started");
    }
}
