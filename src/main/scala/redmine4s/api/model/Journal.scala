package redmine4s.api.model

import org.joda.time.DateTime

case class Journal(id: Long, user: (Long, String), notes: Option[String], createdOn: DateTime, details: Seq[JournalDetails])
