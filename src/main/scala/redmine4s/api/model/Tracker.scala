package redmine4s.api.model

import redmine4s.Redmine

case class Tracker(id: Long, name: String, defaultStatus: Option[(Long, String)], redmine: Redmine)
