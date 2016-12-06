package redmine4s.api.model

import play.api.libs.json.JsValue
import redmine4s.Redmine

trait RedmineModelBase[T] {
  def setRedmine(redmine: Redmine): T

  def setJsValue(jsValue: JsValue): T
}
