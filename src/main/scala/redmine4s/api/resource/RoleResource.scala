package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.Role

/**
  * Roles
  * http://www.redmine.org/projects/redmine/wiki/Rest_Roles
  */
trait RoleResource extends BaseResource {
  /** Returns the list of roles. */
  def listRoles(): Iterable[Role] = {
    import redmine4s.api.json.JsonHelper.roleReads
    list("/roles.json", __ \ 'roles, Map.empty).map(_.copy(redmine = redmine)).toIterable
  }

  /** Returns the list of permissions for a given role. */
  def showRole(roleId: Long): Role = {
    import redmine4s.api.json.JsonHelper.roleReads
    show(s"/roles/$roleId.json", __ \ 'role, Map.empty).copy(redmine = redmine)
  }
}
