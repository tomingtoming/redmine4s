package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.Version

trait VersionResource extends BaseResource {
  def listVersions(projectId: Long): Iterable[Version] = {
    import redmine4s.api.json.ProjectJsonHelper.versionReads
    list(s"/projects/$projectId/versions.json", __ \ "versions", Map.empty).toIterable
  }

  def showVersion(versionId: Long): Version = {
    import redmine4s.api.json.ProjectJsonHelper.versionReads
    show(s"/versions/$versionId.json", __ \ "version", Map.empty)
  }
}
