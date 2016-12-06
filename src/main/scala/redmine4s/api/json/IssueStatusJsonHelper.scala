package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads.pure
import play.api.libs.json._
import redmine4s.api.model.IssueStatus

trait IssueStatusJsonHelper extends BaseJsonHelper {
  implicit val issueStatusReads: Reads[IssueStatus] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      ((__ \ 'is_default).read[Boolean] or pure(false)) ~
      ((__ \ 'is_closed).read[Boolean] or pure(false))
    ) { (id, name, isDefault, isClosed) =>
    IssueStatus(id, name, isDefault, isClosed, null, null)
  }
}
