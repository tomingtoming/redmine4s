package redmine4s.api.model

import org.joda.time.DateTime
import play.api.libs.json.JsValue
import redmine4s.Redmine

case class News(id: Long,
                project: (Long, String),
                author: (Long, String),
                title: String,
                summary: String,
                description: String,
                createdOn: DateTime,
                jsValue: JsValue,
                redmine: Redmine) extends RedmineModelBase[News] {

  override def setRedmine(redmine: Redmine): News = this.copy(redmine = redmine)

  override def setJsValue(jsValue: JsValue): News = this.copy(jsValue = jsValue)
}
