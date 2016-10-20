package redmine4s

import org.slf4j.LoggerFactory
import redmine4s.api.resource._
import redmine4s.conf.Configuration

class Redmine(val configuration: Configuration)
  extends AttachmentResource
    with IssueCategoryResource
    with IssuePriorityResource
    with IssueResource
    with IssueStatusResource
    with NewsResource
    with ProjectMembershipResource
    with ProjectResource
    with RoleResource
    with TimeEntryActivityResource
    with TimeEntryResource
    with TrackerResource
    with VersionResource
    with WikiResource {
  override protected val logger = LoggerFactory.getLogger(this.getClass)
  override protected val redmine = this
}
