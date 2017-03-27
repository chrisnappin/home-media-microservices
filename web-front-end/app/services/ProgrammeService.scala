package services

import javax.inject.{Inject, Singleton}

import com.google.inject.ImplementedBy
import model.Channel
import play.api.{Configuration, Logger}
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

/**
  * Service to handle programme listings.
  */
@ImplementedBy(classOf[ProgrammeServiceImpl])
trait ProgrammeService {

  def getListing()(implicit ec: ExecutionContext): Future[Seq[Channel]]
}

@Singleton
class ProgrammeServiceImpl @Inject() (val ws: WSClient, configuration: Configuration) extends ProgrammeService {

  /** The logger to use. */
  val logger: Logger = Logger(this.getClass)

  /**
    * Get a programme listing.
    * @param ec   The implicit execution context
    * @return A sequence of channels, with programme listings
    */
  def getListing()(implicit ec: ExecutionContext): Future[Seq[Channel]] = {
    logger.debug("In programmeService.getListing...")

    // TODO: add metrics and timings

    import scala.concurrent.duration._

    val request: WSRequest = ws.url(configuration.getString("tvlistings.programme.get").get)
                      .withRequestTimeout(configuration.getMilliseconds("tvlistings.timeout").get.millis)

    val response: Future[WSResponse] = request.get()

    response.map { r =>
      val result = r.json.validate[Seq[Channel]]
      result.fold(
        invalid => {
          val message = "Error deserialising programme listing JSON: " + result
          logger.error(message)
          throw new RuntimeException(message)
        },
        valid => {
          logger.debug("Listing successfully received and parsed")
          result.get
        }
      )

    } recover {
      case NonFatal(t) =>
        val message = "Error getting programme listing"
        logger.error(message, t)
        throw new RuntimeException(message, t)
    }
  }
}
