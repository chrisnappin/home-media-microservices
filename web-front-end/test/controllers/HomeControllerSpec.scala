package controllers

import play.api.test.{FakeRequest, PlaySpecification, WithApplication}

/**
 * Tests the <code>HomeController</code> class.
 */
class HomeControllerSpec extends PlaySpecification {

  "HomeController GET" should {

    "render the index page from a new instance of controller" in {
      val controller = new HomeController
      val home = controller.index().apply(FakeRequest())

      status(home) must be equalTo OK
      contentType(home) must be equalTo Some("text/html")
      contentAsString(home) must contain("Welcome to Play")
    }

    "render the index page from the application" in new WithApplication {
      val controller = app.injector.instanceOf[HomeController]
      val home = controller.index().apply(FakeRequest())

      status(home) must be equalTo OK
      contentType(home) must be equalTo Some("text/html")
      contentAsString(home) must contain("Welcome to Play")
    }

    // TODO: move this to an integration test...
    "render the index page from the router" in new WithApplication {
      // Need to specify Host header to get through AllowedHostsFilter
      val request = FakeRequest(GET, "/").withHeaders("Host" -> "localhost")
      val home = route(app, request).get

      status(home) must be equalTo OK
      contentType(home) must be equalTo Some("text/html")
      contentAsString(home) must contain("Welcome to Play")
    }
  }
}
