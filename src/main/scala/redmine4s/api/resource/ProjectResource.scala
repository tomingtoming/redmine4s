package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model._

trait ProjectResource extends BaseResource {
  private def applyRedmineToProject: PartialFunction[Project, Project] = {
    case p: ProjectSummary => p.copy(redmine = redmine)
    case p: ProjectDetail25 => p.copy(redmine = redmine)
    case p: ProjectDetail26 => p.copy(redmine = redmine)
  }

  def listProjects(): Iterable[Project] = {
    import redmine4s.api.json.ProjectJsonHelper.projectReads
    val params = Map("include" -> "trackers,issue_categories,enabled_modules")
    list("/projects.json", __ \ 'projects, params).map(applyRedmineToProject).toIterable
  }

  def showProject(id: Long): Project = showProject(id.toString)

  def showProject(id: String): Project = {
    import redmine4s.api.json.ProjectJsonHelper.projectReads
    val params = Map("include" -> "trackers,issue_categories,enabled_modules")
    applyRedmineToProject(show(s"/projects/$id.json", __ \ 'project, params))
  }

  def createProject(name: String,
                    identifier: String,
                    description: Option[String] = None,
                    homepage: Option[String] = None,
                    isPublic: Option[Boolean] = None,
                    parent: Option[Long] = None,
                    inheritMembers: Option[Boolean] = None,
                    trackers: Option[Seq[Long]] = None,
                    issueCategories: Option[Seq[Long]] = None,
                    enabledModules: Option[Seq[String]] = None): Project = {
    import redmine4s.api.json.ProjectJsonHelper.{projectCreateWrites, projectReads}
    applyRedmineToProject(create("/projects.json", __ \ 'project, (name, identifier, description, homepage, isPublic, parent, inheritMembers, trackers, issueCategories, enabledModules)))
  }

  def updateProject(id: Either[Long,String],
                    name: Option[String] = None,
                    description: Option[String] = None,
                    homepage: Option[String] = None,
                    isPublic: Option[Boolean] = None,
                    parent: Option[Long] = None,
                    inheritMembers: Option[Boolean] = None,
                    trackers: Option[Seq[Long]] = None,
                    issueCategories: Option[Seq[Long]] = None,
                    enabledModules: Option[Seq[String]] = None): Project = {
    import redmine4s.api.json.ProjectJsonHelper.projectUpdateWrites
    val identifier = id.fold(_.toString, _.toString)
    update(s"/projects/$identifier.json", (name, description, homepage, isPublic, parent, inheritMembers, trackers, issueCategories, enabledModules))
    showProject(identifier)
  }

  def deleteProject(id: Long): Unit = deleteProject(id.toString)

  def deleteProject(id: String): Unit = {
    delete(s"/projects/$id.json")
  }
}
