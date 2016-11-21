package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.Group

/**
  * Groups
  * http://www.redmine.org/projects/redmine/wiki/Rest_Groups
  */
trait GroupResource extends BaseResource {
  private def applyRedmineToGroup: PartialFunction[Group, Group] = {
    case p: Group => p.copy(redmine = redmine, memberships = p.memberships.map(_.map(_.copy(redmine = redmine))))
  }

  /** Returns the list of groups. */
  def listGroups(): Iterable[Group] = {
    import redmine4s.api.json.JsonHelper.groupReads
    list("/groups.json", __ \ 'groups, Map.empty).map(applyRedmineToGroup).toIterable
  }

  /** Creates a group. */
  def createGroup(name: String, ids: Option[Seq[Long]] = None, customFields: Option[Seq[(Long, String)]] = None): Group = {
    import redmine4s.api.json.JsonHelper.{groupCreateWrites, groupReads}
    applyRedmineToGroup(create("/groups.json", __ \ 'group, (name, ids, customFields)))
  }

  /** Returns details of a group. */
  def showGroup(groupId: Long): Group = {
    import redmine4s.api.json.JsonHelper.groupReads
    applyRedmineToGroup(show(s"/groups/$groupId.json", __ \ 'group, Map("include" -> "users,memberships")))
  }

  /** Updates an existing group. */
  def updateGroup(groupId: Long, name: Option[String] = None, ids: Option[Seq[Long]] = None, customFields: Option[Seq[(Long, String)]] = None): Group = {
    import redmine4s.api.json.JsonHelper.groupUpdateWrites
    update(s"/groups/$groupId.json", (name, ids, customFields))
    showGroup(groupId)
  }

  /** Deletes an existing group. */
  def deleteGroup(groupId: Long): Unit = delete(s"/groups/$groupId.json")

  /** Adds an existing user to a group. */
  def addUserToGroup(userId: Long, groupId: Long): Group = {
    import redmine4s.api.json.JsonHelper.groupReads
    applyRedmineToGroup(create(s"/groups/$groupId/users.json", __ \ 'user_id, userId))
  }

  /** Adds an existing user to a group. */
  def deleteUserFromGroup(userId: Long, groupId: Long): Unit = delete(s"/groups/$groupId/users/$userId.json")
}
