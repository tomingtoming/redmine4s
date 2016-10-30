package redmine4s.api.resource

import java.io.{ByteArrayOutputStream, InputStream}

import play.api.libs.json._
import org.apache.http.HttpEntity
import org.apache.http.client.methods.{HttpGet, HttpPost}
import org.apache.http.client.utils.HttpClientUtils
import org.apache.http.entity.{ByteArrayEntity, InputStreamEntity}
import play.api.libs.json.Json
import redmine4s.api.model.{Attachment, UploadFile, UploadedFile}

/**
  * Attachments
  * http://www.redmine.org/projects/redmine/wiki/Rest_Attachments
  */
trait AttachmentResource extends BaseResource {

  /**
    * Returns the description of the attachment of given id.
    * The file can actually be downloaded at the URL given by the content_url attribute in the response.
    */
  def showAttachment(attachmentId: Long): Attachment = {
    import redmine4s.api.json.JsonHelper.attachmentReads
    show(s"/attachments/$attachmentId.json", __ \ 'attachment, Map.empty)
  }

  def getAttachmentContent(attachment: Attachment): Array[Byte] = {
    val get = new HttpGet(attachment.contentUrl)
    authorization.foreach { auth => get.addHeader(auth.header._1, auth.header._2) }
    val response = httpClient.execute(get)
    try {
      response.getStatusLine.getStatusCode match {
        case 200 =>
          logger.debug(s"url=$get, ContentLength=${response.getEntity.getContentLength}")
          val bOut = new ByteArrayOutputStream
          response.getEntity.writeTo(bOut)
          bOut.toByteArray
        case 403 /*Forbidden*/ =>
          logger.debug(s"url=$get, status_line=${response.getStatusLine.toString}")
          throw new ForbiddenException(response.getStatusLine.toString)
        case n =>
          logger.debug(s"url=$get, status_line=${response.getStatusLine.toString}")
          throw new ResourceException(response.getStatusLine.toString)
      }
    } finally {
      HttpClientUtils.closeQuietly(response)
    }
  }

  def upload(uploadFile: UploadFile): UploadedFile = uploadFile.content match {
    case Left(in) => upload(uploadFile.filename, uploadFile.contentType, in)
    case Right(bytes) => upload(uploadFile.filename, uploadFile.contentType, bytes)
  }

  def upload(filename: String, contentType: String, bytes: Array[Byte]): UploadedFile = {
    upload(filename, contentType, new ByteArrayEntity(bytes))
  }

  def upload(filename: String, contentType: String, in: InputStream): UploadedFile = {
    upload(filename, contentType, new InputStreamEntity(in))
  }

  /**
    * Internal upload method depend on HttpClient.
    */
  private def upload(filename: String, contentType: String, httpEntity: HttpEntity): UploadedFile = {
    val post = new HttpPost(configuration.baseUrl + "/uploads.json")
    authorization.foreach { auth => post.addHeader(auth.header._1, auth.header._2) }
    post.addHeader("Content-Type", "application/octet-stream")
    post.setEntity(httpEntity)
    val response = httpClient.execute(post)
    try {
      response.getStatusLine.getStatusCode match {
        case 201 =>
          val responseJson = Json.parse(response.getEntity.getContent)
          logger.debug(s"url=$post, uploaded:${httpEntity.getContentLength}B, responseJson=$responseJson")
          val token = (responseJson \ "upload" \ "token").as[String]
          UploadedFile(filename, contentType, token)
        case 422 /*Unprocessable Entity*/ =>
          logger.debug(s"url=$post, status_line=${response.getStatusLine.toString}")
          throw new SizeLimitExceededException(response.getStatusLine.toString)
        case 403 /*Forbidden*/ =>
          logger.debug(s"url=$post, status_line=${response.getStatusLine.toString}")
          throw new ForbiddenException(response.getStatusLine.toString)
        case n =>
          logger.debug(s"url=$post, status_line=${response.getStatusLine.toString}")
          throw new ResourceException(response.getStatusLine.toString)
      }
    } finally {
      HttpClientUtils.closeQuietly(response)
    }
  }
}
