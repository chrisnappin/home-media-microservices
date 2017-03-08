package com.nappin.homemedia.tvlisting.configuration;

import com.nappin.homemedia.tvlisting.service.FavouriteService;
import com.nappin.homemedia.tvlisting.service.ProgrammeService;
import com.nappin.homemedia.tvlisting.service.impl.FavouriteServiceImpl;
import com.nappin.homemedia.tvlisting.service.impl.ProgrammeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Spring application configuration.
 */
@Configuration
@EnableSwagger2
public class ApplicationConfiguration {

    @Autowired
    private CounterService counterService;

    @Bean
    public FavouriteService favouriteService() {
        return new FavouriteServiceImpl(counterService);
    }

    @Bean
    public ProgrammeService programmeService() {
        return new ProgrammeServiceImpl(counterService);
    }

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("TV Listings Service")
                        .description("Example TV Listings microservice built using Java and Spring Boot.")
                        .version("0.1")
                        .contact(new Contact("Chris Nappin", "http://www.nappin.com", "chris@nappin.com"))
                        .license("ASF License v2.0")
                        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                        .build())
                .groupName("tvlistings")
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.nappin.homemedia.tvlisting.controller"))
                    .build()
                .useDefaultResponseMessages(false);
    }
}
