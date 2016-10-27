package redmine4s.api.model

import org.joda.time.DateTime
import redmine4s.Redmine

case class WikiIndex(title: String, parent: Option[String], version: Int, createdOn: DateTime, updatedOn: DateTime, projectId: Long, redmine: Redmine) {
  lazy val show: Wiki = redmine.showWiki(projectId, title)
}

case class Wiki(title: String, parent: Option[String], version: Int, createdOn: DateTime, updatedOn: DateTime, text: String, author: (Long, String), comments: String, attachments: Option[Seq[Attachment]], projectId: Long, redmine: Redmine)
