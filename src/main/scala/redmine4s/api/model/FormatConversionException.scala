package redmine4s.api.model

import redmine4s.RedmineException

sealed class ModelException private[redmine4s](message: String = null, cause: Throwable = null) extends RedmineException(message, cause)

final class FormatConversionException private[redmine4s](message: String = null, cause: Throwable = null) extends ModelException(message, cause)
