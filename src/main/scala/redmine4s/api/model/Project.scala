package redmine4s.api.model

import org.joda.time.DateTime
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
  val status: ProjectStatus
  val parent: Option[(Long, String)]
  val createdOn: DateTime
  val updatedOn: DateTime
  val identifier: String
  val customField: Option[Seq[CustomFieldValue]]

  /** Returns the project of given id or identifier. */
  def show(): Project = redmine.showProject(this.id)

  /** Updates the project of given id or identifier. */
  def update(name: Option[String] = None,
             description: Option[String] = None,
             homepage: Option[String] = None,
             isPublic: Option[Boolean] = None,
             parent: Option[Long] = None,
             customField: Option[Seq[(Long, String)]] = None,
             inheritMembers: Option[Boolean] = None,
             trackers: Option[Seq[Long]] = None,
             issueCategories: Option[Seq[Long]] = None,
             enabledModules: Option[Seq[String]] = None): Project = {
    redmine.updateProject(Right(identifier), name, description, homepage, isPublic, parent, customField, inheritMembers, trackers, issueCategories, enabledModules)
  }

  /** Deletes the project of given id or identifier. */
  def delete(): Unit = redmine.deleteProject(identifier)

  def listProjectMemberships: Iterable[ProjectMembership] = redmine.listProjectMemberships(this.id)

  def listVersions: Iterable[Version] = redmine.listVersions(this.id)

  def listIssueCategories: Iterable[IssueCategory] = redmine.listIssueCategories(this.id)

  def mainWikiTitle(): Option[String] = redmine.mainWikiTitle(this.id)

  def indexWikis(): Iterable[WikiIndex] = redmine.indexWikis(this.id)

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
                          status: ProjectStatus,
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
                           status: ProjectStatus,
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
                           status: ProjectStatus,
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

object ProjectStatus {

  object Open extends ProjectStatus(1) {
    override val isOpen: Boolean = true
    override val isClosed: Boolean = false
  }

  object Closed extends ProjectStatus(5) {
    override val isOpen: Boolean = false
    override val isClosed: Boolean = true
  }

  object Archived extends ProjectStatus(9) {
    override val isOpen: Boolean = false
    override val isClosed: Boolean = false
  }

  def fromInt(intExpr: Int): ProjectStatus = intExpr match {
    case 1 => Open
    case 5 => Closed
    case 9 => Archived
    case n => throw new FormatConversionException(s"Undefined number for ProjectStatus: $n")
  }
}

sealed abstract class ProjectStatus(val intExpr: Int) {
  val isOpen: Boolean
  val isClosed: Boolean
}
