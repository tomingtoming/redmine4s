package redmine4s.api.json

import play.api.libs.functional.syntax._
import play.api.libs.json._
import redmine4s.api.model.{IssueRelation, RelationType}

trait IssueRelationJsonHelper extends BaseJsonHelper {
  implicit val relationTypeReads: Reads[RelationType] = __.read[String].map(RelationType.fromString)
  implicit val relationTypeWrites: Writes[RelationType] = __.write[String].contramap(_.strExpr)
  implicit val issueRelationReads: Reads[IssueRelation] = (
    (__ \ 'id).read[Long] ~
      (__ \ 'issue_id).read[Long] ~
      (__ \ 'issue_to_id).read[Long] ~
      (__ \ 'relation_type).read[RelationType] ~
      (__ \ 'delay).readNullable[Int]
    ) (IssueRelation.apply _)
  implicit val issueRelationCreateWrites = (
    (__ \ 'issue_to_id).write[Long] ~
      (__ \ 'relation_type).write[RelationType] ~
      (__ \ 'delay).writeNullable[Int]
    ).tupled
}
