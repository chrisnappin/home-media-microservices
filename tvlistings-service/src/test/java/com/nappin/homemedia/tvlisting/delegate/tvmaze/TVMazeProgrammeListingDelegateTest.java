package com.nappin.homemedia.tvlisting.delegate.tvmaze;

import com.nappin.homemedia.tvlisting.delegate.ProgrammeListingDelegate;
import com.nappin.homemedia.tvlisting.model.Programme;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
        ProgrammeListingDelegate delegate = new TVMazeProgrammeListingDelegate(restTemplate);

        List<Programme> expected = new ArrayList<>();

        Episode[] result = new Episode[0];
        given(restTemplate.getForObject(anyString(), eq(Episode[].class), eq("GB"))).willReturn(result);

        List<Programme> actual = delegate.getProgrammeListing();
        assertEquals("Wrong result", expected, actual);
    }

    /**
     * Tests result mapping when returned a basic populated result.
     */
    @Test
    public void basicResult() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        ProgrammeListingDelegate delegate = new TVMazeProgrammeListingDelegate(restTemplate);

        List<Programme> expected = new ArrayList<>();
        expected.add(new Programme("Show #1"));
        expected.add(new Programme("Show #2"));

        Episode[] result = new Episode[2];
        Show show1 = new Show();
        show1.setId(1L);
        show1.setName("Show #1");
        Episode episode1 = new Episode();
        episode1.setId(1000L);
        episode1.setShow(show1);
        result[0] = episode1;

        Show show2 = new Show();
        show2.setId(2L);
        show2.setName("Show #2");
        Episode episode2 = new Episode();
        episode2.setId(1001L);
        episode2.setShow(show2);
        result[1] = episode2;

        given(restTemplate.getForObject(anyString(), eq(Episode[].class), eq("GB"))).willReturn(result);

        List<Programme> actual = delegate.getProgrammeListing();
        assertEquals("Wrong result", expected, actual);
    }
}
