package com.nappin.homemedia.tvlisting.controller;

import com.nappin.homemedia.tvlisting.error.UnknownUserException;
import com.nappin.homemedia.tvlisting.model.Channel;
import com.nappin.homemedia.tvlisting.service.FavouriteService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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

    @MockBean
    private FavouriteService favouriteService;

    /**
     * Tests the <code>getFavouriteChannels</code> method, when successful.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsSuccessful() throws Exception {
        List<Channel> mockResult = new ArrayList<>();
        mockResult.add(new Channel(100L, "Example Channel #1"));
        mockResult.add(new Channel(101L, "Example Channel #2"));
        given(favouriteService.getFavouriteChannels(1234L)).willReturn(mockResult);

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
        given(favouriteService.getFavouriteChannels(1235L)).willReturn(new ArrayList<Channel>());

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
        given(favouriteService.getFavouriteChannels(1236L)).willThrow(new UnknownUserException("Oops!"));

        tester.isNotFound(mvc, get("/favourite/channel/{userId}", 1236L));
    }

    /**
     * Tests the <code>getFavouriteChannels</code> method, when the user isn't specified.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsMissingArgument() throws Exception {
        tester.isNotFound(mvc, get("/favourite/channel"));
    }

    /**
     * Tests the <code>getFavouriteChannels</code> method, when the user isn't valid.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsInvalidArgument() throws Exception {
        tester.isBadRequest(mvc, get("/favourite/channel/{userId}", "wibble"));
    }

    /**
     * Tests the <code>getFavouriteChannels</code> method, when the accept media type is missing.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsMissingAccept() throws Exception {
        List<Channel> mockResult = new ArrayList<>();
        mockResult.add(new Channel(100L, "Example Channel #1"));
        mockResult.add(new Channel(101L, "Example Channel #2"));
        given(favouriteService.getFavouriteChannels(1234L)).willReturn(mockResult);

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
        tester.isNotAcceptable(mvc, get("/favourite/channel/{userId}", "1234"));
    }

    /**
     * Tests the <code>getFavouriteChannels</code> method, when an internal error happens.
     * @throws Exception    Test failed
     */
    @Test
    public void getFavouriteChannelsError() throws Exception {
        willThrow(new RuntimeException("Fatal error!")).given(favouriteService).getFavouriteChannels(1234L);

        tester.isInternalServerError(mvc,
                get("/favourite/channel/{userId}", 1234L),
                "{\"message\":\"Fatal error!\"}");
    }

    /**
     * Tests the <code>addFavouriteChannels</code> method, when successful.
     * @throws Exception    Test failed
     */
    @Test
    public void addFavouriteChannelsSuccessful() throws Exception {
        tester.isCreated(mvc, put("/favourite/channel/{userId}?channelId={channelId}", 1234L, 101L));
    }

    /**
     * Tests the <code>addFavouriteChannels</code> method, when the specified user is unknown.
     * @throws Exception    Test failed
     */
    @Test
    public void addFavouriteChannelsUserUnknown() throws Exception {
        willThrow(new UnknownUserException("Oops")).given(favouriteService).addFavouriteChannel(1236L, 101L);

        tester.isNotFound(mvc, put("/favourite/channel/{userId}?channelId={channelId}", 1236L, 101L));
    }

    /**
     * Tests the <code>addFavouriteChannels</code> method, when the user and channel isn't specified.
     * @throws Exception    Test failed
     */
    @Test
    public void addFavouriteChannelsMissingArguments() throws Exception {
        tester.isNotFound(mvc, put("/favourite/channel"));
    }

    /**
     * Tests the <code>addFavouriteChannels</code> method, when the user and channel aren't valid.
     * @throws Exception    Test failed
     */
    @Test
    public void addFavouriteChannelsInvalidArgument() throws Exception {
        tester.isBadRequest(mvc, put("/favourite/channel/{userId}?channelId={channelId}", "wibble", "abc"));
    }

    /**
     * Tests the <code>addFavouriteChannels</code> method, when an internal error happens.
     * @throws Exception    Test failed
     */
    @Test
    public void addFavouriteChannelsError() throws Exception {
        willThrow(new RuntimeException("Fatal error!")).given(favouriteService).addFavouriteChannel(1236L, 101L);

        tester.isInternalServerError(mvc,
                put("/favourite/channel/{userId}?channelId={channelId}", 1236L, 101L),
                "{\"message\":\"Fatal error!\"}");
    }

    /**
     * Tests the <code>removeFavouriteChannels</code> method, when successful.
     * @throws Exception    Test failed
     */
    @Test
    public void removeFavouriteChannelsSuccessful() throws Exception {
        tester.isSuccessfulEmpty(mvc,
                delete("/favourite/channel/{userId}?channelId={channelId}", 1234L, 101L));
    }

    /**
     * Tests the <code>removeFavouriteChannels</code> method, when the specified user is unknown.
     * @throws Exception    Test failed
     */
    @Test
    public void removeFavouriteChannelsUserUnknown() throws Exception {
        willThrow(new UnknownUserException("Oops")).given(favouriteService).removeFavouriteChannel(1236L, 101L);

        tester.isNotFound(mvc,
                delete("/favourite/channel/{userId}?channelId={channelId}", 1236L, 101L));
    }

    /**
     * Tests the <code>removeFavouriteChannels</code> method, when the user and channel isn't specified.
     * @throws Exception    Test failed
     */
    @Test
    public void removeFavouriteChannelsMissingArguments() throws Exception {
        tester.isNotFound(mvc, delete("/favourite/channel"));
    }

    /**
     * Tests the <code>removeFavouriteChannels</code> method, when the user and channel aren't valid.
     * @throws Exception    Test failed
     */
    @Test
    public void removeFavouriteChannelsInvalidArgument() throws Exception {
        tester.isBadRequest(mvc,
                delete("/favourite/channel/{userId}?channelId={channelId}", "wibble", "abc"));
    }

    /**
     * Tests the <code>aremoveFavouriteChannels</code> method, when an internal error happens.
     * @throws Exception    Test failed
     */
    @Test
    public void removeFavouriteChannelsError() throws Exception {
        willThrow(new RuntimeException("Fatal error!")).given(favouriteService).removeFavouriteChannel(1234L, 101L);

        tester.isInternalServerError(mvc,
                delete("/favourite/channel/{userId}?channelId={channelId}", 1234L, 101L),
                "{\"message\":\"Fatal error!\"}");
    }
}
