package views

import java.time.ZonedDateTime

import model.{Channel, Programme}
import play.api.test.PlaySpecification

/**
  * Tests the <code>index.scala.html</code> view template.
  */
class ProgrammeListingSpec extends PlaySpecification {

  "programmeListing template" should {

    "render the page" in {
      val html = views.html.programmeListing(Seq(
        Channel("Test Channel", 1000L, Seq(
          Programme("Programme 1", 1L, "Type 1", ZonedDateTime.now(), 30, 10L),
          Programme("Programme 2", 2L, "Type 2", ZonedDateTime.now(), 40, 11L)
        ))
      ))

      contentAsString(html) must contain("Programme Listing")
      contentAsString(html) must contain("Test Channel")
      contentAsString(html) must contain("Programme 1")
      contentAsString(html) must contain("Programme 2")
    }
  }
}
