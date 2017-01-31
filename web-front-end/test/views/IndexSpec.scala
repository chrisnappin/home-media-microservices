package views

import play.api.test.PlaySpecification

/**
  * Tests the <code>index.scala.html</code> view template.
  */
class IndexSpec extends PlaySpecification {

  "index template" should {

    "render the page" in {
      val html = views.html.index()
      contentAsString(html) must contain("Welcome to the Home Media System")
    }
  }
}
