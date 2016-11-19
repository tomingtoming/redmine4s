package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model.IssueStatus

trait IssueStatusJsonHelper extends BaseJsonHelper {
  implicit val issueStatusReads: Reads[IssueStatus] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      (__ \ 'is_default).readNullable[Boolean] ~
      (__ \ 'is_closed).readNullable[Boolean]
    ) { (id: Long, name: String, isDefaultOpt: Option[Boolean], isClosedOpt: Option[Boolean]) =>
    IssueStatus(id, name, isDefaultOpt.getOrElse(false), isClosedOpt.getOrElse(false))
  }
}
