package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.json.JsonHelper.trackerReads
import redmine4s.api.model.Tracker

/**
  * Trackers
  * http://www.redmine.org/projects/redmine/wiki/Rest_Trackers
  */
trait TrackerResource extends BaseResource {
  /** Returns the list of all trackers. */
  def listTrackers(): Iterable[Tracker] = {
    list("/trackers.json", __ \ 'trackers, Map.empty).toIterable
  }
}
