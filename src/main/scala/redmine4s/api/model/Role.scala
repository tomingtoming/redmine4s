package redmine4s.api.model

import redmine4s.Redmine

case class Role(id: Long, name: String, inherited: Option[Boolean], permissions: Option[Iterable[String]], redmine: Redmine)
