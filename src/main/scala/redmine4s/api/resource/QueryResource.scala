package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.Query

trait QueryResource extends BaseResource {
  def listQueries(): Iterable[Query] = {
    import redmine4s.api.json.JsonHelper.queryReads
    list("/queries.json", __ \ 'queries, Map.empty).toIterable
  }
}
