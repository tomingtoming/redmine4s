package redmine4s.api.json

import org.joda.time.{DateTime, LocalDate}
import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model._

object ProjectJsonHelper extends BaseJsonHelper {
  implicit val roleReads = RoleJsonHelper.roleReads
  implicit val projectMembershipReads: Reads[ProjectMembership] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'project).read[(Long, String)] ~
      (__ \ 'user).readNullable[(Long, String)] ~
      (__ \ 'group).readNullable[(Long, String)] ~
      (__ \ 'roles).read[Iterable[Role]]
    ) (ProjectMembership.apply _)
  implicit val versionReads: Reads[Version] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'project).read[(Long, String)] ~
      (__ \ 'name).read[String] ~
      (__ \ 'description).read[String] ~
      (__ \ 'due_date).readNullable[LocalDate](dateReads) ~
      (__ \ 'status).read[String] ~
      (__ \ 'sharing).read[String] ~
      (__ \ 'created_on).read[DateTime](timeReads) ~
      (__ \ 'updated_on).read[DateTime](timeReads)
    ) (Version.apply _)
  implicit val issueCategoryReads: Reads[IssueCategory] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'project).read[(Long, String)] ~
      (__ \ 'name).read[String] ~
      (__ \ 'assigned_to).readNullable[(Long, String)]
    ) (IssueCategory.apply _)
  implicit val wikiReads: Reads[Wiki] = (
    (__ \ 'title).read[String] ~
      ((__ \ 'parent \ 'title).readNullable[String] orElse (__ \ 'parent).readNullable[String]) ~
      (__ \ 'version).read[Int] ~
      (__ \ 'created_on).read[DateTime](timeReads) ~
      (__ \ 'updated_on).read[DateTime](timeReads) ~
      (__ \ 'text).readNullable[String] ~
      (__ \ 'author).readNullable[(Long, String)] ~
      (__ \ 'comments).readNullable[String]
    ) { (title, parent, version, createdOn, updatedOn, text, author, comments) =>
    Wiki(title, parent, version, createdOn, updatedOn, text, author, comments, 0L, null)
  }
  val projectSummaryReads: Reads[Project] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      (__ \ 'description).readNullable[String] ~
      (__ \ 'homepage).readNullable[String] ~
      (__ \ 'is_public).read[Boolean](false) ~
      (__ \ 'parent).readNullable[(Long, String)] ~
      (__ \ 'created_on).read[DateTime](timeReads) ~
      (__ \ 'updated_on).read[DateTime](timeReads) ~
      (__ \ 'identifier).read[String]
    ) { (id, name, description, homepage, isPublic, parent, createdOn, updatedOn, identifier) =>
    ProjectSummary(id, name, description, homepage, isPublic, parent, createdOn, updatedOn, identifier, null)
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
      (__ \ 'trackers).read[Seq[(Long, String)]] ~
      (__ \ 'issue_categories).read[Seq[(Long, String)]]
    ) { (id, name, description, homepage, isPublic, parent, createdOn, updatedOn, identifier, trackers, issueCategories) =>
    ProjectDetail25(id, name, description, homepage, isPublic, parent, createdOn, updatedOn, identifier, trackers, issueCategories, null)
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
      (__ \ 'trackers).read[Seq[(Long, String)]] ~
      (__ \ 'issue_categories).read[Seq[(Long, String)]] ~
      (__ \ 'enabled_modules).read[Seq[String]]
    ) { (id, name, description, homepage, isPublic, parent, createdOn, updatedOn, identifier, trackers, issueCategories, enabledModules) =>
    ProjectDetail26(id, name, description, homepage, isPublic, parent, createdOn, updatedOn, identifier, trackers, issueCategories, enabledModules, null)
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
      (__ \ 'project \ 'inherit_members).writeNullable[Boolean] ~
      (__ \ 'project \ 'trackers_ids).writeNullable[Seq[Long]] ~
      (__ \ 'project \ 'issue_categories).writeNullable[Seq[Long]] ~
      (__ \ 'project \ 'enabled_modules).writeNullable[Seq[String]]
    ).tupled
}
