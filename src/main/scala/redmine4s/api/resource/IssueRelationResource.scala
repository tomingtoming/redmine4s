package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.IssueRelation

/**
  * Issue Relations
  * http://www.redmine.org/projects/redmine/wiki/Rest_IssueRelations
  */
trait IssueRelationResource extends BaseResource {
  /** Returns the relations for the issue of given id. */
  def listIssueRelations(issueId: Long): Iterable[IssueRelation] = {
    import redmine4s.api.json.JsonHelper.issueRelationReads
    list(s"/issues/$issueId/relations.json", __ \ 'relations, Map.empty).toIterable
  }

  /** Creates a relation for the issue of given id. */
  def createIssueRelation(issueId: Long, issueToId: Long, relationType: String, delay: Option[Int] = None): IssueRelation = {
    import redmine4s.api.json.JsonHelper.{issueRelationCreateWrites, issueRelationReads}
    create(s"/issues/$issueId/relations.json", __ \ 'relation, (issueToId, relationType, delay))
  }

  /** Returns the relation of given id. */
  def showIssueRelation(issueRelationId: Long): IssueRelation = {
    import redmine4s.api.json.JsonHelper.issueRelationReads
    show(s"/relations/$issueRelationId.json", __ \ 'relation, Map.empty)
  }

  /** Deletes the relation of given id. */
  def deleteIssueRelation(issueRelationId: Long): Unit = delete(s"/relations/$issueRelationId.json")
}
