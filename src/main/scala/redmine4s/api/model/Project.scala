package redmine4s.api.model

import org.joda.time.DateTime
import org.slf4j.LoggerFactory
import play.api.libs.json.JsValue
import redmine4s.Redmine

case class Project(id: Long,
                   name: String,
                   description: Option[String],
                   homepage: Option[String],
                   isPublic: Boolean,
                   status: Option[ProjectStatus],
                   parent: Option[(Long, String)],
                   createdOn: DateTime,
                   updatedOn: DateTime,
                   identifier: String,
                   customField: Seq[CustomFieldValue],
                   trackers: Option[Seq[(Long, String)]],
                   issueCategories: Option[Seq[(Long, String)]],
                   enabledModules: Option[Seq[String]],
                   jsValue: JsValue,
                   redmine: Redmine) extends RedmineModelBase[Project] {

  override def setRedmine(redmine: Redmine): Project = this.copy(redmine = redmine)

  override def setJsValue(jsValue: JsValue): Project = this.copy(jsValue = jsValue)

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

  def showMainWiki(): Wiki = redmine.showMainWiki(this.id)

  def indexWikis(): Iterable[WikiIndex] = redmine.indexWikis(this.id)

  def listNews(): Iterable[News] = redmine.listNews(this.id)
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

  def fromInt(expr: Int): ProjectStatus = expr match {
    case 1 => Open
    case 5 => Closed
    case 9 => Archived
    case n => throw new FormatConversionException(s"Undefined number for ProjectStatus: $n")
  }
}

sealed abstract class ProjectStatus(val expr: Int) {
  val isOpen: Boolean
  val isClosed: Boolean
}
