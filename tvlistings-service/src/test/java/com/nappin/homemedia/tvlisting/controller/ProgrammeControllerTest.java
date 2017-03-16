package com.nappin.homemedia.tvlisting.controller;

import com.nappin.homemedia.tvlisting.model.Channel;
import com.nappin.homemedia.tvlisting.model.Programme;
import com.nappin.homemedia.tvlisting.service.ProgrammeService;
import com.nappin.homemedia.tvlisting.test.RESTControllerTester;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * Tests the <code>ProgrammeController</code> class.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ProgrammeController.class)
public class ProgrammeControllerTest {

    @Autowired
    private MockMvc mvc;

    private RESTControllerTester tester;

    @Before
    public void setUp() {
        this.tester = new RESTControllerTester(mvc);
    }

    @MockBean
    private ProgrammeService programmeService;

    /**
     * Tests the <code>getProgrammes</code> method, when successful.
     * @throws Exception    Test failed
     */
    @Test
    public void getProgrammesSuccessful() throws Exception {
        List<Channel> mockResult = new ArrayList<>();
        List<Programme> listing = new ArrayList<>();
        listing.add(new Programme(100L, "Example Programme #1", "AAA",
                ZonedDateTime.parse("2017-03-15T08:10:00+00:00", ISO_OFFSET_DATE_TIME), 30,1L));
        listing.add(new Programme(101L, "Example Programme #2", "BBB",
                ZonedDateTime.parse("2017-03-15T08:10:30+00:00", ISO_OFFSET_DATE_TIME), 30,2L));
        Channel channel = new Channel(10L, "Example Channel", listing);
        mockResult.add(channel);
        given(programmeService.getProgrammes()).willReturn(mockResult);

        tester.isSuccessful(mvc,
                get("/programme"),
                "[" +
                            "{\"name\":\"Example Channel\"," +
                            "\"id\":10," +
                            "\"listing\":[" +
                                "{" +
                                    "\"programmeId\":100," +
                                    "\"name\":\"Example Programme #1\"," +
                                    "\"type\":\"AAA\"," +
                                    "\"startDateTime\":\"2017-03-15T08:10:00+00:00\"," +
                                    "\"runningTime\":30," +
                                    "\"seriesId\":1" +
                                "}," +
                                "{" +
                                    "\"programmeId\":101," +
                                    "\"name\":\"Example Programme #2\"," +
                                    "\"type\":\"BBB\"," +
                                    "\"startDateTime\":\"2017-03-15T08:10:30+00:00\"," +
                                    "\"runningTime\":30," +
                                    "\"seriesId\":2" +
                                "}" +
                            "]}" +
                        "]");
    }

    /**
     * Tests the <code>getProgrammes</code> method, when response is empty.
     * @throws Exception    Test failed
     */
    @Test
    public void getProgrammesEmpty() throws Exception {
        given(programmeService.getProgrammes()).willReturn(new ArrayList<Channel>());

        tester.isSuccessful(mvc,
                get("/programme"),
                "[]");
    }

    /**
     * Tests the <code>getProgrammes</code> method, when the accept media type is missing.
     * @throws Exception    Test failed
     */
    @Test
    public void getProgrammesMissingAccept() throws Exception {
        List<Channel> mockResult = new ArrayList<>();
        List<Programme> listing = new ArrayList<>();
        listing.add(new Programme(100L, "Example Programme #1", "AAA",
                ZonedDateTime.parse("2017-03-15T08:10:00+00:00", ISO_OFFSET_DATE_TIME), 30,1L));
        listing.add(new Programme(101L, "Example Programme #2", "BBB",
                ZonedDateTime.parse("2017-03-15T08:10:30+00:00", ISO_OFFSET_DATE_TIME), 30,2L));
        Channel channel = new Channel(10L, "Example Channel", listing);
        mockResult.add(channel);
        given(programmeService.getProgrammes()).willReturn(mockResult);

        // request with missing accept media type still returns a valid response
        tester.isSuccessfulNoAccept(mvc, get("/programme"),
                "[" +
                                    "{\"name\":\"Example Channel\"," +
                                    "\"id\":10," +
                                    "\"listing\":[" +
                                        "{" +
                                            "\"programmeId\":100," +
                                            "\"name\":\"Example Programme #1\"," +
                                            "\"type\":\"AAA\"," +
                                            "\"startDateTime\":\"2017-03-15T08:10:00+00:00\"," +
                                            "\"runningTime\":30," +
                                            "\"seriesId\":1" +
                                        "}," +
                                        "{" +
                                            "\"programmeId\":101," +
                                            "\"name\":\"Example Programme #2\"," +
                                            "\"type\":\"BBB\"," +
                                            "\"startDateTime\":\"2017-03-15T08:10:30+00:00\"," +
                                            "\"runningTime\":30," +
                                            "\"seriesId\":2" +
                                        "}" +
                                    "]}" +
                        "]");
    }

    /**
     * Tests the <code>getProgrammes</code> method, when the accept media type is wrong.
     * @throws Exception    Test failed
     */
    @Test
    public void getProgrammesWrongAccept() throws Exception {
        tester.isNotAcceptable(mvc, get("/programme"));
    }

    /**
     * Tests the <code>getProgrammes</code> method, when an internal error happens.
     * @throws Exception    Test failed
     */
    @Test
    public void getProgrammesError() throws Exception {
        willThrow(new RuntimeException("Fatal error!")).given(programmeService).getProgrammes();

        tester.isInternalServerError(mvc,
                get("/programme"),
                "{\"message\":\"Fatal error!\"}");
    }
}
