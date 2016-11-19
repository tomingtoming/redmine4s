package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.libs.json.Reads.pure
import redmine4s.api.model._

trait CustomFieldJsonHelper extends BaseJsonHelper {
  implicit val labelValueReads: Reads[(String, String)] = (
    (__ \ 'label).read[String] ~
      (__ \ 'value).read[String]
    ) { (label: String, value: String) => (label, value) }
  implicit val customFieldReads: Reads[CustomField] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      (__ \ 'customized_type).read[String] ~
      (__ \ 'field_format).read[String] ~
      (__ \ 'regexp).read[String] ~
      (__ \ 'min_length).readNullable[Int] ~
      (__ \ 'max_length).readNullable[Int] ~
      ((__ \ 'is_required).read[Boolean] or pure(false)) ~
      ((__ \ 'is_filter).read[Boolean] or pure(false)) ~
      ((__ \ 'searchable).read[Boolean] or pure(false)) ~
      ((__ \ 'multiple).read[Boolean] or pure(false)) ~
      (__ \ 'default_value).readNullable[String] ~
      (__ \ 'visible).read[Boolean] ~
      (__ \ 'possibleValues).readNullable[Seq[(String, String)]]
    ) (CustomField.apply _)
  implicit val customFieldValueReads: Reads[CustomFieldValue] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      (__ \ 'value).readNullable[String]
    ) (CustomFieldValue.apply _)
}
