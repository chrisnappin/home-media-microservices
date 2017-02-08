package com.nappin.homemedia.tvlisting.controller;

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

    /**
     * Tests the <code>getFavouriteChannels</code> method, when successful.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsSuccessful() throws Exception {
        String expectedResponse =
                "[" +
                        "{\"name\":\"Example Channel #1\",\"id\":100}," +
                        "{\"name\":\"Example Channel #2\",\"id\":101}" +
                "]";

        mvc.perform(get("/favourite/channel/{userId}", 1234L).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(expectedResponse));
    }

    /**
     * Tests the <code>getFavouriteChannels</code> method, when response is empty.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsEmpty() throws Exception {
        String expectedResponse = "[]";

        mvc.perform(get("/favourite/channel/{userId}", 1235L).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(expectedResponse));
    }

    /**
     * Tests the <code>getFavouriteChannels</code> method, when the specified user is unknown.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsUserUnknown() throws Exception {
        String expectedResponse = "{\"message\":\"User 1236 is unknown\"}";

        mvc.perform(get("/favourite/channel/{userId}", 1236L).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(expectedResponse));
    }

    /**
     * Tests the <code>getFavouriteChannels</code> method, when the user isn't specified.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsMissingArgument() throws Exception {
        String expectedResponse = ""; // no response body

        mvc.perform(get("/favourite/channel").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedResponse));
    }

    /**
     * Tests the <code>getFavouriteChannels</code> method, when the user isn't valid.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsInvalidArgument() throws Exception {
        String expectedResponse = ""; // no response body

        mvc.perform(get("/favourite/channel/{userId}", "wibble").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedResponse));
    }

    /**
     * Tests the <code>getFavouriteChannels</code> method, when the accept media type is missing.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsMissingAccept() throws Exception {
        // request with missing accept media type still returns a valid response
        String expectedResponse =
                "[" +
                        "{\"name\":\"Example Channel #1\",\"id\":100}," +
                        "{\"name\":\"Example Channel #2\",\"id\":101}" +
                "]";

        mvc.perform(get("/favourite/channel/{userId}", 1234L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(expectedResponse));
    }

    /**
     * Tests the <code>getFavouriteChannels</code> method, when the accept media type is wrong.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsWrongAccept() throws Exception {
        String expectedResponse = ""; // no response body

        mvc.perform(get("/favourite/channel/{userId}", "1234").accept(MediaType.APPLICATION_XML))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string(expectedResponse));
    }

    // TODO: internal server error

    // TODO: all tests for addFavouriteChannel, removeFavouriteChannel

    // TODO: refactor common test so this uses less code - extract common utils perhaps?
}
