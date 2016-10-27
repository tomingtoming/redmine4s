package redmine4s.api.model

import org.joda.time.{DateTime, LocalDate}

case class TimeEntry(id: Long, project: (Long, String), issueId: Option[Long], user: (Long, String), activity: (Long, String), hours: Double, comments: String, spentOn: LocalDate, createdOn: DateTime, updatedOn: DateTime, customField: Option[Seq[CustomFieldValue]])
