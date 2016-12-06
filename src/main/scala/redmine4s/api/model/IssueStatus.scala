package redmine4s.api.model

import play.api.libs.json.JsValue
import redmine4s.Redmine

case class IssueStatus(id: Long,
                       name: String,
                       isDefault: Boolean,
                       isClosed: Boolean,
                       jsValue: JsValue,
                       redmine: Redmine) extends RedmineModelBase[IssueStatus] {

  override def setRedmine(redmine: Redmine): IssueStatus = this.copy(redmine = redmine)

  override def setJsValue(jsValue: JsValue): IssueStatus = this.copy(jsValue = jsValue)
}
