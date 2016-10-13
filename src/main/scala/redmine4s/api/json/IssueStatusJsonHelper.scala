package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model.IssueStatus

object IssueStatusJsonHelper {
  implicit val issueStatusReads: Reads[IssueStatus] = (
    (__ \ "id").read[Long] ~
      (__ \ "name").read[String] ~
      (__ \ "is_default").read[Boolean](false) ~
      (__ \ "is_closed").read[Boolean](false)
    ) (IssueStatus.apply _)
}
