package redmine4s.api.model

import redmine4s.Redmine

case class TimeEntryActivity(id: Long, name: String, isDefault: Boolean, redmine: Redmine)
