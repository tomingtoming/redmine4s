package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.CustomField

/**
  * Custom Fields
  * http://www.redmine.org/projects/redmine/wiki/Rest_CustomFields
  */
trait CustomFieldResource extends BaseResource {
  /** Returns all the custom fields definitions. */
  def listCustomFields(): Iterable[CustomField] = {
    import redmine4s.api.json.JsonHelper.customFieldReads
    list("/custom_fields.json", __ \ 'custom_fields, Map.empty).toIterable
  }
}
