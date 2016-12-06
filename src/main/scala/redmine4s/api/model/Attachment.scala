package redmine4s.api.model

import java.io.InputStream

import org.apache.commons.codec.digest.DigestUtils
import org.joda.time.DateTime
import play.api.libs.json.JsValue
import redmine4s.Redmine

case class Attachment(id: Long,
                      fileName: String,
                      fileSize: Long,
                      contentType: Option[String],
                      description: String,
                      contentUrl: String,
                      author: (Long, String),
                      createdOn: DateTime,
                      jsValue: JsValue,
                      redmine: Redmine) extends RedmineModelBase[Attachment] {

  override def setRedmine(redmine: Redmine): Attachment = this.copy(redmine = redmine)

  override def setJsValue(jsValue: JsValue): Attachment = this.copy(jsValue = jsValue)

  lazy val content: Array[Byte] = redmine.getAttachmentContent(this)
  lazy val digest: String = DigestUtils.md5Hex(content)
  lazy val diskDirectory = createdOn.toString("yyyy/MM")
  lazy val diskFilename = {
    def letterCheck(str: String): Boolean = str.forall { c => c.isLower || c.isUpper || c.isDigit || "_-.".contains(c) }

    val header = createdOn.toString("yyMMddHHmmss_")
    if (letterCheck(fileName)) {
      header + fileName
    } else fileName.split('.').lastOption match {
      case Some(ext) if letterCheck(ext) =>
        header + DigestUtils.md5Hex(fileName) + "." + ext
      case _ =>
        header + DigestUtils.md5Hex(fileName)
    }
  }
}

case class UploadFile(filename: String, contentType: String, content: Either[InputStream, Array[Byte]])

case class UploadedFile(filename: String, contentType: String, token: String)
