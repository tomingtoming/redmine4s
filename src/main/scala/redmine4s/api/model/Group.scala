package redmine4s.api.model

case class Group(id: Long, name: String, users: Option[Seq[(Long, String)]], memberships: Option[Seq[ProjectMembership]], customField: Option[Seq[CustomField]])
