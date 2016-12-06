package redmine4s.api.model

import play.api.libs.json.JsValue
import redmine4s.Redmine

case class Tracker(id: Long,
                   name: String,
                   defaultStatus: Option[(Long, String)],
                   jsValue: JsValue,
                   redmine: Redmine) extends RedmineModelBase[Tracker] {

  override def setRedmine(redmine: Redmine): Tracker = this.copy(redmine = redmine)

  override def setJsValue(jsValue: JsValue): Tracker = this.copy(jsValue = jsValue)
}

