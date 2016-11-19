package redmine4s.api.model

import org.joda.time.DateTime
import redmine4s.Redmine

case class User(id: Long,
                login: String,
                lastname: String,
                firstname: String,
                mail: String,
                lastLoginOn: Option[DateTime],
                createdOn: DateTime,
                groups: Option[Seq[(Long, String)]],
                memberships: Option[Seq[ProjectMembership]],
                customField: Option[Seq[CustomFieldValue]],
                apiKey: Option[String],
                status: Option[Int],
                redmine: Redmine) {
  /** Returns the user details. */
  def show(): User = redmine.showUser(id)

  /** Updates a user. */
  def update(login: Option[String] = None,
             password: Option[String] = None,
             firstname: Option[String] = None,
             lastname: Option[String] = None,
             mail: Option[String] = None,
             authSourceId: Option[Long] = None,
             mailNotification: Option[String] = None,
             mustChangePasswd: Option[Boolean] = None): User = redmine.updateUser(id, login, password, firstname, lastname, mail, authSourceId, mailNotification, mustChangePasswd)

  /** Deletes a user. */
  def delete(): Unit = redmine.deleteUser(id)
}
