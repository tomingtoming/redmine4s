package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.User

trait UserResource extends BaseResource {
  def listUsers(): Iterable[User] = {
    import redmine4s.api.json.JsonHelper.userReads
    list("/users.json", __ \ 'users, Map.empty).toIterable
  }
}
