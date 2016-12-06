package redmine4s.api.json

import org.joda.time.{DateTime, LocalDate}
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads.pure
import play.api.libs.json._
import redmine4s.api.model._

trait VersionJsonHelper extends CustomFieldJsonHelper {
  implicit val versionStatusReads: Reads[Status] = JsPath.read[String].map(Status.fromString)
  implicit val versionSharingReads: Reads[Sharing] = JsPath.read[String].map(Sharing.fromString)
  implicit val versionStatusWrites: Writes[Status] = JsPath.write[String].contramap(_.toString)
  implicit val versionSharingWrites: Writes[Sharing] = JsPath.write[String].contramap(_.toString)
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
      ((__ \ 'custom_fields).read[Seq[CustomFieldValue]] or pure(Seq.empty[CustomFieldValue]))
    ) { (id, project, name, description, dueDate, status, sharing, createdOn, updatedOn, customField) =>
    Version(id, project, name, description, dueDate, status, sharing, createdOn, updatedOn, customField, null, null)
  }
  implicit val versionCreateWrites = (
    (__ \ 'version \ 'name).write[String] ~
      (__ \ 'version \ 'status).writeNullable[Status] ~
      (__ \ 'version \ 'sharing).writeNullable[Sharing] ~
      (__ \ 'version \ 'due_date).writeNullable[LocalDate](dateWrites) ~
      (__ \ 'version \ 'description).writeNullable[String] ~
      (__ \ 'version \ 'custom_fields).writeNullable[Seq[(Long, String)]]
    ).tupled
  implicit val versionUpdateWrites = (
    (__ \ 'version \ 'name).writeNullable[String] ~
      (__ \ 'version \ 'status).writeNullable[Status] ~
      (__ \ 'version \ 'sharing).writeNullable[Sharing] ~
      (__ \ 'version \ 'due_date).writeNullable[LocalDate](dateWrites) ~
      (__ \ 'version \ 'description).writeNullable[String] ~
      (__ \ 'version \ 'custom_fields).writeNullable[Seq[(Long, String)]]
    ).tupled
}
