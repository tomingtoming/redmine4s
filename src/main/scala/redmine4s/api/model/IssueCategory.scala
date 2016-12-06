package redmine4s.api.model

import play.api.libs.json.JsValue
import redmine4s.Redmine

case class IssueCategory(id: Long,
                         project: (Long, String),
                         name: String,
                         assignedTo: Option[(Long, String)],
                         jsValue: JsValue,
                         redmine: Redmine) extends RedmineModelBase[IssueCategory] {

  override def setRedmine(redmine: Redmine): IssueCategory = this.copy(redmine = redmine)

  override def setJsValue(jsValue: JsValue): IssueCategory = this.copy(jsValue = jsValue)
}
