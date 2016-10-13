package redmine4s.api.json

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model.News

object NewsJsonHelper extends BaseJsonHelper {
  implicit val newsReads: Reads[News] = (
    (__ \ "id").read[Long] ~
      (__ \ "project").read[(Long, String)] ~
      (__ \ "author").read[(Long, String)] ~
      (__ \ "title").read[String] ~
      (__ \ "summary").read[String] ~
      (__ \ "description").read[String] ~
      (__ \ "created_on").read[DateTime](timeReads)
    ) (News.apply _)
}
