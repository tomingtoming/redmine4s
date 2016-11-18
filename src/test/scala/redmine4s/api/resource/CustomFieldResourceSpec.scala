package redmine4s.api.resource

import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.scalatest.{DiagrammedAssertions, FlatSpec}
import org.scalatest.mockito.MockitoSugar
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.slf4j.{Logger, LoggerFactory}
import redmine4s.Redmine
import redmine4s.auth.Authorization
import redmine4s.conf.Configuration

class CustomFieldResourceSpec extends FlatSpec with DiagrammedAssertions with MockitoSugar {
  "CustomFieldResource.listCustomFields" should "POST simple HTTP request" in {
    val mockHttpClient = mock[HttpClient]
    val customFieldResource = new CustomFieldResource {
      override protected val httpClient: HttpClient = mock[HttpClient]
      override protected val configuration: Configuration = mock[Configuration]
      override protected val authorization: Option[Authorization] = Some(mock[Authorization])
      override protected val logger: Logger = LoggerFactory.getLogger("nolog")
      override protected val redmine: Redmine = mock[Redmine]
      when(authorization.get.header).thenReturn(("HttpHeaderKey", "HttpHeaderValue"))
      when(httpClient.execute(any[HttpGet])).thenReturn(mock[HttpResponse])
    }

    val customFields = customFieldResource.listCustomFields()
    assert(customFields.toList.length === 2)
  }
}
