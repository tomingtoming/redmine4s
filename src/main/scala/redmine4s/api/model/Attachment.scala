package redmine4s.api.model

import org.joda.time.DateTime

case class Attachment(id: Long, fileName: String, fileSize: Long, description: String, contentUrl: String, author: (Long, String), createdOn: DateTime) {
}
