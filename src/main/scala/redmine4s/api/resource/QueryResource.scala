package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.Query

/**
  * Queries
  * http://www.redmine.org/projects/redmine/wiki/Rest_Queries
  */
trait QueryResource extends BaseResource {
  /** Returns the list of all custom queries visible by the user (public and private queries) for all projects. */
  def listQueries(): Iterable[Query] = {
    import redmine4s.api.json.JsonHelper.queryReads
    list("/queries.json", __ \ 'queries, Map.empty).toIterable
  }
}
