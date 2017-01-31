package views

import model.Programme
import play.api.test.PlaySpecification

/**
  * Tests the <code>index.scala.html</code> view template.
  */
class ProgrammeListingSpec extends PlaySpecification {

  "programmeListing template" should {

    "render the page" in {
      val programmes = Seq(Programme("Programme 1"), Programme("Programme 2"))
      val html = views.html.programmeListing(programmes)

      contentAsString(html) must contain("Programme Listing")
      contentAsString(html) must contain("Programme 1")
      contentAsString(html) must contain("Programme 2")
    }
  }
}
