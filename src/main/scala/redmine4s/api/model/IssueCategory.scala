package redmine4s.api.model

case class IssueCategory(id: Long, project: (Long, String), name: String, assignedTo: Option[(Long, String)])
