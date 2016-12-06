package redmine4s.api.model

import play.api.libs.json.JsValue
import redmine4s.Redmine

case class IssuePriority(id: Long,
                         name: String,
                         isDefault: Boolean,
                         jsValue: JsValue,
                         redmine: Redmine) extends RedmineModelBase[IssuePriority] {
  override def setRedmine(redmine: Redmine): IssuePriority = this.copy(redmine = redmine)

  override def setJsValue(jsValue: JsValue): IssuePriority = this.copy(jsValue = jsValue)
}
