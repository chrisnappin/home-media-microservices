package com.nappin.homemedia.tvlisting.configuration;

import com.nappin.homemedia.tvlisting.service.FavouriteService;
import com.nappin.homemedia.tvlisting.service.impl.FavouriteServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Spring application configuration.
 */
@Configuration
public class ApplicationConfiguration {

    @Bean
    public FavouriteService favouriteService() {
        return new FavouriteServiceImpl();
    }

}
