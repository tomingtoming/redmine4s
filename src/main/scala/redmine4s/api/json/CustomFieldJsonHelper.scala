package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model._

trait CustomFieldJsonHelper extends BaseJsonHelper {
  implicit val customFieldReads: Reads[CustomField] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      (__ \ 'value).read[String]
    ) (CustomField.apply _)
  implicit val customFieldWrites: Writes[CustomField] = (
    (__ \ 'id).write[Long] ~
      (__ \ 'name).write[String] ~
      (__ \ 'value).write[String]
    ) (unlift(CustomField.unapply))
}
