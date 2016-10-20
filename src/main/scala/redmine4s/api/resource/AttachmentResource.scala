package redmine4s.api.resource

import java.io.ByteArrayOutputStream

import org.apache.http.client.methods.HttpGet
import org.apache.http.client.utils.HttpClientUtils
import redmine4s.api.model.Attachment

trait AttachmentResource extends BaseResource {
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
}
