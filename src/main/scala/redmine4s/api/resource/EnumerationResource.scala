package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.{IssuePriority, TimeEntryActivity}

/**
  * Enumerations
  * http://www.redmine.org/projects/redmine/wiki/Rest_Enumerations
  */
trait EnumerationResource extends BaseResource {
  /** Returns the list of issue priorities. */
  def listIssuePriorities(): Iterable[IssuePriority] = {
    import redmine4s.api.json.JsonHelper.issuePriorityReads
    list("/enumerations/issue_priorities.json", __ \ "issue_priorities", Map.empty).map(_.copy(redmine = redmine)).toIterable
  }

  /** Returns the list of time entry activities. */
  def listTimeEntryActivities(): Iterable[TimeEntryActivity] = {
    import redmine4s.api.json.JsonHelper.timeEntryActivitiesReads
    list("/enumerations/time_entry_activities.json", __ \ 'time_entry_activities, Map.empty).map(_.copy(redmine = redmine)).toIterable
  }
}
