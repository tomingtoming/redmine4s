package redmine4s.api.model

case class CustomField(id: Long,
                       name: String,
                       customizedType: String,
                       fieldFormat: String,
                       regexp: String,
                       minLength: Option[Int],
                       maxLength: Option[Int],
                       isRequired: Boolean,
                       isFilter: Boolean,
                       searchable: Boolean,
                       multiple: Boolean,
                       defaultValue: Option[String],
                       visible: Boolean,
                       possibleValues: Option[Seq[(String, String)]])

case class CustomFieldValue(id: Long, name: String, value: Option[String])
