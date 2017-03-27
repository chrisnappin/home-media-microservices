package model

import java.time.ZonedDateTime

import play.api.libs.json.{JsPath, Reads}
import play.api.libs.functional.syntax._

/** Encapsulates a TV programme. */
case class Programme(name: String, programmeId: Long, programmeType: String, startDateTime: ZonedDateTime,
                     runningTime: Int, seriesId: Long)
object Programme {
  implicit val programmeReads: Reads[Programme] = (
    (JsPath \ "name").read[String] and
    (JsPath \ "programmeId").read[Long] and
    (JsPath \ "type").read[String] and
    (JsPath \ "startDateTime").read[ZonedDateTime] and
    (JsPath \ "runningTime").read[Int] and
    (JsPath \ "seriesId").read[Long]
  )(Programme.apply _)
}

/** Encapsulates a TV channel with listing (possibly empty). */
case class Channel(name: String, id: Long, listing: Seq[Programme])
object Channel {
  implicit val channelReads: Reads[Channel] = (
    (JsPath \ "name").read[String] and
    (JsPath \ "id").read[Long] and
    (JsPath \ "listing").read[Seq[Programme]]//.orElse(Reads.pure(Nil))
  )(Channel.apply _)
}

