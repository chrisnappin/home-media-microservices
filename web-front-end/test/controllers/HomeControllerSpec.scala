package controllers

import org.specs2.mock.Mockito
import play.api.test.{FakeRequest, PlaySpecification}

/**
 * Tests the <code>HomeController</code> class.
 */
class HomeControllerSpec extends PlaySpecification with Mockito {

  "HomeController GET" should {

    "render the index page from a new instance of controller" in {
      val controller = new HomeController
      val home = controller.index().apply(FakeRequest())

      status(home) must be equalTo OK
      contentType(home) must be equalTo Some("text/html")
      contentAsString(home) must contain("Welcome to the Home Media System")
    }

    // TODO: move this to an integration test, either with real Router (and all deps mocked out)
    // or a partial router with just routes for this controller...
    /*"render the index page from the router" in new WithApplication {
      // Need to specify Host header to get through AllowedHostsFilter
      val request = FakeRequest(GET, "/").withHeaders("Host" -> "localhost")
      val home = route(app, request).get

      status(home) must be equalTo OK
      contentType(home) must be equalTo Some("text/html")
      contentAsString(home) must contain("Welcome to the Home Media System")
    }*/
  }
}
