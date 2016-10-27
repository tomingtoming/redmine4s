package redmine4s.api.json

import org.joda.time.{DateTime, LocalDate}
import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model._

trait VersionJsonHelper extends CustomFieldJsonHelper {
  implicit val versionReads: Reads[Version] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'project).read[(Long, String)] ~
      (__ \ 'name).read[String] ~
      (__ \ 'description).read[String] ~
      (__ \ 'due_date).readNullable[LocalDate](dateReads) ~
      (__ \ 'status).read[Status] ~
      (__ \ 'sharing).read[Sharing] ~
      (__ \ 'created_on).read[DateTime](timeReads) ~
      (__ \ 'updated_on).read[DateTime](timeReads) ~
      (__ \ 'custom_fields).readNullable[Seq[CustomField]]
    ) (Version.apply _)
  implicit val versionCreateWrites = (
    (__ \ 'name).write[String] ~
      (__ \ 'status).write[Status] ~
      (__ \ 'sharing).write[Sharing] ~
      (__ \ 'due_date).writeNullable[LocalDate](dateWrites) ~
      (__ \ 'description).write[String]
    ).tupled
  implicit val versionUpdateWrites = versionCreateWrites
  implicit val versionStatusReads: Reads[Status] = JsPath.read[String].map(Status.fromString)
  implicit val versionSharingReads: Reads[Sharing] = JsPath.read[String].map(Sharing.fromString)
  implicit val versionStatusWrites: Writes[Status] = JsPath.write[String].contramap(_.toString)
  implicit val versionSharingWrites: Writes[Sharing] = JsPath.write[String].contramap(_.toString)
}
