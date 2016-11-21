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
    ) { (id: Long, project: (Long, String), issueId: Option[Long], user: (Long, String), activity: (Long, String), hours: Double, comments: String, spentOn: LocalDate, createdOn: DateTime, updatedOn: DateTime, customField: Option[Seq[CustomFieldValue]]) =>
    TimeEntry(id, project, issueId, user, activity, hours, comments, spentOn, createdOn, updatedOn, customField, null)
  }
  implicit val timeEntryCreateForProjectWrites = (
    (__ \ 'time_entry \ 'project_id).write[Long] ~
      (__ \ 'time_entry \ 'spent_on).write[LocalDate] ~
      (__ \ 'time_entry \ 'hours).write[Double] ~
      (__ \ 'time_entry \ 'activity_id).write[Long] ~
      (__ \ 'time_entry \ 'comments).write[String] ~
      (__ \ 'time_entry \ 'custom_fields).writeNullable[Seq[(Long, String)]]
    ).tupled
  implicit val timeEntryCreateForIssueWrites = (
    (__ \ 'time_entry \ 'issue_id).write[Long] ~
      (__ \ 'time_entry \ 'spent_on).write[LocalDate] ~
      (__ \ 'time_entry \ 'hours).write[Double] ~
      (__ \ 'time_entry \ 'activity_id).write[Long] ~
      (__ \ 'time_entry \ 'comments).write[String] ~
      (__ \ 'time_entry \ 'custom_fields).writeNullable[Seq[(Long, String)]]
    ).tupled
  implicit val timeEntryUpdateForProjectWrites = (
    (__ \ 'time_entry \ 'project_id).writeNullable[Long] ~
      (__ \ 'time_entry \ 'spent_on).writeNullable[LocalDate] ~
      (__ \ 'time_entry \ 'hours).writeNullable[Double] ~
      (__ \ 'time_entry \ 'activity_id).writeNullable[Long] ~
      (__ \ 'time_entry \ 'comments).writeNullable[String] ~
      (__ \ 'time_entry \ 'custom_fields).writeNullable[Seq[(Long, String)]]
    ).tupled
  implicit val timeEntryUpdateForIssueWrites = (
    (__ \ 'time_entry \ 'issue_id).writeNullable[Long] ~
      (__ \ 'time_entry \ 'spent_on).writeNullable[LocalDate] ~
      (__ \ 'time_entry \ 'hours).writeNullable[Double] ~
      (__ \ 'time_entry \ 'activity_id).writeNullable[Long] ~
      (__ \ 'time_entry \ 'comments).writeNullable[String] ~
      (__ \ 'time_entry \ 'custom_fields).writeNullable[Seq[(Long, String)]]
    ).tupled
}
