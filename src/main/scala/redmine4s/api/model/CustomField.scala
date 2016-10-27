package redmine4s.api.model

case class CustomField(id: Long,
                       name: String,
                       customizedType: String,
                       fieldFormat: String,
                       regexp: String,
                       minLength: Int,
                       maxLength: Int,
                       isRequired: Boolean,
                       isFilter: Boolean,
                       searchable: Boolean,
                       multiple:Boolean,
                       defaultValue:String,
                       visible:Boolean,
                       possibleValues:Option[Seq[(String, String)]])

case class CustomFieldValue(id: Long, name: String, value: String)
