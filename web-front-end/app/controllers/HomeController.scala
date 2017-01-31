package controllers

import javax.inject.{Inject, Singleton}

import play.api.Logger
import play.api.mvc.{Action, Controller}

/**
 * This controller handles the home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  /** The logger to use. */
  val logger: Logger = Logger(this.getClass())

  /**
   * Renders the home page.
   */
  def index = Action { implicit request =>
    Logger.debug("Rendering the home page")
    Ok(views.html.index())
  }
}
