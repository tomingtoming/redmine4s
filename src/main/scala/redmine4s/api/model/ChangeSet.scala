package redmine4s.api.model

import org.joda.time.DateTime

case class ChangeSet(revision: String, user: Option[(Long, String)], comments: String, committedOn: DateTime)
