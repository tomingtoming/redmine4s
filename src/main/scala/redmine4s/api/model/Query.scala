package redmine4s.api.model

case class Query(id: Long, name: String, isPublic: Boolean, projectId: Option[Long])
