package redmine4s.api.model

case class IssueRelation(id: Long, issueId: Long, issueToId: Long, relationType: String, delay: Option[Int])
