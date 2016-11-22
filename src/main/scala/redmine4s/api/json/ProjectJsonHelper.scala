package redmine4s.api.json

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads.pure
import play.api.libs.json._
import redmine4s.api.model._

trait ProjectJsonHelper extends RoleJsonHelper with CustomFieldJsonHelper with VersionJsonHelper with ProjectMembershipJsonHelper with IssueCategoryJsonHelper {
  implicit val projectStatusReads: Reads[ProjectStatus] = __.read[Int].map(ProjectStatus.fromInt)
  implicit val projectReads: Reads[Project] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      (__ \ 'description).readNullable[String] ~
      (__ \ 'homepage).readNullable[String] ~
      ((__ \ 'is_public).read[Boolean] or pure(false)) ~
      (__ \ 'status).readNullable[ProjectStatus] ~
      (__ \ 'parent).readNullable[(Long, String)] ~
      (__ \ 'created_on).read[DateTime](timeReads) ~
      (__ \ 'updated_on).read[DateTime](timeReads) ~
      (__ \ 'identifier).read[String] ~
      ((__ \ 'custom_fields).read[Seq[CustomFieldValue]] or pure(Seq.empty[CustomFieldValue])) ~
      (__ \ 'trackers).readNullable[Seq[(Long, String)]] ~
      (__ \ 'issue_categories).readNullable[Seq[(Long, String)]] ~
      (__ \ 'enabled_modules).readNullable[Seq[String]]
    ) { (id, name, description, homepage, isPublic, status, parent, createdOn, updatedOn, identifier, customField, trackers, issueCategories, enabledModules) =>
    Project(id, name, description, homepage, isPublic, status, parent, createdOn, updatedOn, identifier, customField, trackers, issueCategories, enabledModules, null)
  }
  implicit val projectCreateWrites = (
    (__ \ 'project \ 'name).write[String] ~
      (__ \ 'project \ 'identifier).write[String] ~
      (__ \ 'project \ 'description).writeNullable[String] ~
      (__ \ 'project \ 'homepage).writeNullable[String] ~
      (__ \ 'project \ 'is_public).writeNullable[Boolean] ~
      (__ \ 'project \ 'parent_id).writeNullable[Long] ~
      (__ \ 'project \ 'custom_fields).writeNullable[Seq[(Long, String)]] ~
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
      (__ \ 'project \ 'custom_fields).writeNullable[Seq[(Long, String)]] ~
      (__ \ 'project \ 'inherit_members).writeNullable[Boolean] ~
      (__ \ 'project \ 'trackers_ids).writeNullable[Seq[Long]] ~
      (__ \ 'project \ 'issue_categories).writeNullable[Seq[Long]] ~
      (__ \ 'project \ 'enabled_modules).writeNullable[Seq[String]]
    ).tupled
}
