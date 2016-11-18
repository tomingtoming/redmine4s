package redmine4s.api.json

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model._

trait JournalJsonHelper extends BaseJsonHelper {
  implicit val journalDetailsReads: Reads[JournalDetails] = (
    (__ \ 'property).read[String] ~
      (__ \ 'name).read[String] ~
      (__ \ 'old_value).readNullable[String] ~
      (__ \ 'new_value).readNullable[String]
    ) (JournalDetails.apply _)
  implicit val journalReads: Reads[Journal] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'user).read[(Long, String)] ~
      (__ \ 'notes).readNullable[String] ~
      (__ \ 'created_on).read[DateTime](timeReads) ~
      (__ \ 'details).read[Seq[JournalDetails]]
    ) (Journal.apply _)
}
