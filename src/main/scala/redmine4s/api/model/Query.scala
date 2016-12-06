package redmine4s.api.model

import play.api.libs.json.JsValue
import redmine4s.Redmine

case class Query(id: Long,
                 name: String,
                 isPublic: Boolean,
                 projectId: Option[Long],
                 jsValue: JsValue,
                 redmine: Redmine) extends RedmineModelBase[Query] {

  override def setRedmine(redmine: Redmine): Query = this.copy(redmine = redmine)

  override def setJsValue(jsValue: JsValue): Query = this.copy(jsValue = jsValue)
}
