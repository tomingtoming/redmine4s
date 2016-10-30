package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.ProjectMembership

/**
  * Project Memberships
  * http://www.redmine.org/projects/redmine/wiki/Rest_Memberships
  */
trait ProjectMembershipResource extends BaseResource {
  private def applyRedmineToProjectMembership: PartialFunction[ProjectMembership, ProjectMembership] = {
    case p: ProjectMembership => p.copy(redmine = redmine, roles = p.roles.map(_.copy(redmine = redmine)))
  }

  /** Returns a paginated list of the project memberships. */
  def listProjectMemberships(projectId: Long): Iterable[ProjectMembership] = {
    import redmine4s.api.json.JsonHelper.projectMembershipReads
    list(s"/projects/$projectId/memberships.json", __ \ 'memberships, Map.empty).map(applyRedmineToProjectMembership).toIterable
  }

  /** Adds a project member. */
  def addProjectMembership(projectId: Long, userId: Long, roleIds: Seq[Long]): Unit = {
    import redmine4s.api.json.JsonHelper.projectMembershipCreateWrites
    add[(Long, Seq[Long])](s"/projects/$projectId/memberships.json", __ \ 'membership, (userId, roleIds))
  }

  /** Returns the membership of given id. */
  def showProjectMembership(projectMembershipId: Long): ProjectMembership = {
    import redmine4s.api.json.JsonHelper.projectMembershipReads
    applyRedmineToProjectMembership(show(s"/memberships/$projectMembershipId.xml", __ \ 'membership, Map.empty))
  }

  /** Updates the membership of given id. Only the roles can be updated, the project and the user of a membership are read-only. */
  def updateProjectMembership(projectMembershipId: Long, roleIds: Seq[Long]): Unit = {
    update[Seq[Long]](s"/memberships/$projectMembershipId.xml", roleIds)
  }

  /** Deletes a memberships. */
  def deleteProjectMembership(projectMembershipId: Long): Unit = delete(s"/memberships/$projectMembershipId.xml")
}
