package redmine4s.api.model

import redmine4s.Redmine

case class Group(id: Long,
                 name: String,
                 users: Option[Seq[(Long, String)]],
                 memberships: Option[Seq[ProjectMembership]],
                 customField: Seq[CustomFieldValue],
                 redmine: Redmine) {
  /** Returns details of a group. */
  def show(): Group = redmine.showGroup(id)

  /** Updates an existing group. */
  def update(name: Option[String] = None,
             ids: Option[Seq[Long]] = None,
             customFields: Option[Seq[(Long, String)]] = None): Group = redmine.updateGroup(id, name, ids)

  /** Deletes an existing group. */
  def delete(): Unit = redmine.deleteGroup(id)

  /** Adds an existing user to a group. */
  def addUser(userId: Long): Group = redmine.addUserToGroup(userId, id)

  /** Adds an existing user to a group. */
  def deleteUser(userId: Long): Unit = redmine.deleteUserFromGroup(userId, id)
}
