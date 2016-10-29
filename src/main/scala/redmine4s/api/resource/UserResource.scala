package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.User

/**
  * Users
  * http://www.redmine.org/projects/redmine/wiki/Rest_Users
  */
trait UserResource extends BaseResource {
  private def applyRedmineToUser: PartialFunction[User, User] = {
    case p: User => p.copy(redmine = redmine, memberships = p.memberships.map(_.map(_.copy(redmine = redmine))))
  }

  /** Returns a list of users. */
  def listUsers(): Iterable[User] = {
    import redmine4s.api.json.JsonHelper.userReads
    list("/users.json", __ \ 'users, Map.empty).map(applyRedmineToUser).toIterable
  }

  /** Creates a user. */
  def createUser(login: String,
                 password: Option[String] = None,
                 firstname: String,
                 lastname: String,
                 mail: String,
                 authSourceId: Option[Long] = None,
                 mailNotification: Option[String] = None,
                 mustChangePasswd: Option[Boolean] = None): User = {
    import redmine4s.api.json.JsonHelper.{userCreateWrites, userReads}
    applyRedmineToUser(create("/users.json", __ \ 'user, (login, password, firstname, lastname, mail, authSourceId, mailNotification, mustChangePasswd)))
  }

  /** Returns the user details. */
  def showUser(userId: Long): User = {
    import redmine4s.api.json.JsonHelper.userReads
    applyRedmineToUser(show(s"/users/$userId.json", __ \ 'user, Map("include" -> "memberships,groups")))
  }

  /** Updates a user. */
  def updateUser(userId: Long,
                 login: Option[String] = None,
                 password: Option[String] = None,
                 firstname: Option[String] = None,
                 lastname: Option[String] = None,
                 mail: Option[String] = None,
                 authSourceId: Option[Long] = None,
                 mailNotification: Option[String] = None,
                 mustChangePasswd: Option[Boolean] = None): User = {
    import redmine4s.api.json.JsonHelper.{userReads, userUpdateWrites}
    create(s"/users/$userId.json", __ \ 'user, (login, password, firstname, lastname, mail, authSourceId, mailNotification, mustChangePasswd))
    showUser(userId)
  }

  /** Deletes a user. */
  def deleteUser(userId: Long): Unit = delete(s"/users/$userId.json")
}
