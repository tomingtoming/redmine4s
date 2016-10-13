package redmine4s.api.json

import org.joda.time.{DateTime, LocalDate}
import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model._

object IssueJsonHelper extends BaseJsonHelper {
  implicit val customFieldReads: Reads[CustomField] = (
    (__ \ "id").read[Long] ~
      (__ \ "name").read[String] ~
      (__ \ "value").readNullable[String]
    ) (CustomField.apply _)
  implicit val attachmentReads: Reads[Attachment] = (
    (__ \ "id").read[Long] ~
      (__ \ "filename").read[String] ~
      (__ \ "filesize").read[Long] ~
      (__ \ "description").read[String] ~
      (__ \ "content_url").read[String] ~
      (__ \ "author").read[(Long, String)] ~
      (__ \ "created_on").read[DateTime](timeReads)
    ) (Attachment.apply _)
  implicit val changesetReads: Reads[ChangeSet] = (
    (__ \ "revision").read[String] ~
      (__ \ "user").readNullable[(Long, String)] ~
      (__ \ "comments").read[String] ~
      (__ \ "committed_on").read[DateTime](timeReads)
    ) (ChangeSet.apply _)
  implicit val journalDetailsReads: Reads[JournalDetails] = (
    (__ \ "property").read[String] ~
      (__ \ "name").read[String] ~
      (__ \ "old_value").readNullable[String] ~
      (__ \ "new_value").readNullable[String]
    ) (JournalDetails.apply _)
  implicit val journalReads: Reads[Journal] = (
    (__ \ "id").read[Long] ~
      (__ \ "user").read[(Long, String)] ~
      (__ \ "notes").readNullable[String] ~
      (__ \ "created_on").read[DateTime](timeReads) ~
      (__ \ "details").read[Seq[JournalDetails]]
    ) (Journal.apply _)
  implicit val issueChildReads: Reads[IssueChild] = (
    (__ \ "id").read[Long] ~
      (__ \ "tracker").read[(Long, String)] ~
      (__ \ "subject").read[String]
    ) (IssueChild.apply _)
  implicit val issueRelationReads: Reads[IssueRelation] = (
    (__ \ "id").read[Long] ~
      (__ \ "issue_id").read[Long] ~
      (__ \ "issue_to_id").read[Long] ~
      (__ \ "relation_type").read[String] ~
      (__ \ "delay").readNullable[Int]
    ) (IssueRelation.apply _)
  implicit val issueRequiredFieldsReads: Reads[IssueRequiredFields] = (
    (__ \ "id").read[Long] ~
      (__ \ "project").read[(Long, String)] ~
      (__ \ "tracker").read[(Long, String)] ~
      (__ \ "status").read[(Long, String)] ~
      (__ \ "priority").read[(Long, String)] ~
      (__ \ "author").read[(Long, String)] ~
      (__ \ "subject").read[String] ~
      (__ \ "done_ratio").read[Int] ~
      (__ \ "created_on").read[DateTime](timeReads) ~
      (__ \ "updated_on").read[DateTime](timeReads)
    ) (IssueRequiredFields.apply _)
  implicit val issueOptionalFieldsReads: Reads[IssueOptionalFields] = (
    ((__ \ "parent" \ "id").readNullable[Long] orElse (__ \ "parent").readNullable[Long]) ~
      (__ \ "description").readNullable[String] ~
      (__ \ "fixed_version").readNullable[(Long, String)] ~
      (__ \ "assigned_to").readNullable[(Long, String)] ~
      (__ \ "category").readNullable[(Long, String)] ~
      (__ \ "start_date").readNullable[LocalDate](dateReads) ~
      (__ \ "due_date").readNullable[LocalDate](dateReads) ~
      (__ \ "actual_start_date").readNullable[LocalDate](dateReads) ~
      (__ \ "actual_due_date").readNullable[LocalDate](dateReads) ~
      (__ \ "estimated_hours").readNullable[Double] ~
      (__ \ "closed_on").readNullable[DateTime](timeReads) ~
      (__ \ "custom_fields").readNullable[Seq[CustomField]] ~
      (__ \ "watchers").readNullable[Seq[(Long, String)]] ~
      (__ \ "attachments").readNullable[Seq[Attachment]] ~
      (__ \ "changesets").readNullable[Seq[ChangeSet]] ~
      (__ \ "journals").readNullable[Seq[Journal]] ~
      (__ \ "children").readNullable[Seq[IssueChild]] ~
      (__ \ "relations").readNullable[Seq[IssueRelation]]
    ) (IssueOptionalFields.apply _)
  implicit val issueReads: Reads[Issue] = (
    __.read[IssueRequiredFields] ~ __.read[IssueOptionalFields]
    ) { (requiredFields, optionalFields) =>
    Issue(requiredFields, optionalFields, null)
  }
  implicit val issueCreateWrites = {
    implicit val ivw = idValueWrites
    (
      (__ \ 'issue \ 'subject).write[String] ~
        (__ \ 'issue \ 'project_id).write[Long] ~
        (__ \ 'issue \ 'tracker_id).writeNullable[Long] ~
        (__ \ 'issue \ 'status_id).writeNullable[Long] ~
        (__ \ 'issue \ 'priority_id).writeNullable[Long] ~
        (__ \ 'issue \ 'description).writeNullable[String] ~
        (__ \ 'issue \ 'category_id).writeNullable[Long] ~
        (__ \ 'issue \ 'fixed_version_id).writeNullable[Long] ~
        (__ \ 'issue \ 'assigned_to_id).writeNullable[Long] ~
        (__ \ 'issue \ 'parent_issue_id).writeNullable[Long] ~
        (__ \ 'issue \ 'custom_fields).writeNullable[Seq[(Long, String)]] ~
        (__ \ 'issue \ 'watcher_user_ids).writeNullable[Seq[Long]] ~
        (__ \ 'issue \ 'is_private).writeNullable[Boolean] ~
        (__ \ 'issue \ 'estimated_hours).writeNullable[Double]
      ).tupled
  }
  implicit val issueUpdateWrites = {
    implicit val ivw = idValueWrites
    (
      (__ \ 'issue \ 'subject).writeNullable[String] ~
        (__ \ 'issue \ 'project_id).writeNullable[Long] ~
        (__ \ 'issue \ 'tracker_id).writeNullable[Long] ~
        (__ \ 'issue \ 'status_id).writeNullable[Long] ~
        (__ \ 'issue \ 'priority_id).writeNullable[Long] ~
        (__ \ 'issue \ 'description).writeNullable[String] ~
        (__ \ 'issue \ 'category_id).writeNullable[Long] ~
        (__ \ 'issue \ 'fixed_version_id).writeNullable[Long] ~
        (__ \ 'issue \ 'assigned_to_id).writeNullable[Long] ~
        (__ \ 'issue \ 'parent_issue_id).writeNullable[Long] ~
        (__ \ 'issue \ 'custom_fields).writeNullable[Seq[(Long, String)]] ~
        (__ \ 'issue \ 'watcher_user_ids).writeNullable[Seq[Long]] ~
        (__ \ 'issue \ 'is_private).writeNullable[Boolean] ~
        (__ \ 'issue \ 'estimated_hours).writeNullable[Double]
      ).tupled
  }
}
