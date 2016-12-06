package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads.pure
import play.api.libs.json._
import redmine4s.api.model.Query

trait QueryJsonHelper extends BaseJsonHelper {
  implicit val queryReads: Reads[Query] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      ((__ \ 'is_public).read[Boolean] or pure(false)) ~
      (__ \ 'project_id).readNullable[Long]
    ) { (id, name, isPublic, projectId) =>
    Query(id, name, isPublic, projectId, null, null)
  }
}
