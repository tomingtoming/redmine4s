package redmine4s.api.model

import org.joda.time.{DateTime, LocalDate}
import play.api.libs.json.JsValue
import redmine4s.Redmine
import redmine4s.api.resource.ResourceException

case class Version(id: Long,
                   project: (Long, String),
                   name: String,
                   description: String,
                   dueDate: Option[LocalDate],
                   status: Status,
                   sharing: Sharing,
                   createdOn: DateTime,
                   updatedOn: DateTime,
                   customField: Seq[CustomFieldValue],
                   jsValue: JsValue,
                   redmine: Redmine) extends RedmineModelBase[Version] {

  override def setRedmine(redmine: Redmine): Version = this.copy(redmine = redmine)

  override def setJsValue(jsValue: JsValue): Version = this.copy(jsValue = jsValue)

  /** Returns the version of given id. */
  def show(): Version = redmine.showVersion(id)

  /** Updates the version of given id. */
  def update(name: Option[String] = None,
             status: Option[Status] = None,
             sharing: Option[Sharing] = None,
             dueDate: Option[LocalDate] = None,
             description: Option[String] = None,
             customFields: Option[Seq[(Long, String)]] = None): Version = redmine.updateVersion(id, name, status, sharing, dueDate, description)

  /** Deletes the version of given id. */
  def delete(): Unit = redmine.deleteVersion(id)
}

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
