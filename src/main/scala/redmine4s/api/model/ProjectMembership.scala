package redmine4s.api.model

case class ProjectMembership(id: Long, project: (Long, String), user: Option[(Long, String)], group: Option[(Long, String)], roles: Iterable[Role])
