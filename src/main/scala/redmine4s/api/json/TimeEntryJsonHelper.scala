package redmine4s.api.json

import org.joda.time.{DateTime, LocalDate}
import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model.{CustomFieldValue, TimeEntry}

trait TimeEntryJsonHelper extends CustomFieldJsonHelper {
  implicit val timeEntryReads: Reads[TimeEntry] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'project).read[(Long, String)] ~
      ((__ \ 'issue \ 'id).readNullable[Long] orElse (__ \ 'issue).readNullable[Long]) ~
      (__ \ 'user).read[(Long, String)] ~
      (__ \ 'activity).read[(Long, String)] ~
      (__ \ 'hours).read[Double] ~
      (__ \ 'comments).read[String] ~
      (__ \ 'spent_on).read[LocalDate](dateReads) ~
      (__ \ 'created_on).read[DateTime](timeReads) ~
      (__ \ 'updated_on).read[DateTime](timeReads) ~
      (__ \ 'custom_fields).readNullable[Seq[CustomFieldValue]]
    ) (TimeEntry.apply _)
}
