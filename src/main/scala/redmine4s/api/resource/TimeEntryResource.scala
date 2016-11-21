package redmine4s.api.resource

import org.joda.time.LocalDate
import play.api.libs.json._
import redmine4s.api.model.TimeEntry

/**
  * Time Entries
  * http://www.redmine.org/projects/redmine/wiki/Rest_TimeEntries
  */
trait TimeEntryResource extends BaseResource {
  private def applyRedmineToTimeEntry: PartialFunction[TimeEntry, TimeEntry] = {
    case p: TimeEntry => p.copy(redmine = redmine)
  }

  /** Listing time entries */
  def listTimeEntries(): Iterable[TimeEntry] = {
    import redmine4s.api.json.JsonHelper.timeEntryReads
    list("/time_entries.json", __ \ 'time_entries, Map.empty).map(applyRedmineToTimeEntry).toIterable
  }

  /** Showing a time entry */
  def showTimeEntry(timeEntryId: Long): TimeEntry = {
    import redmine4s.api.json.JsonHelper.timeEntryReads
    applyRedmineToTimeEntry(show(s"/time_entries/$timeEntryId.json", __ \ 'time_entry, Map.empty))
  }

  /** Creating a time entry */
  def createTimeEntryToProject(projectId: Long,
                               spentOn: LocalDate = LocalDate.now(),
                               hours: Double,
                               activityId: Long,
                               comments: String = "",
                               customFields: Option[Seq[(Long, String)]] = None): TimeEntry = {
    import redmine4s.api.json.JsonHelper.{timeEntryCreateForProjectWrites, timeEntryReads}
    applyRedmineToTimeEntry(create("/time_entries.json", __ \ 'time_entry, (projectId, spentOn, hours, activityId, comments, customFields)))
  }

  /** Creating a time entry */
  def createTimeEntryToIssue(issueId: Long,
                             spentOn: LocalDate = LocalDate.now(),
                             hours: Double,
                             activityId: Long,
                             comments: String = "",
                             customFields: Option[Seq[(Long, String)]] = None): TimeEntry = {
    import redmine4s.api.json.JsonHelper.{timeEntryCreateForIssueWrites, timeEntryReads}
    applyRedmineToTimeEntry(create("/time_entries.json", __ \ 'time_entry, (issueId, spentOn, hours, activityId, comments, customFields)))
  }

  /** Updating a time entry */
  def updateTimeEntryToProject(timeEntryId: Long,
                               projectId: Option[Long] = None,
                               spentOn: Option[LocalDate] = None,
                               hours: Option[Double] = None,
                               activityId: Option[Long] = None,
                               comments: Option[String] = None,
                               customFields: Option[Seq[(Long, String)]] = None): TimeEntry = {
    import redmine4s.api.json.JsonHelper.timeEntryUpdateForProjectWrites
    update(s"/time_entries/$timeEntryId.json", (projectId, spentOn, hours, activityId, comments, customFields))
    showTimeEntry(timeEntryId)
  }

  /** Updating a time entry */
  def updateTimeEntryToIssue(timeEntryId: Long,
                             issueId: Option[Long] = None,
                             spentOn: Option[LocalDate] = None,
                             hours: Option[Double] = None,
                             activityId: Option[Long] = None,
                             comments: Option[String] = None,
                             customFields: Option[Seq[(Long, String)]] = None): TimeEntry = {
    import redmine4s.api.json.JsonHelper.timeEntryUpdateForIssueWrites
    update(s"/time_entries/$timeEntryId.json", (issueId, spentOn, hours, activityId, comments, customFields))
    showTimeEntry(timeEntryId)
  }

  /** Deleting a time entry */
  def deleteTimeEntry(timeEntryId: Long): Unit = delete(s"/time_entries/$timeEntryId.json")
}
