package redmine4s.api.model

import redmine4s.Redmine

case class Query(id: Long, name: String, isPublic: Boolean, projectId: Option[Long], redmine: Redmine)
