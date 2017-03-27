package model

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME

import org.specs2.mutable.Specification
import play.api.libs.json.{JsError, JsSuccess, Json}

/**
  * Tests the <code>Channel</code> and <code?Programme</code> classes (JSON parsing).
  */
class ProgrammeSpec extends Specification {

  "Programme" should {

    "deserialise valid single object" in {
      val jsonInput = Json.parse("""
          |{
            |"programmeId":100,
            |"name":"Example Programme #1",
            |"type":"AAA",
            |"startDateTime":"2017-03-15T08:10:00+00:00",
            |"runningTime":30,
            |"seriesId":1
          |}
          |""".stripMargin)

      val expected = Programme("Example Programme #1", 100L, "AAA",
        ZonedDateTime.parse("2017-03-15T08:10:00+00:00", ISO_OFFSET_DATE_TIME), 30, 1L)

      val result = jsonInput.validate[Programme]

      result must beAnInstanceOf[JsSuccess[Programme]]
      result.get must be equalTo expected
    }

    "deserialise valid array" in {
      val jsonInput = Json.parse("""
           |[
             |{
               |"programmeId":100,
               |"name":"Example Programme #1",
               |"type":"AAA",
               |"startDateTime":"2017-03-15T08:10:00+00:00",
               |"runningTime":30,
               |"seriesId":1
             |},
             |{
               |"programmeId":101,
               |"name":"Example Programme #2",
               |"type":"BBB",
               |"startDateTime":"2017-03-15T08:40:00+00:00",
               |"runningTime":20,
               |"seriesId":2
             |},
             |{
               |"programmeId":102,
               |"name":"Example Programme #3",
               |"type":"CCC",
               |"startDateTime":"2017-03-15T09:00:00+00:00",
               |"runningTime":60,
               |"seriesId":3
             |}
           |]
           |""".stripMargin)

      val expected = Seq(
        Programme("Example Programme #1", 100L, "AAA",
          ZonedDateTime.parse("2017-03-15T08:10:00+00:00", ISO_OFFSET_DATE_TIME), 30, 1L),
        Programme("Example Programme #2", 101L, "BBB",
          ZonedDateTime.parse("2017-03-15T08:40:00+00:00", ISO_OFFSET_DATE_TIME), 20, 2L),
        Programme("Example Programme #3", 102L, "CCC",
          ZonedDateTime.parse("2017-03-15T09:00:00+00:00", ISO_OFFSET_DATE_TIME), 60, 3L)
      )

      val result = jsonInput.validate[Seq[Programme]]

      result must beAnInstanceOf[JsSuccess[Seq[Programme]]]
      result.get must be equalTo expected
    }
  }

  "Channel" should {

    "reject missing listing" in {
      val jsonInput = Json.parse("""
             |{
             |"name":"Example Channel #1",
             |"id":1234
             |}
             |""".stripMargin)

      val expected = Channel("Example Channel #1", 1234L, Seq())

      val result = jsonInput.validate[Channel]

      result must beAnInstanceOf[JsError]
    }

    "deserialise valid single object (with empty listing)" in {
      val jsonInput = Json.parse("""
             |{
             |"name":"Example Channel #1",
             |"id":1234,
             |"listing":[]
             |}
             |""".stripMargin)

      val expected = Channel("Example Channel #1", 1234L, Seq())

      val result = jsonInput.validate[Channel]

      result must beAnInstanceOf[JsSuccess[Channel]]
      result.get must be equalTo expected
    }

    "deserialise valid single object (with listing)" in {
      val jsonInput = Json.parse("""
             |{
             |"name":"Example Channel #1",
             |"id":1234,
             |"listing":[
               |{
                 |"programmeId":100,
                 |"name":"Example Programme #1",
                 |"type":"AAA",
                 |"startDateTime":"2017-03-15T08:10:00+00:00",
                 |"runningTime":30,
                 |"seriesId":1
               |},
               |{
                 |"programmeId":101,
                 |"name":"Example Programme #2",
                 |"type":"BBB",
                 |"startDateTime":"2017-03-15T08:40:00+00:00",
                 |"runningTime":20,
                 |"seriesId":2
               |}
             |]}
             |""".stripMargin)

      val expected = Channel("Example Channel #1", 1234L, Seq(
        Programme("Example Programme #1", 100L, "AAA",
          ZonedDateTime.parse("2017-03-15T08:10:00+00:00", ISO_OFFSET_DATE_TIME), 30, 1L),
        Programme("Example Programme #2", 101L, "BBB",
          ZonedDateTime.parse("2017-03-15T08:40:00+00:00", ISO_OFFSET_DATE_TIME), 20, 2L)
      ))

      val result = jsonInput.validate[Channel]

      result must beAnInstanceOf[JsSuccess[Channel]]
      result.get must be equalTo expected
    }

    "deserialise valid array (with listing)" in {
      val jsonInput = Json.parse("""
             |[
               |{
                 |"name":"Example Channel #1",
                 |"id":1234,
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
                 |"name":"Example Channel #2",
                 |"id":1235,
                 |"listing":
                   |[
                     |{
                       |"programmeId":101,
                       |"name":"Example Programme #2",
                       |"type":"BBB",
                       |"startDateTime":"2017-03-15T08:40:00+00:00",
                       |"runningTime":20,
                       |"seriesId":2
                     |}
                   |]
               |}
             |]
             |""".stripMargin)

      val expected = Seq(
        Channel("Example Channel #1", 1234L, Seq(
          Programme("Example Programme #1", 100L, "AAA",
            ZonedDateTime.parse("2017-03-15T08:10:00+00:00", ISO_OFFSET_DATE_TIME), 30, 1L)
          )),
        Channel("Example Channel #2", 1235L, Seq(
          Programme("Example Programme #2", 101L, "BBB",
            ZonedDateTime.parse("2017-03-15T08:40:00+00:00", ISO_OFFSET_DATE_TIME), 20, 2L)
          ))
      )

      val result = jsonInput.validate[Seq[Channel]]

      result must beAnInstanceOf[JsSuccess[Seq[Channel]]]
      result.get must be equalTo expected
    }
  }
}
