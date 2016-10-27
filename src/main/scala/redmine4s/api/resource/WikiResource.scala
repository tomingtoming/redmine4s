package redmine4s.api.resource

import play.api.libs.json._
import redmine4s.api.json.JsonHelper.wikiReads
import redmine4s.api.json.JsonHelper.wikiIndexReads
import redmine4s.api.model.{Wiki, WikiIndex}

trait WikiResource extends BaseResource {
  def indexWikis(projectId: Long): Iterable[WikiIndex] = {
    list[WikiIndex](s"/projects/$projectId/wiki/index.json", __ \ "wiki_pages", Map.empty).map {
      wikiIndex => wikiIndex.copy(projectId = projectId, redmine = redmine)
    }.toIterable
  }

  def listWikis(projectId: Long): Iterable[Wiki] = indexWikis(projectId).map(_.show)

  def mainWikiTitle(projectId: Long): Option[String] = {
    try {
      Some(show[String](s"/projects/$projectId/wiki.json", __ \ "wiki_page" \ "title", Map("include" -> "attachments")))
    } catch {
      case e: NotFoundException => None
    }
  }

  def showWiki(projectId: Long, title: String): Wiki = {
    val wiki = show[Wiki](s"/projects/$projectId/wiki/$title.json", __ \ "wiki_page", Map("include" -> "attachments"))
    wiki.copy(projectId = projectId, redmine = redmine, attachments = wiki.attachments.map(_.map(_.copy(redmine = redmine))))
  }
}
