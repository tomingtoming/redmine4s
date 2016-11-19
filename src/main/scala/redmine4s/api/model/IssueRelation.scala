package redmine4s.api.model

case class IssueRelation(id: Long, issueId: Long, issueToId: Long, relationType: RelationType, delay: Option[Int])

object RelationType {

  object Relates extends RelationType("relates")

  object Duplicates extends RelationType("duplicates")

  object Blocks extends RelationType("blocks")

  object Precedes extends RelationType("precedes")

  object CopiedTo extends RelationType("copied_to")

  val values = Seq(Relates, Duplicates, Blocks, Precedes, CopiedTo)

  def fromString(strExpr: String): RelationType = strExpr match {
    case "relates" => Relates
    case "duplicates" => Duplicates
    case "blocks" => Blocks
    case "precedes" => Precedes
    case "copied_to" => CopiedTo
  }
}

sealed class RelationType(val strExpr: String)
