package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.Tracker

trait TrackerResource extends BaseResource {
  def listTrackers(): Iterable[Tracker] = {
    import redmine4s.api.json.TrackerJsonHelper.trackerReads
    list("/trackers.json", __ \ 'trackers, Map.empty).toIterable
  }
}