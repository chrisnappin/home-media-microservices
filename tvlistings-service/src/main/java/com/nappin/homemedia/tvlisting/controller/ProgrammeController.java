package com.nappin.homemedia.tvlisting.controller;

import com.nappin.homemedia.tvlisting.model.APIError;
import com.nappin.homemedia.tvlisting.model.Programme;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(description="Programmes Endpoint")
public class ProgrammeController {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(ProgrammeController.class);

    @RequestMapping(value="/programme", method=GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="Get all programmes")
    @ApiResponses(value={
        @ApiResponse(code=200, message="Programmes successfully returned", response=Programme.class, responseContainer="List"),
        @ApiResponse(code=406, message="Unsupported media type", response=Void.class),
        @ApiResponse(code=500, message="Internal error", response=APIError.class)
        })
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
