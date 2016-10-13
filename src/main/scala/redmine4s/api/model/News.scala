package redmine4s.api.model

import org.joda.time.DateTime

case class News(id: Long, project: (Long, String), author: (Long, String), title: String, summary: String, description: String, createdOn: DateTime)
