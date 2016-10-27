package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model._

trait ProjectMembershipJsonHelper extends RoleJsonHelper {
  implicit val projectMembershipReads: Reads[ProjectMembership] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'project).read[(Long, String)] ~
      (__ \ 'user).readNullable[(Long, String)] ~
      (__ \ 'group).readNullable[(Long, String)] ~
      (__ \ 'roles).read[Iterable[Role]]
    ) (ProjectMembership.apply _)
}
