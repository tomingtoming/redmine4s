package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model.Tracker

object TrackerJsonHelper extends BaseJsonHelper {
  implicit val trackerReads: Reads[Tracker] = (
    (__ \ "id").read[Long] ~
      (__ \ "name").read[String]
    ) (Tracker.apply _)
}
