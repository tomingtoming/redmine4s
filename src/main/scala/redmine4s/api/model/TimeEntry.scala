package redmine4s.api.model

import org.joda.time.{DateTime, LocalDate}
import redmine4s.Redmine

case class TimeEntry(id: Long,
                     project: (Long, String),
                     issueId: Option[Long],
                     user: (Long, String),
                     activity: (Long, String),
                     hours: Double,
                     comments: String,
                     spentOn: LocalDate,
                     createdOn: DateTime,
                     updatedOn: DateTime,
                     customField: Option[Seq[CustomFieldValue]],
                     redmine: Redmine) {
  /** Updating a time entry */
  def updateToProject(projectId: Option[Long] = None,
                      spentOn: Option[LocalDate] = None,
                      hours: Option[Double] = None,
                      activityId: Option[Long] = None,
                      comments: Option[String] = None): TimeEntry = redmine.updateTimeEntryToProject(id, projectId, spentOn, hours, activityId, comments)

  /** Updating a time entry */
  def updateToIssue(issueId: Option[Long] = None,
                    spentOn: Option[LocalDate] = None,
                    hours: Option[Double] = None,
                    activityId: Option[Long] = None,
                    comments: Option[String] = None): TimeEntry = redmine.updateTimeEntryToIssue(id, issueId, spentOn, hours, activityId, comments)

  /** Deleting a time entry */
  def delete(): Unit = redmine.deleteTimeEntry(id)
}
