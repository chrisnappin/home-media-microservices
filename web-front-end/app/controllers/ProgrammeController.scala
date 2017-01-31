package controllers

import akka.pattern.after
import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import model.Programme
import play.api.Logger
import play.api.mvc.{Action, Controller}
import services.ProgrammeService

import scala.concurrent.{Future, TimeoutException}
import scala.concurrent.duration._
import scala.util.control.NonFatal


/**
 * Controller for the programme listings page.
 */
@Singleton
class ProgrammeController @Inject() (val programmeService: ProgrammeService, val actorSystem: ActorSystem)
    extends Controller {

  /** The logger to use. */
  val logger: Logger = Logger(this.getClass())

  /**
   * Renders the programme listing page.
   */
  def list = Action.async { implicit request =>
    implicit val context = scala.concurrent.ExecutionContext.Implicits.global

    // TODO: extract out timeout boilerplate code on the third time of writing!

    lazy val programmesResult: Future[Seq[Programme]] = {
      Logger.debug("calling programmeService.getListing")
      val listing = programmeService.getListing()
      Logger.debug("programmeService.getListing returned")
      listing
    }

    lazy val timeout = after(500.millis, actorSystem.scheduler)(
      Future.failed(new TimeoutException("Future timed out!")))

    Future.firstCompletedOf(Seq(programmesResult, timeout)).map { programmes =>
      Logger.debug("programme successfully returned")
      Ok(views.html.programmeListing(programmes))

    } recover {
      case e: TimeoutException =>
        Logger.error("programmeService timed out")
        InternalServerError(views.html.error("Programme Service did not respond"))

      case NonFatal(t) =>
        Logger.error("programmeService failed: " + t.getMessage)
        InternalServerError(views.html.error(t.getMessage))
    }
  }
}
