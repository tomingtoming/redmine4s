package redmine4s.api.model

import org.joda.time.DateTime
import redmine4s.Redmine

case class Wiki(title: String, parent: Option[String], version: Int, createdOn: DateTime, updatedOn: DateTime,
                text: Option[String], author: Option[(Long, String)], comments: Option[String], projectId: Long, redmine: Redmine) {
  lazy val show: Wiki = redmine.showWiki(projectId, title)
}
