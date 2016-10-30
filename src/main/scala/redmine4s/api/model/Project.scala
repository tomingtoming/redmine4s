package redmine4s.api.model

import org.joda.time.{DateTime, LocalDate}
import org.slf4j.{Logger, LoggerFactory}
import redmine4s.Redmine

sealed trait Project {
  protected val logger: Logger
  protected val redmine: Redmine

  val id: Long
  val name: String
  val description: Option[String]
  val homepage: Option[String]
  val isPublic: Boolean
  val parent: Option[(Long, String)]
  val createdOn: DateTime
  val updatedOn: DateTime
  val identifier: String
  val customField: Option[Seq[CustomFieldValue]]

  def show: Project = redmine.showProject(this.id)

  def update(name: Option[String] = None,
             description: Option[String] = None,
             homepage: Option[String] = None,
             isPublic: Option[Boolean] = None,
             parent: Option[Long] = None,
             customField: Option[Seq[CustomFieldValue]] = None,
             inheritMembers: Option[Boolean] = None,
             trackers: Option[Seq[Long]] = None,
             issueCategories: Option[Seq[Long]] = None,
             enabledModules: Option[Seq[String]] = None): Project = {
    redmine.updateProject(Right(identifier), name, description, homepage, isPublic, parent, customField, inheritMembers, trackers, issueCategories, enabledModules)
  }

  def delete(): Unit = redmine.deleteProject(identifier)

  /** Creating an issue */
  def createIssue(subject: String,
                  trackerId: Option[Long] = None,
                  statusId: Option[Long] = None,
                  priorityId: Option[Long] = None,
                  description: Option[String] = None,
                  doneRatio: Option[Int] = None,
                  categoryId: Option[Long] = None,
                  startDate: Option[LocalDate] = None,
                  dueDate: Option[LocalDate] = None,
                  actualStartDate: Option[LocalDate] = None,
                  actualDueDate: Option[LocalDate] = None,
                  fixedVersionId: Option[Long] = None,
                  assignedToId: Option[Long] = None,
                  parentIssueId: Option[Long] = None,
                  customFields: Option[Seq[(Long, String)]] = None,
                  watcherUserIds: Option[Seq[Long]] = None,
                  isPrivate: Option[Boolean] = None,
                  estimatedHours: Option[Double] = None,
                  uploadFiles: Option[Seq[UploadFile]] = None): Issue = {
    redmine.createIssue(subject, this.id, trackerId, statusId, priorityId, description, doneRatio, categoryId, startDate, dueDate, actualStartDate, actualDueDate, fixedVersionId, assignedToId, parentIssueId, customFields, watcherUserIds, isPrivate, estimatedHours, uploadFiles)
  }

  def listProjectMemberships: Iterable[ProjectMembership] = redmine.listProjectMemberships(this.id)

  def listVersions: Iterable[Version] = redmine.listVersions(this.id)

  def listIssueCategories: Iterable[IssueCategory] = redmine.listIssueCategories(this.id)

  def mainWikiTitle(): Option[String] = redmine.mainWikiTitle(this.id)

  def listWikis(): Iterable[Wiki] = redmine.listWikis(this.id)

  def listNews(): Iterable[News] = redmine.listNews(this.id)
}

trait ProjectDetail extends Project {
  val trackers: Seq[(Long, String)]
  val issueCategories: Seq[(Long, String)]
}

case class ProjectSummary(id: Long,
                          name: String,
                          description: Option[String],
                          homepage: Option[String],
                          isPublic: Boolean,
                          parent: Option[(Long, String)],
                          createdOn: DateTime,
                          updatedOn: DateTime,
                          identifier: String,
                          customField: Option[Seq[CustomFieldValue]],
                          redmine: Redmine) extends Project {
  protected val logger = LoggerFactory.getLogger(this.getClass)
}

case class ProjectDetail25(id: Long,
                           name: String,
                           description: Option[String],
                           homepage: Option[String],
                           isPublic: Boolean,
                           parent: Option[(Long, String)],
                           createdOn: DateTime,
                           updatedOn: DateTime,
                           identifier: String,
                           customField: Option[Seq[CustomFieldValue]],
                           trackers: Seq[(Long, String)],
                           issueCategories: Seq[(Long, String)],
                           redmine: Redmine) extends ProjectDetail {
  protected val logger = LoggerFactory.getLogger(this.getClass)
}

case class ProjectDetail26(id: Long,
                           name: String,
                           description: Option[String],
                           homepage: Option[String],
                           isPublic: Boolean,
                           parent: Option[(Long, String)],
                           createdOn: DateTime,
                           updatedOn: DateTime,
                           identifier: String,
                           customField: Option[Seq[CustomFieldValue]],
                           trackers: Seq[(Long, String)],
                           issueCategories: Seq[(Long, String)],
                           enabledModules: Seq[String],
                           redmine: Redmine) extends ProjectDetail {
  protected val logger = LoggerFactory.getLogger(this.getClass)
}
