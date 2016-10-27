package redmine4s.api.json

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model._

trait UserJsonHelper extends CustomFieldJsonHelper with ProjectMembershipJsonHelper {
  implicit val userReads: Reads[User] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'login).read[String] ~
      (__ \ 'lastname).read[String] ~
      (__ \ 'firstname).read[String] ~
      (__ \ 'mail).read[String] ~
      (__ \ 'last_login_on).readNullable[DateTime](timeReads) ~
      (__ \ 'created_on).read[DateTime](timeReads) ~
      (__ \ 'groups).readNullable[Seq[(Long, String)]] ~
      (__ \ 'memberships).readNullable[Seq[ProjectMembership]] ~
      (__ \ 'custom_fields).readNullable[Seq[CustomField]]
    ) (User.apply _)
}
