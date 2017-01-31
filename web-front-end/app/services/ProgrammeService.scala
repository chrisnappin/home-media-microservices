package services

import javax.inject.{Inject, Singleton}

import com.google.inject.ImplementedBy
import model.Programme
import play.api.Logger

import scala.concurrent.{ExecutionContext, Future}

/**
  * Service to handle programme listings.
  */
@ImplementedBy(classOf[ProgrammeServiceImpl])
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
    Future { Seq(
      Programme("Dummy Programme #1"),
      Programme("Dummy Programme #2"),
      Programme("Dummy Programme #3"),
      Programme("Dummy Programme #4")
    ) }
  }
}
