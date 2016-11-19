package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model.{IssuePriority, TimeEntryActivity}

trait  EnumerationsJsonHelper {
  implicit val issuePriorityReads: Reads[IssuePriority] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      (__ \ 'is_default).readNullable[Boolean]
    ) { (id: Long, name: String, isDefaultOpt: Option[Boolean]) =>
    IssuePriority(id, name, isDefaultOpt.getOrElse(false), null)
  }
  implicit val timeEntryActivitiesReads: Reads[TimeEntryActivity] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      (__ \ 'is_default).readNullable[Boolean]
    ) { (id: Long, name: String, isDefaultOpt: Option[Boolean]) =>
    TimeEntryActivity(id, name, isDefaultOpt.getOrElse(false), null)
  }
}
