package services

import javax.inject.{Inject, Singleton}

import model.Programme
import play.api.Logger

import scala.concurrent.{ExecutionContext, Future}

/**
  * Service to handle programme listings.
  */
trait ProgrammeService {

  def getListing()(implicit ec: ExecutionContext): Future[Seq[Programme]]
}

@Singleton
class ProgrammeServiceImpl @Inject() extends ProgrammeService {

  /** The logger to use. */
  val logger: Logger = Logger(this.getClass())

  /**
    * Get a programme listing.
    * @param ec   The implicit execution context
    * @return A sequence of programmes
    */
  def getListing()(implicit ec: ExecutionContext): Future[Seq[Programme]] = {
    Logger.debug("In programmeService.getListing...")
    Future { Seq() }
  }
}
