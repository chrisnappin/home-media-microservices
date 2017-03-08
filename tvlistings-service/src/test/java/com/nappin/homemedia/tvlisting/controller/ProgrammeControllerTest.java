package com.nappin.homemedia.tvlisting.controller;

import com.nappin.homemedia.tvlisting.error.UnknownUserException;
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

import java.util.ArrayList;
import java.util.List;

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
        List<Programme> mockResult = new ArrayList<>();
        mockResult.add(new Programme("Example Programme #1"));
        mockResult.add(new Programme("Example Programme #2"));
        given(programmeService.getProgrammes()).willReturn(mockResult);

        tester.isSuccessful(mvc,
                get("/programme"),
                "[" +
                            "{\"name\":\"Example Programme #1\"}," +
                            "{\"name\":\"Example Programme #2\"}" +
                        "]");
    }

    /**
     * Tests the <code>getProgrammes</code> method, when response is empty.
     * @throws Exception    Test failed
     */
    @Test
    public void getProgrammesEmpty() throws Exception {
        given(programmeService.getProgrammes()).willReturn(new ArrayList<Programme>());

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
        List<Programme> mockResult = new ArrayList<>();
        mockResult.add(new Programme("Example Programme #1"));
        mockResult.add(new Programme("Example Programme #2"));
        given(programmeService.getProgrammes()).willReturn(mockResult);

        // request with missing accept media type still returns a valid response
        tester.isSuccessfulNoAccept(mvc, get("/programme"),
                "[" +
                            "{\"name\":\"Example Programme #1\"}," +
                            "{\"name\":\"Example Programme #2\"}" +
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
