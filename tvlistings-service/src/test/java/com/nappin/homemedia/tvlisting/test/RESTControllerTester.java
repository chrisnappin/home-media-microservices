package com.nappin.homemedia.tvlisting.test;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Utility used to simplify testing Spring REST controllers in a consistent manner.
 */
public class RESTControllerTester {

    private final MockMvc mvc;

    public RESTControllerTester(MockMvc mvc) {
        this.mvc = mvc;
    }

    /**
     * Tests that the specified request was successful (200), and contained the expected JSON result.
     * @param mvc               The MockMvc to use
     * @param requestBuilder    The request to use
     * @param expectedResponse  The expected response to check for
     * @throws Exception    Test failed
     */
    public void isSuccessful(MockMvc mvc, MockHttpServletRequestBuilder requestBuilder,
                                         String expectedResponse) throws Exception {
        mvc.perform(requestBuilder.accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(expectedResponse));
    }

    /**
     * Tests that the specified request was successful (200), but contained no response.
     * @param mvc               The MockMvc to use
     * @param requestBuilder    The request to use
     * @throws Exception    Test failed
     */
    public void isSuccessfulEmpty(MockMvc mvc, MockHttpServletRequestBuilder requestBuilder) throws Exception {
        mvc.perform(requestBuilder).andExpect(status().isOk());
    }

    /**
     * Tests that the specified request (with no JSON accept header) was successful (200), and contained the
     * expected JSON result.
     * @param mvc               The MockMvc to use
     * @param requestBuilder    The request to use
     * @param expectedResponse  The expected response to check for
     * @throws Exception    Test failed
     */
    public void isSuccessfulNoAccept(MockMvc mvc, MockHttpServletRequestBuilder requestBuilder,
                             String expectedResponse) throws Exception {
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(expectedResponse));
    }

    /**
     * Tests that the specified request was not found (404), and did not contain any result.
     * @param mvc               The MockMvc to use
     * @param requestBuilder    The request to use
     * @throws Exception    Test failed
     */
    public void isNotFound(MockMvc mvc, MockHttpServletRequestBuilder requestBuilder) throws Exception {
        mvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    /**
     * Tests that the specified request was a bad request (400), and did not contain any result.
     * @param mvc               The MockMvc to use
     * @param requestBuilder    The request to use
     * @throws Exception    Test failed
     */
    public void isBadRequest(MockMvc mvc, MockHttpServletRequestBuilder requestBuilder) throws Exception {
        mvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

        /**
         * Tests that the specified request (with an XML accept header) was not acceptable (406), and did not
         * contain any result.
         * @param mvc               The MockMvc to use
         * @param requestBuilder    The request to use
         * @throws Exception    Test failed
         */
    public void isNotAcceptable(MockMvc mvc, MockHttpServletRequestBuilder requestBuilder) throws Exception {
        mvc.perform(requestBuilder.accept(MediaType.APPLICATION_XML))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string(""));
    }

    /**
     * Tests that the specified request was created (201), and did not contain any result.
     * @param mvc               The MockMvc to use
     * @param requestBuilder    The request to use
     * @throws Exception    Test failed
     */
    public void isCreated(MockMvc mvc, MockHttpServletRequestBuilder requestBuilder) throws Exception {
        mvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().string(""));
    }

    /**
     * Tests that the specified request was an internal server error (500), and contained the expected JSON result.
     * @param mvc               The MockMvc to use
     * @param requestBuilder    The request to use
     * @param expectedResponse  The expected response to check for
     * @throws Exception    Test failed
     */
    public void isInternalServerError(MockMvc mvc, MockHttpServletRequestBuilder requestBuilder,
                             String expectedResponse) throws Exception {
        mvc.perform(requestBuilder.accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(expectedResponse));
    }
}
