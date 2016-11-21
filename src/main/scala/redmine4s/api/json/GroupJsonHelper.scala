package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads.pure
import play.api.libs.json._
import redmine4s.api.model._

trait GroupJsonHelper extends CustomFieldJsonHelper with ProjectMembershipJsonHelper {
  implicit val groupReads: Reads[Group] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      (__ \ 'users).readNullable[Seq[(Long, String)]] ~
      (__ \ 'memberships).readNullable[Seq[ProjectMembership]] ~
      ((__ \ 'custom_fields).read[Seq[CustomFieldValue]] or pure(Seq.empty[CustomFieldValue]))
    ) { (id, name, users, memberships, customField) =>
    Group(id, name, users, memberships, customField, null)
  }
  implicit val groupCreateWrites = (
    (__ \ 'group \ 'name).write[String] ~
      (__ \ 'group \ 'users).writeNullable[Seq[Long]] ~
      (__ \ 'group \ 'custom_fields).writeNullable[Seq[(Long, String)]]
    ).tupled
  implicit val groupUpdateWrites = (
    (__ \ 'group \ 'name).writeNullable[String] ~
      (__ \ 'group \ 'users).writeNullable[Seq[Long]] ~
      (__ \ 'group \ 'custom_fields).writeNullable[Seq[(Long, String)]]
    ).tupled
}
