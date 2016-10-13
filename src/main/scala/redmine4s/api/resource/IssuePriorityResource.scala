package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.IssuePriority

trait IssuePriorityResource extends BaseResource {
  def listIssuePriorities(): Iterable[IssuePriority] = {
    import redmine4s.api.json.EnumerationsJsonHelper.issuePriorityReads
    val url = s"/enumerations/issue_priorities.json"
    list(url, __ \ "issue_priorities", Map.empty).toIterable
  }
}
