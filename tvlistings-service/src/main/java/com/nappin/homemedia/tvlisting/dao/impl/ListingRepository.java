package com.nappin.homemedia.tvlisting.dao.impl;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Entity repository used to retrieve listings from MongoDB.
 */
public interface ListingRepository extends MongoRepository<ProgrammeListing, String> {

    /**
     * Finds a programme listing for the specified date.
     * @param listingDate   The date
     * @return The programme listing, or <code>null</code> if not found.
     */
    ProgrammeListing findByListingDate(String listingDate);
}
