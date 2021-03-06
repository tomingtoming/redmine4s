package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads.pure
import play.api.libs.json._
import redmine4s.api.model.{IssuePriority, TimeEntryActivity}

trait EnumerationsJsonHelper {
  implicit val issuePriorityReads: Reads[IssuePriority] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      ((__ \ 'is_default).read[Boolean] or pure(false))
    ) (IssuePriority.apply _)
  implicit val timeEntryActivitiesReads: Reads[TimeEntryActivity] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      ((__ \ 'is_default).read[Boolean] or pure(false))
    ) (TimeEntryActivity.apply _)
}
