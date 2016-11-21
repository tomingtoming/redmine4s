package redmine4s.api.json

import org.joda.time.{DateTime, LocalDate}
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads.pure
import play.api.libs.json._
import redmine4s.api.model._

trait IssueJsonHelper extends AttachmentJsonHelper with CustomFieldJsonHelper with JournalJsonHelper with IssueRelationJsonHelper {
  implicit val changesetReads: Reads[ChangeSet] = (
    (__ \ 'revision).read[String] ~
      (__ \ 'user).readNullable[(Long, String)] ~
      (__ \ 'comments).read[String] ~
      (__ \ 'committed_on).read[DateTime](timeReads)
    ) (ChangeSet.apply _)
  implicit val childIssueReads: Reads[ChildIssue] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'tracker).read[(Long, String)] ~
      (__ \ 'subject).read[String]
    ) (ChildIssue.apply _)
  implicit val issueReads: Reads[Issue] = {
    val requiredFieldsReads = (
      (__ \ 'id).read[Long] ~
        (__ \ 'project).read[(Long, String)] ~
        (__ \ 'tracker).read[(Long, String)] ~
        (__ \ 'status).read[(Long, String)] ~
        (__ \ 'priority).read[(Long, String)] ~
        (__ \ 'author).read[(Long, String)] ~
        (__ \ 'subject).read[String] ~
        (__ \ 'done_ratio).read[Int] ~
        (__ \ 'created_on).read[DateTime](timeReads) ~
        (__ \ 'updated_on).read[DateTime](timeReads)
      ).tupled
    val optionalFieldsReads = (
      ((__ \ 'parent \ 'id).readNullable[Long] orElse (__ \ 'parent).readNullable[Long]) ~
        (__ \ 'description).readNullable[String] ~
        (__ \ 'fixed_version).readNullable[(Long, String)] ~
        (__ \ 'assigned_to).readNullable[(Long, String)] ~
        (__ \ 'category).readNullable[(Long, String)] ~
        (__ \ 'start_date).readNullable[LocalDate](dateReads) ~
        (__ \ 'due_date).readNullable[LocalDate](dateReads) ~
        (__ \ 'actual_start_date).readNullable[LocalDate](dateReads) ~
        (__ \ 'actual_due_date).readNullable[LocalDate](dateReads) ~
        (__ \ 'estimated_hours).readNullable[Double] ~
        (__ \ 'closed_on).readNullable[DateTime](timeReads) ~
        ((__ \ 'custom_fields).read[Seq[CustomFieldValue]] or pure(Seq.empty[CustomFieldValue])) ~
        (__ \ 'watchers).readNullable[Seq[(Long, String)]] ~
        (__ \ 'attachments).readNullable[Seq[Attachment]] ~
        (__ \ 'changesets).readNullable[Seq[ChangeSet]] ~
        (__ \ 'journals).readNullable[Seq[Journal]] ~
        (__ \ 'children).readNullable[Seq[ChildIssue]] ~
        (__ \ 'relations).readNullable[Seq[IssueRelation]]
      ).tupled
    (requiredFieldsReads ~ optionalFieldsReads) { (x: (Long, (Long, String), (Long, String), (Long, String), (Long, String), (Long, String), String, Int, DateTime, DateTime), y: (Option[Long], Option[String], Option[(Long, String)], Option[(Long, String)], Option[(Long, String)], Option[LocalDate], Option[LocalDate], Option[LocalDate], Option[LocalDate], Option[Double], Option[DateTime], Seq[CustomFieldValue], Option[Seq[(Long, String)]], Option[Seq[Attachment]], Option[Seq[ChangeSet]], Option[Seq[Journal]], Option[Seq[ChildIssue]], Option[Seq[IssueRelation]])) =>
      Issue(x._1, x._2, x._3, x._4, x._5, x._6, x._7, x._8, x._9, x._10, y._1, y._2, y._3, y._4, y._5, y._6, y._7, y._8, y._9, y._10, y._11, y._12, y._13, y._14, y._15, y._16, y._17, y._18, null)
    }
  }
  implicit val issueCreateWrites = (
    (__ \ 'issue \ 'subject).write[String] ~
      (__ \ 'issue \ 'project_id).write[Long] ~
      (__ \ 'issue \ 'tracker_id).writeNullable[Long] ~
      (__ \ 'issue \ 'status_id).writeNullable[Long] ~
      (__ \ 'issue \ 'priority_id).writeNullable[Long] ~
      (__ \ 'issue \ 'description).writeNullable[String] ~
      (__ \ 'issue \ 'done_ratio).writeNullable[Int] ~
      (__ \ 'issue \ 'category_id).writeNullable[Long] ~
      (__ \ 'issue \ 'start_date).writeNullable[LocalDate](dateWrites) ~
      (__ \ 'issue \ 'due_date).writeNullable[LocalDate](dateWrites) ~
      (__ \ 'issue \ 'actual_start_date).writeNullable[LocalDate](dateWrites) ~
      (__ \ 'issue \ 'actual_due_date).writeNullable[LocalDate](dateWrites) ~
      (__ \ 'issue \ 'fixed_version_id).writeNullable[Long] ~
      (__ \ 'issue \ 'assigned_to_id).writeNullable[Long] ~
      (__ \ 'issue \ 'parent_issue_id).writeNullable[Long] ~
      (__ \ 'issue \ 'custom_fields).writeNullable[Seq[(Long, String)]] ~
      (__ \ 'issue \ 'watcher_user_ids).writeNullable[Seq[Long]] ~
      (__ \ 'issue \ 'is_private).writeNullable[Boolean] ~
      (__ \ 'issue \ 'estimated_hours).writeNullable[Double] ~
      (__ \ 'issue \ 'uploads).writeNullable[Seq[UploadedFile]]
    ).tupled
  implicit val issueUpdateWrites = (
    (__ \ 'issue \ 'subject).writeNullable[String] ~
      (__ \ 'issue \ 'project_id).writeNullable[Long] ~
      (__ \ 'issue \ 'tracker_id).writeNullable[Long] ~
      (__ \ 'issue \ 'status_id).writeNullable[Long] ~
      (__ \ 'issue \ 'priority_id).writeNullable[Long] ~
      (__ \ 'issue \ 'description).writeNullable[String] ~
      (__ \ 'issue \ 'done_ratio).writeNullable[Int] ~
      (__ \ 'issue \ 'category_id).writeNullable[Long] ~
      (__ \ 'issue \ 'start_date).writeNullable[LocalDate](dateWrites) ~
      (__ \ 'issue \ 'due_date).writeNullable[LocalDate](dateWrites) ~
      (__ \ 'issue \ 'actual_start_date).writeNullable[LocalDate](dateWrites) ~
      (__ \ 'issue \ 'actual_due_date).writeNullable[LocalDate](dateWrites) ~
      (__ \ 'issue \ 'fixed_version_id).writeNullable[Long] ~
      (__ \ 'issue \ 'assigned_to_id).writeNullable[Long] ~
      (__ \ 'issue \ 'parent_issue_id).writeNullable[Long] ~
      (__ \ 'issue \ 'custom_fields).writeNullable[Seq[(Long, String)]] ~
      (__ \ 'issue \ 'watcher_user_ids).writeNullable[Seq[Long]] ~
      (__ \ 'issue \ 'is_private).writeNullable[Boolean] ~
      (__ \ 'issue \ 'estimated_hours).writeNullable[Double] ~
      (__ \ 'issue \ 'uploads).writeNullable[Seq[UploadedFile]]
    ).tupled
}
