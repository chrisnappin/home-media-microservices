package services

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME

import model.{Channel, Programme}
import org.specs2.mock.Mockito
import play.api.{Application, Configuration}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}
import play.api.test.{PlaySpecification, WithApplication}

import scala.concurrent.Future
import scala.concurrent.duration.Duration

/**
 * Tests the <code>ProgrammeServiceImpl</code> class.
 */
class ProgrammeServiceSpec extends PlaySpecification with Mockito {

  implicit val context = scala.concurrent.ExecutionContext.Implicits.global

  val appSettings: Map[String, Any] = Map(
    "tvlistings.programme.get" -> "http://test-url/wibble",
    "tvlistings.timeout" -> "2 seconds"
  )

  val app: Application = GuiceApplicationBuilder().configure(appSettings).build()

  "ProgrammeService" should {

    "return a channel (with empty listing) on success" in new WithApplication(app) {
      val (mockWS, configuration) = createMocks(Some("""
          |[
            |{
              |"name":"Channel #1",
              |"id":10,
              |"listing":[]
            |}
          |]
        """.stripMargin), false)

      val service = new ProgrammeServiceImpl(mockWS, configuration)
      val result: Seq[Channel] = await(service.getListing())

      result must be equalTo Seq(Channel("Channel #1", 10L, Seq()))
    }

    "return channels with listings on success" in new WithApplication(app) {
      val (mockWS, configuration) = createMocks(Some("""
          |[
            |{
              |"name":"Channel #1",
              |"id":10,
              |"listing":
                |[
                  |{
                    |"programmeId":100,
                    |"name":"Example Programme #1",
                    |"type":"AAA",
                    |"startDateTime":"2017-03-15T08:10:00+00:00",
                    |"runningTime":30,
                    |"seriesId":1
                  |}
                |]
            |},
            |{
              |"name":"Channel #2",
              |"id":11,
              |"listing":
              |[
                |{
                  |"programmeId":101,
                  |"name":"Example Programme #2",
                  |"type":"BBB",
                  |"startDateTime":"2017-03-15T08:00:00+00:00",
                  |"runningTime":45,
                  |"seriesId":2
                |}
              |]
            |}
          |]
        """.stripMargin), false)

      val service = new ProgrammeServiceImpl(mockWS, configuration)
      val result: Seq[Channel] = await(service.getListing())

      result must be equalTo Seq(
        Channel("Channel #1", 10L, Seq(
          Programme("Example Programme #1", 100L, "AAA",
            ZonedDateTime.parse("2017-03-15T08:10:00+00:00", ISO_OFFSET_DATE_TIME), 30, 1L)
        )),
        Channel("Channel #2", 11L, Seq(
          Programme("Example Programme #2", 101L, "BBB",
            ZonedDateTime.parse("2017-03-15T08:00:00+00:00", ISO_OFFSET_DATE_TIME), 45, 2L)
        ))
      )
    }

    "rethrow a 500 error" in {
      val (mockWS, configuration) = createMocks(None, true)
      val service = new ProgrammeServiceImpl(mockWS, configuration)
      await(service.getListing()) must throwAn[RuntimeException]
    }

    "rethrow a JSON error" in {
      val (mockWS, configuration) = createMocks(Some("[{\"wibble\":32}]"), false)
      val service = new ProgrammeServiceImpl(mockWS, configuration)
      await(service.getListing()) must throwAn[RuntimeException]
    }
  }

  /**
    * Create mocks populated with a response using the specified JSON.
    * @param json   The JSON response
    * @param error  Whether the result is an error
    * @return The mocks
    */
  private def createMocks(json: Option[String], error: Boolean): (WSClient, Configuration) = {
    val mockWS = mock[WSClient]
    val mockRequest = mock[WSRequest]
    val mockResponse = mock[WSResponse]
    val configuration = app.injector.instanceOf[Configuration]
    mockWS.url(anyString) returns mockRequest
    mockRequest.withRequestTimeout(any[Duration]) returns mockRequest

    if (error) {
      mockRequest.get() returns Future.failed(new RuntimeException("Oops"))
    } else {
      mockRequest.get() returns Future.successful { mockResponse }
    }

    if (json.isDefined) {
      mockResponse.json returns Json.parse(json.get)
    }

    (mockWS, configuration)
  }
}
