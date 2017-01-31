package views

import play.api.test.PlaySpecification

/**
  * Tests the <code>error.scala.html</code> view template.
  */
class ErrorSpec extends PlaySpecification {

  "error template" should {

    "render the error message" in {
      val html = views.html.error("Oops")

      contentAsString(html) must contain("Error")
      contentAsString(html) must contain("Oops")
    }
  }
}
