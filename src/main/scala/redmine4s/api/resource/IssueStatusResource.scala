package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.IssueStatus

/**
  * Issue Statuses
  * http://www.redmine.org/projects/redmine/wiki/Rest_IssueStatuses
  */
trait IssueStatusResource extends BaseResource {
  /** Returns the list of all issue statuses. */
  def listIssueStatus(): Iterable[IssueStatus] = {
    import redmine4s.api.json.JsonHelper.issueStatusReads
    list("/issue_statuses.json", __ \ 'issue_statuses, Map.empty).toIterable
  }
}
