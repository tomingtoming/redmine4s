package redmine4s.api.extension

import redmine4s.api.model.Issue

trait IssueExtension {

  case class IssueTree(id: Long, subject: String, children: Iterable[IssueTree])

  implicit class RichIssues(issues: Iterable[Issue]) {
    def toIdMap: Map[Long, Issue] = issues.map(p => (p.id, p)).toMap

    def toIssueTree: Iterable[IssueTree] = {
      def toIssueTreeSub(parent: Option[Long], issues: Iterable[Issue]): Iterable[IssueTree] = {
        issues.filter(_.parent == parent).map { issue: Issue =>
          IssueTree(issue.id, issue.subject, toIssueTreeSub(Some(issue.id), issues))
        }
      }

      toIssueTreeSub(None, issues)
    }

    def toSortedIssueTree[B](order: IssueTree => B)(implicit ord: Ordering[B]): Seq[IssueTree] = {
      def toSortedIssueTreeSub(order: IssueTree => B, issueTrees: Iterable[IssueTree]): Seq[IssueTree] = {
        issueTrees.toSeq.sortBy(order).map { issueTree =>
          issueTree.copy(children = toSortedIssueTreeSub(order, issueTree.children))
        }
      }

      toSortedIssueTreeSub(order, toIssueTree)
    }
  }

}
