package com.nappin.homemedia.tvlisting.delegate.tvmaze;

import com.nappin.homemedia.tvlisting.delegate.ProgrammeListingDelegate;
import com.nappin.homemedia.tvlisting.delegate.ProgrammeListingException;
import com.nappin.homemedia.tvlisting.model.Channel;
import com.nappin.homemedia.tvlisting.model.Programme;
import org.junit.Test;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;

/**
 * Tests the <code>TVMazeProgrammeListingDelegate</code> class.
 */
public class TVMazeProgrammeListingDelegateTest {

    /**
     * Tests result mapping when returned an empty result.
     */
    @Test
    public void emptyResult() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        GaugeService gaugeService = mock(GaugeService.class);
        ProgrammeListingDelegate delegate = new TVMazeProgrammeListingDelegate(restTemplate, gaugeService);

        List<Channel> expected = new ArrayList<>();

        Episode[] result = new Episode[0];
        given(restTemplate.getForObject(anyString(), eq(Episode[].class), eq("GB"))).willReturn(result);

        List<Channel> actual = delegate.getProgrammeListing();
        assertEquals("Wrong result", expected, actual);
    }

    /**
     * Tests result mapping when returned a basic populated result.
     */
    @Test
    public void basicResult() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        GaugeService gaugeService = mock(GaugeService.class);
        ProgrammeListingDelegate delegate = new TVMazeProgrammeListingDelegate(restTemplate, gaugeService);

        List<Channel> expected = new ArrayList<>();
        List<Programme> listing1 = new ArrayList<>();
        listing1.add(new Programme(1000L, "Show #1", "AAA",
                ZonedDateTime.parse("2017-03-15T08:10:00-04:00", ISO_OFFSET_DATE_TIME), 30, 1L));
        Channel channel1 = new Channel(100L, "Network #1", listing1);
        expected.add(channel1);
        List<Programme> listing2 = new ArrayList<>();
        listing2.add(new Programme(1001L, "Show #2", "BBB",
                ZonedDateTime.parse("2017-03-15T09:30:00-04:00", ISO_OFFSET_DATE_TIME), 80,2L));
        Channel channel2 = new Channel(101L, "Network #2", listing2);
        expected.add(channel2);

        Episode[] result = new Episode[2];
        Network network1 = new Network();
        network1.setId(100L);
        network1.setName("Network #1");
        Show show1 = new Show();
        show1.setId(1L);
        show1.setName("Show #1");
        show1.setType("AAA");
        show1.setNetwork(network1);
        Episode episode1 = new Episode();
        episode1.setId(1000L);
        episode1.setAirstamp("2017-03-15T08:10:00-04:00");
        episode1.setRuntime(30);
        episode1.setShow(show1);
        result[0] = episode1;

        Network network2 = new Network();
        network2.setId(101L);
        network2.setName("Network #2");
        Show show2 = new Show();
        show2.setId(2L);
        show2.setName("Show #2");
        show2.setType("BBB");
        show2.setNetwork(network2);
        Episode episode2 = new Episode();
        episode2.setId(1001L);
        episode2.setAirstamp("2017-03-15T09:30:00-04:00");
        episode2.setRuntime(80);
        episode2.setShow(show2);
        result[1] = episode2;

        given(restTemplate.getForObject(anyString(), eq(Episode[].class), eq("GB"))).willReturn(result);

        List<Channel> actual = delegate.getProgrammeListing();
        assertEquals("Wrong result", expected, actual);
    }

    /**
     * Tests result mapping when returned a result with multiple programmes per channel.
     */
    @Test
    public void multipleProgrammesPerChannelResult() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        GaugeService gaugeService = mock(GaugeService.class);
        ProgrammeListingDelegate delegate = new TVMazeProgrammeListingDelegate(restTemplate, gaugeService);

        List<Channel> expected = new ArrayList<>();
        List<Programme> listing1 = new ArrayList<>();
        listing1.add(new Programme(1000L, "Show #1", "AAA",
                ZonedDateTime.parse("2017-03-15T08:10:00-04:00", ISO_OFFSET_DATE_TIME), 30,1L));
        listing1.add(new Programme(1002L, "Show #3", "CCC",
                ZonedDateTime.parse("2017-03-15T08:10:30-04:00", ISO_OFFSET_DATE_TIME), 30,3L));
        listing1.add(new Programme(1003L, "Show #4", "DDD",
                ZonedDateTime.parse("2017-03-15T08:11:00-04:00", ISO_OFFSET_DATE_TIME), 30,4L));
        Channel channel1 = new Channel(100L, "Network #1", listing1);
        expected.add(channel1);
        List<Programme> listing2 = new ArrayList<>();
        listing2.add(new Programme(1001L, "Show #2", "BBB",
                ZonedDateTime.parse("2017-03-15T08:10:00-04:00", ISO_OFFSET_DATE_TIME), 30,2L));
        Channel channel2 = new Channel(101L, "Network #2", listing2);
        expected.add(channel2);

        Episode[] result = new Episode[4];
        Network network1 = new Network();
        network1.setId(100L);
        network1.setName("Network #1");
        Show show1 = new Show();
        show1.setId(1L);
        show1.setName("Show #1");
        show1.setType("AAA");
        show1.setNetwork(network1);
        Episode episode1 = new Episode();
        episode1.setId(1000L);
        episode1.setAirstamp("2017-03-15T08:10:00-04:00");
        episode1.setRuntime(30);
        episode1.setShow(show1);
        result[0] = episode1;

        Network network2 = new Network();
        network2.setId(101L);
        network2.setName("Network #2");
        Show show2 = new Show();
        show2.setId(2L);
        show2.setName("Show #2");
        show2.setType("BBB");
        show2.setNetwork(network2);
        Episode episode2 = new Episode();
        episode2.setId(1001L);
        episode2.setAirstamp("2017-03-15T08:10:00-04:00");
        episode2.setRuntime(30);
        episode2.setShow(show2);
        result[1] = episode2;

        Show show3 = new Show();
        show3.setId(3L);
        show3.setName("Show #3");
        show3.setType("CCC");
        show3.setNetwork(network1); // same network as above
        Episode episode3 = new Episode();
        episode3.setId(1002L);
        episode3.setAirstamp("2017-03-15T08:10:30-04:00");
        episode3.setRuntime(30);
        episode3.setShow(show3);
        result[2] = episode3;

        Show show4 = new Show();
        show4.setId(4L);
        show4.setName("Show #4");
        show4.setType("DDD");
        show4.setNetwork(network1); // same network as above
        Episode episode4 = new Episode();
        episode4.setId(1003L);
        episode4.setAirstamp("2017-03-15T08:11:00-04:00");
        episode4.setRuntime(30);
        episode4.setShow(show4);
        result[3] = episode4;

        given(restTemplate.getForObject(anyString(), eq(Episode[].class), eq("GB"))).willReturn(result);

        List<Channel> actual = delegate.getProgrammeListing();
        assertEquals("Wrong result", expected, actual);
    }

    /**
     * Tests result mapping when REST response was invalid (e.g. 500 error, invalid JSON, etc).
     */
    @Test
    public void responseError() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        GaugeService gaugeService = mock(GaugeService.class);
        ProgrammeListingDelegate delegate = new TVMazeProgrammeListingDelegate(restTemplate, gaugeService);

        given(restTemplate.getForObject(anyString(), eq(Episode[].class), eq("GB")))
                .willThrow(new RestClientException("Oops"));

        try {
            delegate.getProgrammeListing();
            fail("Should have thrown wrapped exception");

        } catch (ProgrammeListingException ex) {
            // success
        }
    }

    /**
     * Tests result mapping when returned an invalid result (episode with missing show).
     */
    @Test
    public void missingShow() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        GaugeService gaugeService = mock(GaugeService.class);
        ProgrammeListingDelegate delegate = new TVMazeProgrammeListingDelegate(restTemplate, gaugeService);

        Episode[] result = new Episode[1];
        Episode episode1 = new Episode();
        episode1.setId(1000L);
        episode1.setAirstamp("2017-03-15T08:10:00-04:00");
        episode1.setRuntime(30);
        result[0] = episode1;

        given(restTemplate.getForObject(anyString(), eq(Episode[].class), eq("GB"))).willReturn(result);

        try {
            delegate.getProgrammeListing();
            fail("Should have thrown wrapped exception");

        } catch (ProgrammeListingException ex) {
            // success
        }
    }

    /**
     * Tests result mapping when returned an invalid result (episode and show with missing network).
     */
    @Test
    public void missingNetwork() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        GaugeService gaugeService = mock(GaugeService.class);
        ProgrammeListingDelegate delegate = new TVMazeProgrammeListingDelegate(restTemplate, gaugeService);

        Episode[] result = new Episode[1];
        Show show1 = new Show();
        show1.setId(1L);
        show1.setName("Show #1");
        show1.setType("AAA");
        Episode episode1 = new Episode();
        episode1.setId(1000L);
        episode1.setAirstamp("2017-03-15T08:10:00-04:00");
        episode1.setRuntime(30);
        episode1.setShow(show1);
        result[0] = episode1;

        given(restTemplate.getForObject(anyString(), eq(Episode[].class), eq("GB"))).willReturn(result);

        try {
            delegate.getProgrammeListing();
            fail("Should have thrown wrapped exception");

        } catch (ProgrammeListingException ex) {
            // success
        }
    }

    /**
     * Tests result mapping when returned an invalid timestamp.
     */
    @Test
    public void invalidDate() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        GaugeService gaugeService = mock(GaugeService.class);
        ProgrammeListingDelegate delegate = new TVMazeProgrammeListingDelegate(restTemplate, gaugeService);

        Episode[] result = new Episode[1];
        Network network1 = new Network();
        network1.setId(100L);
        network1.setName("Network #1");
        Show show1 = new Show();
        show1.setId(1L);
        show1.setName("Show #1");
        show1.setType("AAA");
        show1.setNetwork(network1);
        Episode episode1 = new Episode();
        episode1.setId(1000L);
        episode1.setAirstamp("1111222233334444"); // not a valid ISO 8601 timestamp
        episode1.setRuntime(30);
        episode1.setShow(show1);
        result[0] = episode1;

        given(restTemplate.getForObject(anyString(), eq(Episode[].class), eq("GB"))).willReturn(result);

        try {
            delegate.getProgrammeListing();
            fail("Should have thrown wrapped exception");

        } catch (ProgrammeListingException ex) {
            // success
        }
    }
}
