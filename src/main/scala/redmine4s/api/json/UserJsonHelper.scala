package redmine4s.api.json

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads.pure
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
      ((__ \ 'custom_fields).read[Seq[CustomFieldValue]] or pure(Seq.empty[CustomFieldValue])) ~
      (__ \ 'api_key).readNullable[String] ~
      (__ \ 'status).readNullable[Int]
    ) { (id: Long, login: String, lastname: String, firstname: String, mail: String, lastLoginOn: Option[DateTime], createdOn: DateTime, groups: Option[Seq[(Long, String)]], memberships: Option[Seq[ProjectMembership]], customField: Seq[CustomFieldValue], apiKey: Option[String], status: Option[Int]) =>
    User(id, login, lastname, firstname, mail, lastLoginOn, createdOn, groups, memberships, customField, apiKey, status, null)
  }
  implicit val userCreateWrites = (
    (__ \ 'user \ 'login).write[String] ~
      (__ \ 'user \ 'password).writeNullable[String] ~
      (__ \ 'user \ 'firstname).write[String] ~
      (__ \ 'user \ 'lastname).write[String] ~
      (__ \ 'user \ 'mail).write[String] ~
      (__ \ 'user \ 'auth_source_id).writeNullable[Long] ~
      (__ \ 'user \ 'mail_notification).writeNullable[String] ~
      (__ \ 'user \ 'must_change_passwd).writeNullable[Boolean] ~
      (__ \ 'user \ 'custom_fields).writeNullable[Seq[(Long, String)]]
    ).tupled
  implicit val userUpdateWrites = (
    (__ \ 'user \ 'login).writeNullable[String] ~
      (__ \ 'user \ 'password).writeNullable[String] ~
      (__ \ 'user \ 'firstname).writeNullable[String] ~
      (__ \ 'user \ 'lastname).writeNullable[String] ~
      (__ \ 'user \ 'mail).writeNullable[String] ~
      (__ \ 'user \ 'auth_source_id).writeNullable[Long] ~
      (__ \ 'user \ 'mail_notification).writeNullable[String] ~
      (__ \ 'user \ 'must_change_passwd).writeNullable[Boolean] ~
      (__ \ 'user \ 'custom_fields).writeNullable[Seq[(Long, String)]]
    ).tupled
}
