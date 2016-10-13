package redmine4s.api.model

/**
  * Created by toming on 16/10/12.
  */
case class IssueCategory(id: Long, project: (Long, String), name: String, assignedTo: Option[(Long, String)])
