package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json._

trait BaseJsonHelper {
  val dateReads = Reads.jodaLocalDateReads("yyyy-MM-dd")
  val timeReads = Reads.jodaDateReads("yyyy-MM-dd'T'HH:mm:ssZ")
  implicit val idNameReads: Reads[(Long, String)] = (
    (__ \ 'id).read[Long] and (__ \ 'name).read[String]
    ) { (id, name) => (id, name) }
  val idValueWrites: Writes[(Long, String)] = (
    (__ \ 'id).write[Long] ~ (__ \ 'value).write[String]
    ).tupled
}
