package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.TimeEntryActivity

trait TimeEntryActivityResource extends BaseResource {
  def listTimeEntryActivities(): Iterable[TimeEntryActivity] = {
    import redmine4s.api.json.EnumerationsJsonHelper.timeEntryActivitiesReads
    list("/enumerations/time_entry_activities.json", __ \ 'time_entry_activities, Map.empty).toIterable
  }
}
