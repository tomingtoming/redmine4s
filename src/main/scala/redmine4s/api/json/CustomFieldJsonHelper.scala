package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads.pure
import play.api.libs.json._
import redmine4s.api.model._

trait CustomFieldJsonHelper extends BaseJsonHelper {
  implicit val customizedTypeReads: Reads[CustomizedType] = __.read[String].map(CustomizedType.fromApiExpr)
  implicit val fieldFormatReads: Reads[FieldFormat] = __.read[String].map(FieldFormat.fromApiExpr)
  implicit val labelValueReads: Reads[(String, String)] = (
    (__ \ 'label).read[String] ~
      (__ \ 'value).read[String]
    ) { (label: String, value: String) => (label, value) }
  implicit val customFieldReads: Reads[CustomField] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      (__ \ 'customized_type).read[CustomizedType] ~
      (__ \ 'field_format).read[FieldFormat] ~
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
    ) { (id, name, customizedType, fieldFormat, regexp, minLength, maxLength, isRequired, isFilter, searchable, multiple, defaultValue, visible, possibleValues) =>
    CustomField(id, name, customizedType, fieldFormat, regexp, minLength, maxLength, isRequired, isFilter, searchable, multiple, defaultValue, visible, possibleValues, null, null)
  }
  implicit val customFieldValueReads: Reads[CustomFieldValue] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'name).read[String] ~
      (__ \ 'value).readNullable[String]
    ) (CustomFieldValue.apply _)
}
