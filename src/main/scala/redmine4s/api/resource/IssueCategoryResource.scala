package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.IssueCategory

trait IssueCategoryResource extends BaseResource {
  def listIssueCategories(projectId: Long): Iterable[IssueCategory] = {
    import redmine4s.api.json.ProjectJsonHelper.issueCategoryReads
    list(s"/projects/$projectId/issue_categories.json", __ \ 'issue_categories, Map.empty).toIterable
  }
}
