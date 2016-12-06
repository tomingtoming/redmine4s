package redmine4s.api.model

import org.joda.time.{DateTime, LocalDate}
import play.api.libs.json.JsValue
import redmine4s.Redmine

case class Issue(id: Long,
                 project: (Long, String),
                 tracker: (Long, String),
                 status: (Long, String),
                 priority: (Long, String),
                 author: (Long, String),
                 subject: String,
                 doneRatio: Int,
                 createdOn: DateTime,
                 updatedOn: DateTime,
                 parent: Option[Long],
                 description: Option[String],
                 fixedVersion: Option[(Long, String)],
                 assignedTo: Option[(Long, String)],
                 category: Option[(Long, String)],
                 startDate: Option[LocalDate],
                 dueDate: Option[LocalDate],
                 actualStartDate: Option[LocalDate],
                 actualDueDate: Option[LocalDate],
                 estimatedHours: Option[Double],
                 closedOn: Option[DateTime],
                 customField: Seq[CustomFieldValue],
                 watchers: Option[Seq[(Long, String)]],
                 attachments: Option[Seq[Attachment]],
                 changeSets: Option[Seq[ChangeSet]],
                 journals: Option[Seq[Journal]],
                 children: Option[Seq[ChildIssue]],
                 relations: Option[Seq[IssueRelation]],
                 jsValue: JsValue,
                 redmine: Redmine) extends RedmineModelBase[Issue] {

  override def setRedmine(redmine: Redmine): Issue = this.copy(redmine = redmine)

  override def setJsValue(jsValue: JsValue): Issue = this.copy(jsValue = jsValue)

  def show: Issue = redmine.showIssue(this.id)

  def update(subject: Option[String] = None,
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
    redmine.updateIssue(id, subject, projectId, trackerId, statusId, priorityId, description, doneRatio, categoryId, startDate, dueDate, actualStartDate, actualDueDate, fixedVersionId, assignedToId, parentIssueId, customFields, watcherUserIds, isPrivate, estimatedHours, uploadFiles)
  }

  def delete(): Unit = redmine.deleteIssue(this.id)
}

case class ChildIssue(id: Long, tracker: (Long, String), subject: String, children: Seq[ChildIssue])
