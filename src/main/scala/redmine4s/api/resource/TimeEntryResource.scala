package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.json.JsonHelper.timeEntryReads
import redmine4s.api.model.TimeEntry

trait TimeEntryResource extends BaseResource {
  def listTimeEntries(): Iterable[TimeEntry] = {
    list("/time_entries.json", __ \ 'time_entries, Map.empty).toIterable
  }

  def showTimeEntry(timeEntryId: Long): TimeEntry = {
    show(s"/time_entries/$timeEntryId.json", __ \ 'time_entry, Map.empty)
  }
}
