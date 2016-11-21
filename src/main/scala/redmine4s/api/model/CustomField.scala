package redmine4s.api.model

case class CustomField(id: Long,
                       name: String,
                       customizedType: CustomizedType,
                       fieldFormat: FieldFormat,
                       regexp: String,
                       minLength: Option[Int],
                       maxLength: Option[Int],
                       isRequired: Boolean,
                       isFilter: Boolean,
                       searchable: Boolean,
                       multiple: Boolean,
                       defaultValue: Option[String],
                       visible: Boolean,
                       possibleValues: Option[Seq[(String, String)]])

case class CustomFieldValue(id: Long, name: String, value: Option[String])

object CustomizedType {

  object Document extends CustomizedType("document", "DocumentCustomField")

  object DocumentCategory extends CustomizedType("document_category", "DocumentCategoryCustomField")

  object Group extends CustomizedType("group", "GroupCustomField")

  object Issue extends CustomizedType("issue", "IssueCustomField")

  object IssuePriority extends CustomizedType("issue_priority", "IssuePriorityCustomField")

  object Project extends CustomizedType("project", "ProjectCustomField")

  object TimeEntry extends CustomizedType("time_entry", "TimeEntryActivityCustomField")

  object TimeEntryActivity extends CustomizedType("time_entry_activity", "TimeEntryCustomField")

  object User extends CustomizedType("user", "UserCustomField")

  object Version extends CustomizedType("version", "VersionCustomField")

  val values: Seq[CustomizedType] = Seq(Document, DocumentCategory, Group, Issue, IssuePriority, Project, TimeEntry, TimeEntryActivity, User, Version)

  def fromApiExpr(expr: String): CustomizedType = expr match {
    case "document" => Document
    case "document_category" => DocumentCategory
    case "group" => Group
    case "issue" => Issue
    case "issue_priority" => IssuePriority
    case "project" => Project
    case "time_entry" => TimeEntry
    case "time_entry_activity" => TimeEntryActivity
    case "user" => User
    case "version" => Version
  }
}

sealed abstract class CustomizedType(val apiExpr: String, val dbExpr: String)

object FieldFormat {

  object Enumeration extends FieldFormat("enumeration")

  object String extends FieldFormat("string")

  object Version extends FieldFormat("version")

  object User extends FieldFormat("user")

  object List extends FieldFormat("list")

  object Link extends FieldFormat("link")

  object Float extends FieldFormat("float")

  object Int extends FieldFormat("int")

  object Date extends FieldFormat("date")

  object Bool extends FieldFormat("bool")

  object Text extends FieldFormat("text")

  val values: Seq[FieldFormat] = Seq(Enumeration, String, Version, User, List, Link, Float, Int, Date, Bool, Text)

  def fromApiExpr(expr: String): FieldFormat = expr match {
    case "enumeration" => Enumeration
    case "string" => String
    case "version" => Version
    case "user" => User
    case "list" => List
    case "link" => Link
    case "float" => Float
    case "int" => Int
    case "date" => Date
    case "bool" => Bool
    case "text" => Text
  }
}

sealed abstract class FieldFormat(val expr: String)
