package redmine4s.api.json

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model._

trait WikiJsonHelper extends AttachmentJsonHelper {
  implicit val wikiIndexReads: Reads[WikiIndex] = (
    (__ \ 'title).read[String] ~
      ((__ \ 'parent \ 'title).readNullable[String] orElse (__ \ 'parent).readNullable[String]) ~
      (__ \ 'version).read[Int] ~
      (__ \ 'created_on).read[DateTime](timeReads) ~
      (__ \ 'updated_on).read[DateTime](timeReads)
    ) { (title, parent, version, createdOn, updatedOn) =>
    WikiIndex(title, parent, version, createdOn, updatedOn, 0L, null)
  }
  implicit val wikiReads: Reads[Wiki] = (
    (__ \ 'title).read[String] ~
      ((__ \ 'parent \ 'title).readNullable[String] orElse (__ \ 'parent).readNullable[String]) ~
      (__ \ 'version).read[Int] ~
      (__ \ 'created_on).read[DateTime](timeReads) ~
      (__ \ 'updated_on).read[DateTime](timeReads) ~
      (__ \ 'text).read[String] ~
      (__ \ 'author).read[(Long, String)] ~
      (__ \ 'comments).read[String] ~
      (__ \ 'attachments).readNullable[Seq[Attachment]]
    ) { (title, parent, version, createdOn, updatedOn, text, author, comments, attachments) =>
    Wiki(title, parent, version, createdOn, updatedOn, text, author, comments, attachments, 0L, null)
  }
  implicit val wikiUpdateWrites = (
    (__ \ 'wiki_page \ 'text).writeNullable[String] ~
      (__ \ 'wiki_page \ 'comments).writeNullable[String] ~
      (__ \ 'wiki_page \ 'version).writeNullable[Int] ~
      (__ \ 'attachments).writeNullable[Seq[UploadedFile]]
    ).tupled
}
