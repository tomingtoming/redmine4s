package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model.Query

trait QueryJsonHelper extends BaseJsonHelper {
  implicit val queryReads: Reads[Query] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      (__ \ 'is_public).readNullable[Boolean] ~
      (__ \ 'project_id).readNullable[Long]
    ) { (id: Long, name: String, isPublicOpt: Option[Boolean], projectId: Option[Long]) =>
    Query(id, name, isPublicOpt.getOrElse(false), projectId, null)
  }
}
