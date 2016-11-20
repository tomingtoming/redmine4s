package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.libs.json.Reads.pure
import redmine4s.api.model.Query

trait QueryJsonHelper extends BaseJsonHelper {
  implicit val queryReads: Reads[Query] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      ((__ \ 'is_public).read[Boolean] or pure(false)) ~
      (__ \ 'project_id).readNullable[Long]
    ) (Query.apply _)
}
