package redmine4s.api.resource

import redmine4s.RedmineException

sealed class ResourceException private[redmine4s](message: String = null, cause: Throwable = null) extends RedmineException(message, cause)

final class ForbiddenException private[redmine4s](message: String = null, cause: Throwable = null) extends ResourceException(message, cause)
