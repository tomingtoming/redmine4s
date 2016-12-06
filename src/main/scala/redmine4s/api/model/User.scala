package redmine4s.api.model

import org.joda.time.DateTime
import play.api.libs.json.JsValue
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
                customField: Seq[CustomFieldValue],
                apiKey: Option[String],
                status: Option[Int],
                jsValue: JsValue,
                redmine: Redmine) extends RedmineModelBase[User] {

  override def setRedmine(redmine: Redmine): User = this.copy(redmine = redmine)

  override def setJsValue(jsValue: JsValue): User = this.copy(jsValue = jsValue)

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
             mustChangePasswd: Option[Boolean] = None,
             customFields: Option[Seq[(Long, String)]] = None): User = redmine.updateUser(id, login, password, firstname, lastname, mail, authSourceId, mailNotification, mustChangePasswd, customFields)

  /** Deletes a user. */
  def delete(): Unit = redmine.deleteUser(id)
}
