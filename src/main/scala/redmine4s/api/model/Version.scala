package redmine4s.api.model

import org.joda.time.{DateTime, LocalDate}

case class Version(id: Long, project: (Long, String), name: String, description: String, dueDate: Option[LocalDate], status: String, sharing: String, createdOn: DateTime, updatedOn: DateTime)
