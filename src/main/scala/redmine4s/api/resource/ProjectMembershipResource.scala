package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.ProjectMembership

/**
  * Project Memberships
  * http://www.redmine.org/projects/redmine/wiki/Rest_Memberships
  */
trait ProjectMembershipResource extends BaseResource {
  def listProjectMemberships(projectId: Long): Iterable[ProjectMembership] = {
    import redmine4s.api.json.JsonHelper.projectMembershipReads
    list(s"/projects/$projectId/memberships.json", __ \ 'memberships, Map.empty).map(_.copy(redmine = redmine)).toIterable
  }
}
