package redmine4s.api.resource

import org.joda.time.LocalDate
import play.api.libs.json._
import redmine4s.api.model._

/**
  * Issues
  * http://www.redmine.org/projects/redmine/wiki/Rest_Issues
  */
trait IssueResource extends BaseResource {
  private def applyRedmineToIssue: PartialFunction[Issue, Issue] = {
    case p: Issue =>
      p.copy(redmine = redmine, attachments = p.attachments.map(_.map(_.copy(redmine = redmine))))
  }

  /** Listing issues */
  def listIssues(params: Map[String, String] = Map.empty): Iterable[Issue] = {
    import redmine4s.api.json.JsonHelper.issueReads
    list("/issues.json", __ \ "issues", params).map(applyRedmineToIssue).toIterable
  }

  /** Showing an issue */
  def showIssue(issueId: Long): Issue = {
    import redmine4s.api.json.JsonHelper.issueReads
    val params = Map("include" -> "attachments,changesets,children,journals,relations,watchers")
    applyRedmineToIssue.apply(show(s"/issues/$issueId.json", __ \ "issue", params))
  }

  /** Creating an issue */
  def createIssue(subject: String,
                  projectId: Long,
                  trackerId: Option[Long] = None,
                  statusId: Option[Long] = None,
                  priorityId: Option[Long] = None,
                  description: Option[String] = None,
                  doneRatio: Option[Int] = None,
                  categoryId: Option[Long] = None,
                  startDate: Option[LocalDate] = None,
                  dueDate: Option[LocalDate] = None,
                  actualStartDate: Option[LocalDate] = None,
                  actualDueDate: Option[LocalDate] = None,
                  fixedVersionId: Option[Long] = None,
                  assignedToId: Option[Long] = None,
                  parentIssueId: Option[Long] = None,
                  customFields: Option[Seq[(Long, String)]] = None,
                  watcherUserIds: Option[Seq[Long]] = None,
                  isPrivate: Option[Boolean] = None,
                  estimatedHours: Option[Double] = None,
                  uploadFiles: Option[Seq[UploadFile]] = None): Issue = {
    import redmine4s.api.json.JsonHelper.{issueCreateWrites, issueReads}
    applyRedmineToIssue(create("/issues.json", __ \ 'issue, (subject, projectId, trackerId, statusId, priorityId, description, doneRatio, categoryId, startDate, dueDate, actualStartDate, actualDueDate, fixedVersionId, assignedToId, parentIssueId, customFields, watcherUserIds, isPrivate, estimatedHours, uploadFiles.map(_.map(redmine.upload)))))
  }

  /** Updating an issue */
  def updateIssue(id: Long,
                  subject: Option[String] = None,
                  projectId: Option[Long] = None,
                  trackerId: Option[Long] = None,
                  statusId: Option[Long] = None,
                  priorityId: Option[Long] = None,
                  description: Option[String] = None,
                  doneRatio: Option[Int] = None,
                  categoryId: Option[Long] = None,
                  startDate: Option[LocalDate] = None,
                  dueDate: Option[LocalDate] = None,
                  actualStartDate: Option[LocalDate] = None,
                  actualDueDate: Option[LocalDate] = None,
                  fixedVersionId: Option[Long] = None,
                  assignedToId: Option[Long] = None,
                  parentIssueId: Option[Long] = None,
                  customFields: Option[Seq[(Long, String)]] = None,
                  watcherUserIds: Option[Seq[Long]] = None,
                  isPrivate: Option[Boolean] = None,
                  estimatedHours: Option[Double] = None,
                  uploadFiles: Option[Seq[UploadFile]] = None): Issue = {
    import redmine4s.api.json.JsonHelper.issueUpdateWrites
    update(s"/issues/$id.json", (subject, projectId, trackerId, statusId, priorityId, description, doneRatio, categoryId, startDate, dueDate, actualStartDate, actualDueDate, fixedVersionId, assignedToId, parentIssueId, customFields, watcherUserIds, isPrivate, estimatedHours, uploadFiles.map(_.map(redmine.upload))))
    showIssue(id)
  }

  /** Deleting an issue */
  def deleteIssue(id: Long): Unit = delete(s"/issues/$id.json")

  /** Adding a watcher */
  def addWatcher(issueId: Long, userId: Long): Unit = {
    create[Long, Long](s"/issues/$issueId/watchers.json", __ \ 'user_id, userId)
  }

  /** Removing a watcher */
  def deleteWatcher(issueId: Long, userId: Long): Unit = delete(s"/issues/$issueId/watchers/$userId.json")
}
