package redmine4s.api.resource

import org.joda.time.LocalDate
import play.api.libs.json._
import redmine4s.api.model.{Sharing, Status, Version}

/**
  * Versions
  * http://www.redmine.org/projects/redmine/wiki/Rest_Versions
  */
trait VersionResource extends BaseResource {
  /** Returns the versions available for the project of given id or identifier (:project_id). The response may include shared versions from other projects. */
  def listVersions(projectId: Long): Iterable[Version] = listVersions(projectId.toString)

  /** Returns the versions available for the project of given id or identifier (:project_id). The response may include shared versions from other projects. */
  def listVersions(projectId: String): Iterable[Version] = {
    import redmine4s.api.json.JsonHelper.versionReads
    list(s"/projects/$projectId/versions.json", __ \ "versions", Map.empty).map(_.copy(redmine = redmine)).toIterable
  }

  /** Creates a version for the project of given id or identifier. */
  def createVersion(projectId: String,
                    name: String,
                    status: Option[Status] = None,
                    sharing: Option[Sharing] = None,
                    dueDate: Option[LocalDate] = None,
                    description: Option[String] = None,
                    customFields: Option[Seq[(Long, String)]] = None): Version = {
    import redmine4s.api.json.JsonHelper.{versionCreateWrites, versionReads}
    create(s"/projects/$projectId/versions.json", __ \ 'version, (name, status, sharing, dueDate, description, customFields)).copy(redmine = redmine)
  }

  /** Returns the version of given id. */
  def showVersion(versionId: Long): Version = {
    import redmine4s.api.json.JsonHelper.versionReads
    show(s"/versions/$versionId.json", __ \ "version", Map.empty).copy(redmine = redmine)
  }

  /** Updates the version of given id. */
  def updateVersion(versionId: Long,
                    name: Option[String] = None,
                    status: Option[Status] = None,
                    sharing: Option[Sharing] = None,
                    dueDate: Option[LocalDate] = None,
                    description: Option[String] = None,
                    customFields: Option[Seq[(Long, String)]] = None): Version = {
    import redmine4s.api.json.JsonHelper.versionUpdateWrites
    update(s"/versions/$versionId.json", (name, status, sharing, dueDate, description, customFields))
    showVersion(versionId)
  }

  /** Deletes the version of given id. */
  def deleteVersion(versionId: Long): Unit = delete(s"/versions/$versionId.json")
}
