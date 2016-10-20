package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.model.Wiki

trait WikiResource extends BaseResource {
  def listWikis(projectId: Long): Iterable[Wiki] = {
    import redmine4s.api.json.ProjectJsonHelper.wikiReads
    list(s"/projects/$projectId/wiki/index.json", __ \ "wiki_pages", Map.empty).map {
      wiki => wiki.copy(projectId = projectId, redmine = redmine)
    }.toIterable
  }

  def mainWiki(projectId: Long): Option[Wiki] = {
    import redmine4s.api.json.ProjectJsonHelper.wikiReads
    try {
      Some(show(s"/projects/$projectId/wiki.json", __ \ "wiki_page", Map.empty).copy(projectId = projectId, redmine = redmine))
    } catch {
      case e:NotFoundException => None
    }
  }

  def showWiki(projectId: Long, title: String): Wiki = {
    import redmine4s.api.json.ProjectJsonHelper.wikiReads
    show(s"/projects/$projectId/wiki/$title.json", __ \ "wiki_page", Map.empty).copy(projectId = projectId, redmine = redmine)
  }
}
