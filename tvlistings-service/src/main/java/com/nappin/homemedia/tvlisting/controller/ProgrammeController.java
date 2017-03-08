package com.nappin.homemedia.tvlisting.controller;

import com.nappin.homemedia.tvlisting.model.APIError;
import com.nappin.homemedia.tvlisting.model.Programme;
import com.nappin.homemedia.tvlisting.service.ProgrammeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value="/programme")
@Api(description="Programmes Endpoint")
public class ProgrammeController {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(ProgrammeController.class);

    /** The programme service to delegate to. */
    @Autowired
    private ProgrammeService programmeService;

    /**
     * Get the programmes for the current day.
     * @return A List of Programmes
     */
    @RequestMapping(method=GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="Get all programmes for the current day")
    @ApiResponses(value={
        @ApiResponse(code=200, message="Programmes successfully returned", response=Programme.class, responseContainer="List"),
        @ApiResponse(code=406, message="Unsupported media type", response=Void.class),
        @ApiResponse(code=500, message="Internal error", response=APIError.class)
        })
    public ResponseEntity<List<Programme>> getProgrammes() {
        return ResponseEntity.ok(programmeService.getProgrammes());
    }
}
