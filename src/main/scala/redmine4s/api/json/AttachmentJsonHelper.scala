package redmine4s.api.json

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model.{UploadedFile, _}

trait AttachmentJsonHelper extends BaseJsonHelper {
  implicit val attachmentReads: Reads[Attachment] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'filename).read[String] ~
      (__ \ 'filesize).read[Long] ~
      (__ \ 'content_type).readNullable[String] ~
      (__ \ 'description).read[String] ~
      (__ \ 'content_url).read[String] ~
      (__ \ 'author).read[(Long, String)] ~
      (__ \ 'created_on).read[DateTime](timeReads)
    ) { (id, fileName, fileSize, contentType, description, contentUrl, author, createdOn) =>
    Attachment(id, fileName, fileSize, contentType, description, contentUrl, author, createdOn, null)
  }
  implicit val uploadedFileWrites = (
    (__ \ 'filename).write[String] ~
      (__ \ 'content_type).write[String] ~
      (__ \ 'token).write[String]
    ) (unlift(UploadedFile.unapply))
}
