package redmine4s.api.json

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model._

trait ProjectJsonHelper extends RoleJsonHelper with CustomFieldJsonHelper with VersionJsonHelper with ProjectMembershipJsonHelper with IssueCategoryJsonHelper {
  val projectSummaryReads: Reads[Project] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      (__ \ 'description).readNullable[String] ~
      (__ \ 'homepage).readNullable[String] ~
      (__ \ 'is_public).read[Boolean](false) ~
      (__ \ 'parent).readNullable[(Long, String)] ~
      (__ \ 'created_on).read[DateTime](timeReads) ~
      (__ \ 'updated_on).read[DateTime](timeReads) ~
      (__ \ 'identifier).read[String] ~
      (__ \ 'custom_fields).readNullable[Seq[CustomFieldValue]]
    ) { (id, name, description, homepage, isPublic, parent, createdOn, updatedOn, identifier, customField) =>
    ProjectSummary(id, name, description, homepage, isPublic, parent, createdOn, updatedOn, identifier, customField, null)
  }
  val projectDetail25Reads: Reads[Project] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      (__ \ 'description).readNullable[String] ~
      (__ \ 'homepage).readNullable[String] ~
      (__ \ 'is_public).read[Boolean](false) ~
      (__ \ 'parent).readNullable[(Long, String)] ~
      (__ \ 'created_on).read[DateTime](timeReads) ~
      (__ \ 'updated_on).read[DateTime](timeReads) ~
      (__ \ 'identifier).read[String] ~
      (__ \ 'custom_fields).readNullable[Seq[CustomFieldValue]] ~
      (__ \ 'trackers).read[Seq[(Long, String)]] ~
      (__ \ 'issue_categories).read[Seq[(Long, String)]]
    ) { (id, name, description, homepage, isPublic, parent, createdOn, updatedOn, identifier, trackers, issueCategories, customField) =>
    ProjectDetail25(id, name, description, homepage, isPublic, parent, createdOn, updatedOn, identifier, trackers, issueCategories, customField, null)
  }
  val projectDetail26Reads: Reads[Project] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      (__ \ 'description).readNullable[String] ~
      (__ \ 'homepage).readNullable[String] ~
      (__ \ 'is_public).read[Boolean](false) ~
      (__ \ 'parent).readNullable[(Long, String)] ~
      (__ \ 'created_on).read[DateTime](timeReads) ~
      (__ \ 'updated_on).read[DateTime](timeReads) ~
      (__ \ 'identifier).read[String] ~
      (__ \ 'custom_fields).readNullable[Seq[CustomFieldValue]] ~
      (__ \ 'trackers).read[Seq[(Long, String)]] ~
      (__ \ 'issue_categories).read[Seq[(Long, String)]] ~
      (__ \ 'enabled_modules).read[Seq[String]]
    ) { (id, name, description, homepage, isPublic, parent, createdOn, updatedOn, identifier, customField, trackers, issueCategories, enabledModules) =>
    ProjectDetail26(id, name, description, homepage, isPublic, parent, createdOn, updatedOn, identifier, customField, trackers, issueCategories, enabledModules, null)
  }
  implicit val projectReads: Reads[Project] = {
    projectDetail26Reads orElse projectDetail25Reads orElse projectSummaryReads
  }
  implicit val projectCreateWrites = (
    (__ \ 'project \ 'name).write[String] ~
      (__ \ 'project \ 'identifier).write[String] ~
      (__ \ 'project \ 'description).writeNullable[String] ~
      (__ \ 'project \ 'homepage).writeNullable[String] ~
      (__ \ 'project \ 'is_public).writeNullable[Boolean] ~
      (__ \ 'project \ 'parent_id).writeNullable[Long] ~
      (__ \ 'project \ 'custom_fields).writeNullable[Seq[CustomFieldValue]] ~
      (__ \ 'project \ 'inherit_members).writeNullable[Boolean] ~
      (__ \ 'project \ 'trackers_ids).writeNullable[Seq[Long]] ~
      (__ \ 'project \ 'issue_categories).writeNullable[Seq[Long]] ~
      (__ \ 'project \ 'enabled_modules).writeNullable[Seq[String]]
    ).tupled
  implicit val projectUpdateWrites = (
    (__ \ 'project \ 'name).writeNullable[String] ~
      (__ \ 'project \ 'description).writeNullable[String] ~
      (__ \ 'project \ 'homepage).writeNullable[String] ~
      (__ \ 'project \ 'is_public).writeNullable[Boolean] ~
      (__ \ 'project \ 'parent_id).writeNullable[Long] ~
      (__ \ 'project \ 'custom_fields).writeNullable[Seq[CustomFieldValue]] ~
      (__ \ 'project \ 'inherit_members).writeNullable[Boolean] ~
      (__ \ 'project \ 'trackers_ids).writeNullable[Seq[Long]] ~
      (__ \ 'project \ 'issue_categories).writeNullable[Seq[Long]] ~
      (__ \ 'project \ 'enabled_modules).writeNullable[Seq[String]]
    ).tupled
}
