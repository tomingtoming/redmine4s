package redmine4s.api.model

import org.joda.time.{DateTime, LocalDate}
import redmine4s.Redmine

case class Issue(requiredFields: IssueRequiredFields, optionalFields: IssueOptionalFields, redmine: Redmine) {
  val id: Long = requiredFields.id
  val project: (Long, String) = requiredFields.project
  val tracker: (Long, String) = requiredFields.tracker
  val status: (Long, String) = requiredFields.status
  val priority: (Long, String) = requiredFields.priority
  val author: (Long, String) = requiredFields.author
  val subject: String = requiredFields.subject
  val doneRatio: Int = requiredFields.doneRatio
  val createdOn: DateTime = requiredFields.createdOn
  val updatedOn: DateTime = requiredFields.updatedOn
  val parent: Option[Long] = optionalFields.parent
  val description: Option[String] = optionalFields.description
  val fixedVersion: Option[(Long, String)] = optionalFields.fixedVersion
  val assignedTo: Option[(Long, String)] = optionalFields.assignedTo
  val category: Option[(Long, String)] = optionalFields.category
  val startDate: Option[LocalDate] = optionalFields.startDate
  val dueDate: Option[LocalDate] = optionalFields.dueDate
  val actualStartDate: Option[LocalDate] = optionalFields.actualStartDate
  val actualDueDate: Option[LocalDate] = optionalFields.actualDueDate
  val estimatedHours: Option[Double] = optionalFields.estimatedHours
  val closedOn: Option[DateTime] = optionalFields.closedOn
  val customField: Option[Seq[CustomFieldValue]] = optionalFields.customField
  val watchers: Option[Seq[(Long, String)]] = optionalFields.watchers
  val attachments: Option[Seq[Attachment]] = optionalFields.attachments
  val changeSets: Option[Seq[ChangeSet]] = optionalFields.changeSets
  val journals: Option[Seq[Journal]] = optionalFields.journals
  val children: Option[Seq[IssueChild]] = optionalFields.children
  val relations: Option[Seq[IssueRelation]] = optionalFields.relations
  //val isPrivate TODO: is_private is unavailable

  def show: Issue = redmine.showIssue(this.id)

  def update(subject: Option[String] = None,
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
    redmine.updateIssue(id, subject, projectId, trackerId, statusId, priorityId, description, categoryId, fixedVersionId, assignedToId, parentIssueId, customFields, watcherUserIds, isPrivate, estimatedHours, uploadFiles)
  }

  def delete(): Unit = redmine.deleteIssue(this.id)
}

case class IssueRequiredFields(id: Long,
                               project: (Long, String),
                               tracker: (Long, String),
                               status: (Long, String),
                               priority: (Long, String),
                               author: (Long, String),
                               subject: String,
                               doneRatio: Int,
                               createdOn: DateTime,
                               updatedOn: DateTime)

case class IssueOptionalFields(parent: Option[Long],
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
                               customField: Option[Seq[CustomFieldValue]],
                               watchers: Option[Seq[(Long, String)]],
                               attachments: Option[Seq[Attachment]],
                               changeSets: Option[Seq[ChangeSet]],
                               journals: Option[Seq[Journal]],
                               children: Option[Seq[IssueChild]],
                               relations: Option[Seq[IssueRelation]])

case class IssueChild(id: Long, tracker: (Long, String), subject: String)
