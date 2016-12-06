package redmine4s.api.model

import play.api.libs.json.JsValue
import redmine4s.Redmine

case class TimeEntryActivity(id: Long, name: String, isDefault: Boolean,
                             jsValue: JsValue,
                             redmine: Redmine) extends RedmineModelBase[TimeEntryActivity] {
  override def setRedmine(redmine: Redmine): TimeEntryActivity = this.copy(redmine = redmine)

  override def setJsValue(jsValue: JsValue): TimeEntryActivity = this.copy(jsValue = jsValue)
}

