package redmine4s.conf

import redmine4s.RedmineException

sealed class ConfigurationException private[redmine4s](message: String = null, cause: Throwable = null) extends RedmineException(message, cause)

final class ConfigurationNotFoundException private[redmine4s](message: String = null, cause: Throwable = null) extends ConfigurationException(message, cause)
