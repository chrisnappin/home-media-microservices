package com.nappin.homemedia.tvlisting.controller;

import com.nappin.homemedia.tvlisting.model.APIError;
import com.nappin.homemedia.tvlisting.model.Programme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * REST controller that handles requests for programme listings.
 */
@RestController()
public class ProgrammesController {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(ProgrammesController.class);

    @RequestMapping(value="/programme", method=GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> programmeListing() {
        try {
            // TODO: add delegate service

            List<Programme> programmes = new ArrayList<>();
            programmes.add(new Programme("Example Programme ABC"));
            programmes.add(new Programme("Example Programme DEF"));

            return new ResponseEntity<List<Programme>>(programmes, HttpStatus.OK);

        } catch (Throwable ex) {
            return new ResponseEntity<APIError>(new APIError(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
