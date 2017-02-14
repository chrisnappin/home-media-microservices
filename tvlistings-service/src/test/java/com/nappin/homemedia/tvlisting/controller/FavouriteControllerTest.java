package com.nappin.homemedia.tvlisting.controller;

import com.nappin.homemedia.tvlisting.test.RESTControllerTester;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests the <code>FavouriteController</code> class.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(FavouriteController.class)
public class FavouriteControllerTest {

    @Autowired
    private MockMvc mvc;

    private RESTControllerTester tester;

    @Before
    public void setUp() {
        this.tester = new RESTControllerTester(mvc);
    }

    /**
     * Tests the <code>getFavouriteChannels</code> method, when successful.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsSuccessful() throws Exception {
        tester.isSuccessful(mvc,
                get("/favourite/channel/{userId}", 1234L),
                "[" +
                            "{\"name\":\"Example Channel #1\",\"id\":100}," +
                            "{\"name\":\"Example Channel #2\",\"id\":101}" +
                        "]");
    }

    /**
     * Tests the <code>getFavouriteChannels</code> method, when response is empty.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsEmpty() throws Exception {
        tester.isSuccessful(mvc,
                get("/favourite/channel/{userId}", 1235L),
                "[]");
    }

    /**
     * Tests the <code>getFavouriteChannels</code> method, when the specified user is unknown.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsUserUnknown() throws Exception {
        tester.isNotFound(mvc,
                get("/favourite/channel/{userId}", 1236L),
                "{\"message\":\"User 1236 is unknown\"}");
    }

    /**
     * Tests the <code>getFavouriteChannels</code> method, when the user isn't specified.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsMissingArgument() throws Exception {
        tester.isNotFoundEmpty(mvc, get("/favourite/channel"));
    }

    /**
     * Tests the <code>getFavouriteChannels</code> method, when the user isn't valid.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsInvalidArgument() throws Exception {
        tester.isBadRequestEmpty(mvc, get("/favourite/channel/{userId}", "wibble"));
    }

    /**
     * Tests the <code>getFavouriteChannels</code> method, when the accept media type is missing.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsMissingAccept() throws Exception {
        // request with missing accept media type still returns a valid response
        tester.isSuccessfulNoAccept(mvc, get("/favourite/channel/{userId}", 1234L),
                "[" +
                            "{\"name\":\"Example Channel #1\",\"id\":100}," +
                            "{\"name\":\"Example Channel #2\",\"id\":101}" +
                        "]");
    }

    /**
     * Tests the <code>getFavouriteChannels</code> method, when the accept media type is wrong.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsWrongAccept() throws Exception {
        tester.isNotAcceptableEmpty(mvc, get("/favourite/channel/{userId}", "1234"));
    }

    // TODO: internal server error

    // TODO: all tests for addFavouriteChannel, removeFavouriteChannel
}
