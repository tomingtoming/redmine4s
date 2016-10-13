package redmine4s

class RedmineException private[redmine4s](message: String = null, cause: Throwable = null) extends Throwable(message, cause)
