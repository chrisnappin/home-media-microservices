package controllers

import akka.actor.ActorSystem
import org.specs2.mock.Mockito
import play.api.test.{FakeRequest, PlaySpecification}
import services.ProgrammeService

import scala.concurrent.Future

/**
 * Tests the <code>ProgrammeController</code> class.
 */
class ProgrammeControllerSpec extends PlaySpecification with Mockito {

  implicit val context = scala.concurrent.ExecutionContext.Implicits.global

  // use a real Akka actor system since that is part of the code under test
  val actorSystem = ActorSystem("TestActorSystem")

  "ProgrammeController GET" should {

    "list the programmes on success" in {
      val mockProgrammeService = mock[ProgrammeService]
      mockProgrammeService.getListing() returns Future.successful { Seq() }

      val controller = new ProgrammeController(mockProgrammeService, actorSystem)
      val listing = controller.list().apply(FakeRequest())

      status(listing) must be equalTo OK
      contentType(listing) must be equalTo Some("text/html")
      contentAsString(listing) must contain("Programme Listing")
    }

    "show error message on failure" in {
      val mockProgrammeService = mock[ProgrammeService]
      mockProgrammeService.getListing() returns Future.failed { new RuntimeException("Oops") }

      val controller = new ProgrammeController(mockProgrammeService, actorSystem)
      val listing = controller.list().apply(FakeRequest())

      status(listing) must be equalTo INTERNAL_SERVER_ERROR
      contentType(listing) must be equalTo Some("text/html")
      contentAsString(listing) must contain("Oops")
    }

    "show error message on timeout" in {
      val mockProgrammeService = mock[ProgrammeService]

      // thread must sleep for longer than timeout value...
      mockProgrammeService.getListing() returns Future { Thread.sleep(1000L); Seq() }

      val controller = new ProgrammeController(mockProgrammeService, actorSystem)
      val listing = controller.list().apply(FakeRequest())

      status(listing) must be equalTo INTERNAL_SERVER_ERROR
      contentType(listing) must be equalTo Some("text/html")
      contentAsString(listing) must contain("Programme Service did not respond")
    }
  }
}
