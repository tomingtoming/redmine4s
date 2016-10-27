package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.IssueStatus

trait IssueStatusResource extends BaseResource {
  def listIssueStatus(): Iterable[IssueStatus] = {
    import redmine4s.api.json.JsonHelper.issueStatusReads
    list("/issue_statuses.json", __ \ 'issue_statuses, Map.empty).toIterable
  }
}
