package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.Role

trait RoleResource extends BaseResource {
  def listRoles(): Iterable[Role] = {
    import redmine4s.api.json.JsonHelper.roleReads
    list("/roles.json", __ \ 'roles, Map.empty).toIterable
  }
}
