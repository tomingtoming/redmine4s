package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.Group

trait GroupResource extends BaseResource {
  def listGroups(): Iterable[Group] = {
    import redmine4s.api.json.JsonHelper.groupReads
    list("/groups.json", __ \ 'groups, Map.empty).toIterable
  }
}
