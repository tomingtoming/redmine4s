package redmine4s.api.model

import play.api.libs.json.JsValue
import redmine4s.Redmine

case class ProjectMembership(id: Long,
                             project: (Long, String),
                             user: Option[(Long, String)],
                             group: Option[(Long, String)],
                             roles: Iterable[Role],
                             jsValue: JsValue,
                             redmine: Redmine) extends RedmineModelBase[ProjectMembership] {

  override def setRedmine(redmine: Redmine): ProjectMembership = this.copy(redmine = redmine)

  override def setJsValue(jsValue: JsValue): ProjectMembership = this.copy(jsValue = jsValue)

  /** Returns the membership of given id. */
  def show(): ProjectMembership = redmine.showProjectMembership(id)

  /** Updates the membership of given id. Only the roles can be updated, the project and the user of a membership are read-only. */
  def update(roleIds: Seq[Long]): Unit = redmine.updateProjectMembership(id, roleIds)

  /** Deletes a memberships. */
  def delete(): Unit = redmine.deleteProjectMembership(id)
}
