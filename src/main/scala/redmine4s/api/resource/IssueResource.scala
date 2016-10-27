package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model._

trait IssueResource extends BaseResource {
  private def applyRedmineToIssue: PartialFunction[Issue, Issue] = {
    case p: Issue =>
      val attachments = p.attachments.map(_.map(_.copy(redmine = redmine)))
      p.copy(redmine = redmine, optionalFields = p.optionalFields.copy(attachments = attachments))
  }

  def listIssues(params: Map[String, String] = Map.empty): Iterable[Issue] = {
    import redmine4s.api.json.JsonHelper.issueReads
    list("/issues.json", __ \ "issues", params).map(applyRedmineToIssue).toIterable
  }

  def showIssue(issueId: Long): Issue = {
    import redmine4s.api.json.JsonHelper.issueReads
    val params = Map("include" -> "attachments,changesets,children,journals,relations,watchers")
    applyRedmineToIssue.apply(show(s"/issues/$issueId.json", __ \ "issue", params))
  }

  def createIssue(subject: String,
                  projectId: Long,
                  trackerId: Option[Long] = None,
                  statusId: Option[Long] = None,
                  priorityId: Option[Long] = None,
                  description: Option[String] = None,
                  categoryId: Option[Long] = None,
                  fixedVersionId: Option[Long] = None,
                  assignedToId: Option[Long] = None,
                  parentIssueId: Option[Long] = None,
                  customFields: Option[Seq[(Long, String)]] = None,
                  watcherUserIds: Option[Seq[Long]] = None,
                  isPrivate: Option[Boolean] = None,
                  estimatedHours: Option[Double] = None,
                  uploadFiles: Option[Seq[UploadFile]] = None): Issue = {
    import redmine4s.api.json.JsonHelper.{issueReads, issueCreateWrites}
    applyRedmineToIssue(create("/issues.json", __ \ 'issue, (subject, projectId, trackerId, statusId, priorityId, description, categoryId, fixedVersionId, assignedToId, parentIssueId, customFields, watcherUserIds, isPrivate, estimatedHours, uploadFiles.map(_.map(redmine.upload)))))
  }

  def updateIssue(id: Long,
                  subject: Option[String] = None,
                  projectId: Option[Long] = None,
                  trackerId: Option[Long] = None,
                  statusId: Option[Long] = None,
                  priorityId: Option[Long] = None,
                  description: Option[String] = None,
                  categoryId: Option[Long] = None,
                  fixedVersionId: Option[Long] = None,
                  assignedToId: Option[Long] = None,
                  parentIssueId: Option[Long] = None,
                  customFields: Option[Seq[(Long, String)]] = None,
                  watcherUserIds: Option[Seq[Long]] = None,
                  isPrivate: Option[Boolean] = None,
                  estimatedHours: Option[Double] = None,
                  uploadFiles: Option[Seq[UploadFile]] = None): Issue = {
    import redmine4s.api.json.JsonHelper.issueUpdateWrites
    update(s"/issues/$id.json", (subject, projectId, trackerId, statusId, priorityId, description, categoryId, fixedVersionId, assignedToId, parentIssueId, customFields, watcherUserIds, isPrivate, estimatedHours, uploadFiles.map(_.map(redmine.upload))))
    showIssue(id)
  }

  def deleteIssue(id: Long): Unit = {
    delete(s"/issues/$id.json")
  }
}
