package redmine4s.api.json

import org.joda.time.{DateTime, LocalDate}
import play.api.libs.functional.syntax._
import play.api.libs.json._

trait BaseJsonHelper {
  val dateReads: Reads[LocalDate] = Reads.jodaLocalDateReads("yyyy-MM-dd")
  val timeReads: Reads[DateTime] = Reads.jodaDateReads("yyyy-MM-dd'T'HH:mm:ssZ")
  val dateWrites: Writes[LocalDate] = JsPath.write[String].contramap(_.toString)
  val timeWrites: Writes[DateTime] = JsPath.write[String].contramap(_.toString)
  implicit val idNameReads: Reads[(Long, String)] = (
    (__ \ 'id).read[Long] and (__ \ 'name).read[String]
    ) { (id, name) => (id, name) }
  implicit val idValueWrites: Writes[(Long, String)] = (
    (__ \ 'id).write[Long] ~ (__ \ 'value).write[String]
    ).tupled
}
