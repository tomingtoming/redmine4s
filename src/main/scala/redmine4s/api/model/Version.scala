package redmine4s.api.model

import org.joda.time.{DateTime, LocalDate}
import redmine4s.api.resource.ResourceException

case class Version(id: Long, project: (Long, String), name: String, description: String, dueDate: Option[LocalDate], status: Status, sharing: Sharing, createdOn: DateTime, updatedOn: DateTime, customField: Option[Seq[CustomFieldValue]])

sealed trait Status

object Status {

  object Open extends Status {
    override def toString = "open"
  }

  object Locked extends Status {
    override def toString = "locked"
  }

  object Closed extends Status {
    override def toString = "closed"
  }

  def fromString(str: String): Status = str match {
    case "open" => Open
    case "locked" => Locked
    case "closed" => Closed
    case st => throw new ResourceException("Invalid version status!: " + st)
  }

}

sealed trait Sharing

object Sharing {

  object None extends Sharing {
    override def toString = "none"
  }

  object Descendants extends Sharing {
    override def toString = "descendants"
  }

  object Hierarchy extends Sharing {
    override def toString = "hierarchy"
  }

  object Tree extends Sharing {
    override def toString = "tree"
  }

  object System extends Sharing {
    override def toString = "system"
  }

  def fromString(str: String): Sharing = str match {
    case "none" => None
    case "descendants" => Descendants
    case "hierarchy" => Hierarchy
    case "tree" => Tree
    case "system" => System
    case st => throw new ResourceException("Invalid version sharing!: " + st)
  }
}
