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
      (__ \ 'custom_fields).readNullable[Seq[CustomFieldValue]]
    ) { (id: Long, login: String, lastname: String, firstname: String, mail: String, lastLoginOn: Option[DateTime], createdOn: DateTime, groups: Option[Seq[(Long, String)]], memberships: Option[Seq[ProjectMembership]], customField: Option[Seq[CustomFieldValue]]) =>
    User(id, login, lastname, firstname, mail, lastLoginOn, createdOn, groups, memberships, customField, null)
  }
}
