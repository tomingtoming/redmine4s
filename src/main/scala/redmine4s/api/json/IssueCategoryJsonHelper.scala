package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model._

trait IssueCategoryJsonHelper extends BaseJsonHelper {
  implicit val issueCategoryReads: Reads[IssueCategory] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'project).read[(Long, String)] ~
      (__ \ 'name).read[String] ~
      (__ \ 'assigned_to).readNullable[(Long, String)]
    ) (IssueCategory.apply _)
  implicit val issueCategoryCreateWrites = (
    (__ \ 'name).write[String] ~
      (__ \ 'assigned_to).writeNullable[Long]
    ).tupled
  implicit val issueCategoryUpdateWrites = (
    (__ \ 'name).writeNullable[String] ~
      (__ \ 'assigned_to).writeNullable[Long]
    ).tupled
}
