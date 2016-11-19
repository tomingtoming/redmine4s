package redmine4s

import org.slf4j.LoggerFactory
import redmine4s.api.resource._
import redmine4s.conf.Configuration

class Redmine(val configuration: Configuration)
  extends AttachmentResource
    with CustomFieldResource
    with BaseResource
    with EnumerationResource
    with GroupResource
    with IssueCategoryResource
    with IssueRelationResource
    with IssueResource
    with IssueStatusResource
    with NewsResource
    with ProjectMembershipResource
    with ProjectResource
    with QueryResource
    with RoleResource
    with TimeEntryResource
    with TrackerResource
    with UserResource
    with VersionResource
    with WikiResource {
  override protected val logger = LoggerFactory.getLogger(this.getClass)
  override protected val redmine = this
}
