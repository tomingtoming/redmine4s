package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.IssueCategory

/**
  * Issue Categories
  * http://www.redmine.org/projects/redmine/wiki/Rest_IssueCategories
  */
trait IssueCategoryResource extends BaseResource {
  /** Returns the issue categories available for the project of given id or identifier. */
  def listIssueCategories(projectId: Long): Iterable[IssueCategory] = listIssueCategories(projectId.toString)

  /** Returns the issue categories available for the project of given id or identifier. */
  def listIssueCategories(projectId: String): Iterable[IssueCategory] = {
    import redmine4s.api.json.JsonHelper.issueCategoryReads
    list(s"/projects/$projectId/issue_categories.json", __ \ 'issue_categories, Map.empty).toIterable
  }

  /** Returns the issue category of given id. */
  def showIssueCategory(issueCategoryId: Long): IssueCategory = {
    import redmine4s.api.json.JsonHelper.issueCategoryReads
    show(s"/issue_categories/$issueCategoryId.json", __ \ 'issue_category, Map.empty)
  }

  /** Creates an issue category for the project of given id or identifier. */
  def createIssueCategory(projectId: Long, name: String, assignedToId: Option[Long] = None): IssueCategory = {
    import redmine4s.api.json.JsonHelper.{issueCategoryCreateWrites, issueCategoryReads}
    create(s"/projects/$projectId/issue_categories.json", __ \ 'issue_category, (name, assignedToId))
  }

  /** Updates the issue category of given id. */
  def updateIssueCategory(issueCategoryId: Long, name: Option[String] = None, assignedToId: Option[Long] = None): IssueCategory = {
    import redmine4s.api.json.JsonHelper.issueCategoryUpdateWrites
    update(s"/issue_categories/$issueCategoryId.json", (name, assignedToId))
    showIssueCategory(issueCategoryId)
  }

  /** Deletes the issue category of given id. */
  def deleteIssueCategory(issueCategoryId: Long): Unit = delete(s"/issue_categories/$issueCategoryId.json")
}
