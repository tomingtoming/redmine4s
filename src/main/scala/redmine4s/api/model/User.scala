package redmine4s.api.model

import org.joda.time.DateTime

case class User(id: Long,
                login: String,
                lastname: String,
                firstname: String,
                mail: String,
                lastLoginOn: Option[DateTime],
                createdOn: DateTime,
                groups: Option[Seq[(Long, String)]],
                memberships: Option[Seq[ProjectMembership]],
                customField: Option[Seq[CustomField]])
