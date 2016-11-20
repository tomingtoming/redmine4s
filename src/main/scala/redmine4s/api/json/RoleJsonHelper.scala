package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model.{Permission, Role}

trait RoleJsonHelper extends BaseJsonHelper {
  implicit val permissionReads: Reads[Permission] = __.read[String].map(Permission.fromString)
  implicit val roleReads: Reads[Role] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      (__ \ 'inherited).readNullable[Boolean] ~
      (__ \ 'permissions).readNullable[Iterable[Permission]]
    ) { (id, name, inherited, permissions) => Role(id, name, inherited, permissions, null) }
}
