package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.ProjectMembership

trait ProjectMembershipResource extends BaseResource {
  def listProjectMemberships(projectId: Long): Iterable[ProjectMembership] = {
    import redmine4s.api.json.ProjectJsonHelper.projectMembershipReads
    list(s"/projects/$projectId/memberships.json", __ \ 'memberships, Map.empty).toIterable
  }
}
